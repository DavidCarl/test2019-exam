import java.util.HashMap;
import java.util.NoSuchElementException;

public class TopicRepository {

    HashMap<String, Topic> topics;

    public TopicRepository(){
        topics = new HashMap<String, Topic>();
    }

    public boolean add(Topic t) {
        if(topics.containsKey(t.getName()))
            return false;

        topics.put(t.getName(), t);
        return true;
    }

    public void remove(String name){
        if(!topics.containsKey(name))
            throw new NoSuchElementException();

        topics.remove(name);
    }

    public int size() {
        return topics.size();
    }
}
