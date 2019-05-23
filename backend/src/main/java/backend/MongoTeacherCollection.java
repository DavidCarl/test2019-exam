package backend;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;

public class MongoTeacherCollection implements ITeacherData {
    MongoCollection<Document> teacherCollection;

    public MongoTeacherCollection(String databaseName) {
        MongoConnector.getInstance().startConnection("localhost", 27017, databaseName);
        teacherCollection = MongoConnector.getInstance().getCollection("teachers");
    }

    @Override
    public boolean add(Teacher teacher) {
        try
        {
            get(teacher.getEmail());
        }
        catch (NoSuchElementException e)
        {
            teacherCollection.insertOne(getDocumentFromTeacher(teacher));
            return true;
        }

        return false;
    }

    @Override
    public Teacher get(String email) {
        MongoCursor<Document> teacher = teacherCollection.find(eq("email", email)).limit(1).iterator();

        if(!teacher.hasNext())
            throw new NoSuchElementException();

        return getTeacherFromDocument(teacher.next());
    }

    @Override
    public void remove(String email) {
        teacherCollection.deleteOne(getDocumentFromTeacher(get(email)));
    }

    @Override
    public int size() {
        return (int) teacherCollection.countDocuments();
    }

    @Override
    public void empty() {
        teacherCollection.deleteMany(new Document());
    }

    @Override
    public Collection<Teacher> getAllTeachers() {
        MongoCursor<Document> teachersCursor = teacherCollection.find(new Document()).iterator();
        ArrayList<Teacher> teachers = new ArrayList<>();

       while(teachersCursor.hasNext()) {
           teachers.add(getTeacherFromDocument(teachersCursor.next()));
       }

        return teachers;
    }

    private Teacher getTeacherFromDocument(Document document) {
        Teacher teacher = new Teacher(document.getString("name"), document.getString("email"), document.getString("eduBackground"));

        return teacher;
    }

    private Document getDocumentFromTeacher(Teacher teacher) {
        return new Document("name", teacher.getName())
                .append("email", teacher.getEmail())
                .append("eduBackground", teacher.getEduBackground());
    }
}
