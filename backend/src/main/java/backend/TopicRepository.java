package backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class TopicRepository {

    HashMap<String, Topic> topics;
    private static final TopicRepository SINGLE_INSTANCE = new TopicRepository();

    private TopicRepository() {
        topics = new HashMap<String, Topic>();
    }
    public static TopicRepository getInstance() {
        return SINGLE_INSTANCE;
    }

    public boolean add(Topic t) {
        if(topics.containsKey(t.getName().toLowerCase()))
            return false;

        topics.put(t.getName().toLowerCase(), t);
        return true;
    }

    public boolean add(String topicName){
        Topic topic = new Topic(topicName);

        return add(topic);
    }

    public void remove(String name){
        if(!topics.containsKey(name.toLowerCase()))
            throw new NoSuchElementException();

        topics.remove(name.toLowerCase());
    }

    public int size() {
        return topics.size();
    }

    public Topic getTopic(String topicName){
        if(!topics.containsKey(topicName.toLowerCase()))
            throw new NoSuchElementException();

        return topics.get(topicName.toLowerCase());
    }

    public Course getCourse(String courseName){

        for(Topic topic: topics.values()){
            try {
                return topic.getCourse(courseName);

            }catch(NoSuchElementException e){}
        }

        throw new NoSuchElementException();
    }

    public void empty() {
        topics.clear();
    }

    public Collection<Topic> getAllTopics() {
        return topics.values();
    }

    public Collection<Course> getAllCourses(){
        ArrayList<Course> courses = new ArrayList<Course>();

        for(Topic topic: topics.values()){
            courses.addAll(topic.getCourses());
        }

        return courses;
    }
}
