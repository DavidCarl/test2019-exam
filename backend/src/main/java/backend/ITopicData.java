package backend;

import java.util.Collection;

public interface ITopicData {
    boolean add(Topic topic);
    boolean add(String topicName);
    void remove(String topicName);
    int size();
    Topic getTopic(String topicName);
    Course getCourse(String courseName);
    void empty();
    Collection<Topic> getAllTopics();
    Collection<Course> getAllCourses();
}
