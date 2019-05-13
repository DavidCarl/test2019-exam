import backend.Topic;
import backend.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TopicTest {
    TopicRepository topics;

    @BeforeEach
        //Here the repository would be initialized before each test.
    void topicRepoSetup(){
        topics = new TopicRepository();
    }

    // Here we test if we can add a topic to the previously initialized repository.
    @Test
    void addTopicToRepo() {
        Topic t = new Topic("Language");

        boolean added = topics.add(t);

        assertEquals(1, topics.size());
        assertTrue(added);
    }


    // Here we test if when we add a topic having the same name as one already in the repository,
    // the former is not being added.
    @Test
    void shouldNotAddTopicWithSameName() {
        Topic t1 = new Topic("Language");
        Topic t2 = new Topic("Language");

        topics.add(t1);
        boolean addedT2 = topics.add(t2);

        assertEquals(1, topics.size());
        assertFalse(addedT2);
    }

    // Here we test if we can add more topics with different names.
    @Test
    void addMoreTopicsWithDistinctNames() {
        Topic t1 = new Topic("Language");
        Topic t2 = new Topic("Motion");
        Topic t3 = new Topic("Science");

        boolean addedT1 = topics.add(t1);
        boolean addedT2 = topics.add(t2);
        boolean addedT3 = topics.add(t3);

        assertEquals(3, topics.size());
        assertTrue(addedT1);
        assertTrue(addedT2);
        assertTrue(addedT3);
    }

    // Here we test if we can remove existing courses.
    @Test
    void removeTopic() {
        Topic t1 = new Topic("Language");

        topics.add(t1);

        assertEquals(1, topics.size());

        assertDoesNotThrow(() -> {
            topics.remove(t1.getName());
        });
    }

    @Test
    void shouldNotRemoveTopicThatDoesNotExist() {
        Topic t1 = new Topic("Language");
        Topic t2 = new Topic("Motion");

        topics.add(t1);

        assertEquals(1, topics.size());

        assertThrows(NoSuchElementException.class, () -> {
            topics.remove(t2.getName());
        });
    }

    @Test
    void shouldBeAbleToRemoveSeveralTopics() {

        Topic t1 = new Topic("Language");
        Topic t2 = new Topic("Motion");
        Topic t3 = new Topic("Science");
        Topic t4 = new Topic("Arts");
        Topic t5 = new Topic("Music");

        topics.add(t1);
        topics.add(t2);
        topics.add(t3);
        topics.add(t4);
        topics.add(t5);

        assertAll("Test Remove several topics",
                () -> assertDoesNotThrow(() -> {
                    topics.remove(t1.getName());
                }),
                () -> assertDoesNotThrow(() -> {
                    topics.remove(t2.getName());
                }),
                () -> assertDoesNotThrow(() -> {
                    topics.remove(t3.getName());
                }),
                () -> assertEquals(2, topics.size())
        );
    }
}
