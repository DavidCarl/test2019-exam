package backend;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import static com.mongodb.client.model.Filters.eq;

public class MongoTopicCollection implements ITopicData {
    MongoCollection<Document> topicCollection;

    public MongoTopicCollection(String databaseName) {
        MongoConnector.getInstance().startConnection("localhost", 27017, databaseName);
        topicCollection = MongoConnector.getInstance().getCollection("topics");
    }

    @Override
    public boolean add(Topic topic) {
        try{
            getTopic(topic.getName());
        }
        catch (NoSuchElementException e)
        {
            topicCollection.insertOne(getDocumentFromTopic(topic));
            return true;
        }

        return false;
    }

    @Override
    public Topic getTopic(String topicName) {
        topicName = topicName.toLowerCase();
        MongoCursor<Document> topic = topicCollection.find(eq("topicName", topicName)).limit(1).iterator();

        if(!topic.hasNext())
            throw new NoSuchElementException();

        return getTopicFromDocument(topic.next());
    }

    @Override
    public Course getCourse(String courseName) {
        for(Course course: getAllCourses())
        {
            if(course.getName().equals(courseName))
                return course;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove(String topicName) {
        topicCollection.deleteOne(getDocumentFromTopic(getTopic(topicName)));
    }

    @Override
    public int size() {
        return (int) topicCollection.countDocuments();
    }

    @Override
    public void empty() {
        topicCollection.deleteMany(new Document());
    }

    @Override
    public Collection<Topic> getAllTopics() {
        MongoCursor<Document> topicsCursor = topicCollection.find(new Document()).iterator();
        ArrayList<Topic> topics = new ArrayList<>();

        while(topicsCursor.hasNext()) {
            topics.add(getTopicFromDocument(topicsCursor.next()));
        }

        return topics;
    }

    @Override
    public Collection<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<>();

        for(Topic topic: getAllTopics())
            courses.addAll(topic.getCourses());

        return courses;
    }

    private Topic getTopicFromDocument(Document document) {
        Topic topic = new Topic(document.getString("topicName"));

        return topic;
    }

    private Document getDocumentFromTopic(Topic topic) {
        return new Document("topicName", topic.getName());
    }
}
