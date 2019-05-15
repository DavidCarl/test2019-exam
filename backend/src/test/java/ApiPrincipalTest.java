import API.Principal;
import backend.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApiPrincipalTest {

    private static Dispatcher dispatcher;
    private static POJOResourceFactory noDefaults;

    @BeforeEach
    public void setup() {
        dispatcher = MockDispatcherFactory.createDispatcher();

        noDefaults = new POJOResourceFactory(Principal.class);
        dispatcher.getRegistry().addResourceFactory(noDefaults);

        TeacherRepository.getInstance().empty();
        StudentRepository.getInstance().empty();
    }

    @Test
    public void shouldGetTeachers() {
        try {
            backend.Teacher teacher1 = new backend.Teacher("Antonia Alvera", "ant87@gmail.com", "Teacher edu");
            backend.Teacher teacher2 = new backend.Teacher("John Anderson", "andr45@gmail.com", "Teacher edu");
            backend.Teacher teacher3 = new backend.Teacher("Paul Beck", "becky@gmail.com", "Teacher edu");
            TeacherRepository.getInstance().add(teacher1);
            TeacherRepository.getInstance().add(teacher2);
            TeacherRepository.getInstance().add(teacher3);

            Gson gsonBuilder = new GsonBuilder().create();
            String teachersJson = gsonBuilder.toJson(TeacherRepository.getInstance().getAllTeachers());

            MockHttpRequest request = MockHttpRequest.get("principal/teachers");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);

            // Check the status code
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            assertEquals(teachersJson, response.getContentAsString());
        }
        catch (URISyntaxException e){
            fail("The test URL isn't correct.");
        }
    }

    @Test
    public void shouldGetStudents() {
        try {
            backend.Student student1 = new backend.Student("Eliza", "Cook", "30-01-1999", "cook99@gmail.com");
            backend.Student student2 = new backend.Student("Thomas", "Log", "15-05-2000", "logthom@gmail.com");
            backend.Student student3 = new backend.Student("George", "Smith", "03-10-1990", "georg@gmail.com");

            StudentRepository.getInstance().add(student1);
            StudentRepository.getInstance().add(student2);
            StudentRepository.getInstance().add(student3);

            Gson gsonBuilder = new GsonBuilder().create();
            String studentsJson = gsonBuilder.toJson(StudentRepository.getInstance().getAllStudents());

            MockHttpRequest request = MockHttpRequest.get("principal/students");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);

            // Check the status code
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            assertEquals(studentsJson, response.getContentAsString());
        }
        catch (URISyntaxException e){
            fail("The test URL isn't correct.");
        }
    }

    @Test
    public void shouldGetTopics() {
        try {

            Topic topic1 = new Topic("Language");
            Topic topic2 = new Topic("Motion");
            Topic topic3 = new Topic("Science");

            TopicRepository.getInstance().add(topic1);
            TopicRepository.getInstance().add(topic2);
            TopicRepository.getInstance().add(topic3);

            Gson gsonBuilder = new GsonBuilder().create();
            String topicsJson = gsonBuilder.toJson(TopicRepository.getInstance().getAllTopics());

            MockHttpRequest request = MockHttpRequest.get("principal/topics");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);

            // Check the status code
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            assertEquals(topicsJson, response.getContentAsString());
        }
        catch (URISyntaxException e){
            fail("The test URL isn't correct.");
        }
    }

    @Test
    public void shouldGetCourses() {
        try {

            Topic topic1 = new Topic("Language");
            Topic topic2 = new Topic("Motion");
            TopicRepository.getInstance().add(topic1);
            TopicRepository.getInstance().add(topic2);

            Teacher mockedTeacher1 = mock(Teacher.class);
            Teacher mockedTeacher2 = mock(Teacher.class);
            when(mockedTeacher1.isEligible()).thenReturn(true);
            when(mockedTeacher2.isEligible()).thenReturn(true);
            
            topic1.addCourse("French", mockedTeacher1, "107", 100);
            topic1.addCourse("English", mockedTeacher1, "200", 150);
            topic1.addCourse("Danish", mockedTeacher1, "203", 130);
            topic1.addCourse("Spanish", mockedTeacher2, "503", 300);
            
            topic2.addCourse("Swimming", mockedTeacher1, "333", 200);
            topic2.addCourse("Jogging", mockedTeacher1, "334", 200);
            topic2.addCourse("Running", mockedTeacher2, "335", 200);
            
            Gson gsonBuilder = new GsonBuilder().create();
            String coursesJson = gsonBuilder.toJson(TopicRepository.getInstance().getAllCourses());

            MockHttpRequest request = MockHttpRequest.get("principal/courses");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);

            // Check the status code
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            assertEquals(coursesJson, response.getContentAsString());
        }
        catch (URISyntaxException e){
            fail("The test URL isn't correct.");
        }
    }
}
