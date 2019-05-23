package backend;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;

public class MongoStudentCollection implements IStudentData {
    MongoCollection<Document> studentCollection;

    public MongoStudentCollection(String databaseName) {
        MongoConnector.getInstance().startConnection("localhost", 27017, databaseName);
        studentCollection = MongoConnector.getInstance().getCollection("students");
    }

    @Override
    public boolean add(Student student) {
        try
        {
            get(student.getEmail());
        }
        catch (NoSuchElementException e) {
            studentCollection.insertOne(getDocumentFromStudent(student));
            return true;
        }

        return false;
    }

    @Override
    public Student get(String email) {
        MongoCursor<Document> student = studentCollection.find(eq("email", email)).limit(1).iterator();

        if(!student.hasNext())
            throw new NoSuchElementException();

        return getStudentFromDocument(student.next());
    }

    @Override
    public void remove(String email) {
        studentCollection.deleteOne(getDocumentFromStudent(get(email)));
    }

    @Override
    public int size() {
        return (int)studentCollection.countDocuments();
    }

    @Override
    public void empty() {
        studentCollection.deleteMany(new Document());
    }

    @Override
    public Collection<Student> getAllStudents() {
        MongoCursor<Document> studentsCursor = studentCollection.find(new Document()).iterator();
        ArrayList<Student> students = new ArrayList<>();

       while(studentsCursor.hasNext()) {
           students.add(getStudentFromDocument(studentsCursor.next()));
       }

        return students;
    }

    private Student getStudentFromDocument(Document document) {
        List values = new ArrayList(document.values());
        Date birthday = document.getDate("birthday");

        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

        Student student = new Student(values.get(1).toString(), values.get(2).toString(), outputFormat.format(birthday), values.get(4).toString());
        List<Document> courses = (List<Document>)document.get("courses");

        for(Document course: courses){
            String courseName = course.getString("name");
            String roomNr = course.getString("roomNr");
            int price = course.getInteger("price");
            Course c = new Course(courseName,null,roomNr,price);
            student.addCourse(c);
        }

        return student;
    }

    private Document getDocumentFromStudent(Student student) {
        List<Document> courses = new ArrayList<Document>();

        for(Course course: student.getCourses()){
            Document courseBSON = new Document();
            courseBSON.put("name", course.getName());
            courseBSON.put("roomNr", course.getRoomNr());
            courseBSON.put("price", course.getPrice());

            courses.add(courseBSON);
        }

        return new Document("fName", student.getFName())
                .append("lName", student.getLName())
                .append("birthday", student.getBirthday())
                .append("email", student.getEmail())
               .append("courses", courses);
    }
}
