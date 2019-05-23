package backend;

import java.util.Collection;

public interface ITopicData {
    boolean add(Topic topic);

    default boolean add(String topicName){
        Topic t = new Topic(topicName);
        return add(t);
    }

    void remove(String topicName);
    int size();
    Topic getTopic(String topicName);
    Course getCourse(String courseName);
    void empty();
    Collection<Topic> getAllTopics();
    Collection<Course> getAllCourses();
}
