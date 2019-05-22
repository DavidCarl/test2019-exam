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
import org.junit.jupiter.api.Disabled;
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
        TopicRepository.getInstance().empty();
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
        } catch (URISyntaxException e) {
            fail("The test URL isn't correct.");
        }
    }
    @Test
    public void shouldRegisterNewTopicWithSuccess(){
        try {
            MockHttpRequest request = MockHttpRequest.post("principal/register/addTopic/Arts");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);

            // Check the status code
            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
            assertTrue(TopicRepository.getInstance().getAllTopics().size() == 1);

        } catch (URISyntaxException e) {
            fail("The test URL isn't correct.");
        }
    }

    @Test
    public void shouldReturnConflictIfTopicExists(){
        try {

            Topic topic1 = new Topic("Science");
            TopicRepository.getInstance().add(topic1);

            MockHttpRequest request = MockHttpRequest.post("principal/register/addTopic/Science");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);

            // Check the status code
            assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
            assertTrue(TopicRepository.getInstance().getAllTopics().size() == 1);
            assertEquals("{\"errorMessage\":\"Topic with this name is already present in the system!\"}", response.getContentAsString());

        } catch (URISyntaxException e) {
            fail("The test URL isn't correct.");
        }
    }

    @Test
    public void shouldGet406onCourseRequest() {
        try {
            MockHttpRequest request = MockHttpRequest.get("principal/course/this_does_not_exist");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);

            // Check the status code
            assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());

        } catch (URISyntaxException e) {
            fail("The test URL isn't correct.");
        }
    }

    @Test
    public void shouldGetCourseOnRequest(){
        try {
            backend.Teacher teacher = new Teacher("Lucy Bonche", "lycy1234@gmail.com", "Teacher edu");

            TopicRepository tr = TopicRepository.getInstance();
            tr.add("science");
            tr.getTopic("science").addCourse("biology", teacher, "2", 1200);

            MockHttpRequest request = MockHttpRequest.get("principal/course/biology");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);

            // Check the status code
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            assertDoesNotThrow(() -> {
                TopicRepository.getInstance().getCourse("biology");
            });


            String expectedJson = "{\"teacher\":{\"name\":\"Lucy Bonche\"}, \"name\": \"biology\", \"roomNr\": \"2\", \"price\": \"1200\", \"students\": {} }";

            assertEquals(expectedJson, response.getContentAsString());

        } catch (URISyntaxException e) {
            fail("The test URL isn't correct.");
        }
    }

    @Test
    public void shouldRegisterNewCourseWithSuccess(){
        try {
            backend.Teacher teacher= new Teacher("Lucy Bonche", "lycy1234@gmail.com", "Teacher edu");
            TeacherRepository.getInstance().add(teacher);

            TopicRepository.getInstance().add("Sciences");

            MockHttpRequest request = MockHttpRequest.post("principal/register/addCourse/Biology/Sciences/342/lycy1234@gmail.com/300");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);

            // Check the status code
            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

            assertDoesNotThrow(() -> {
                TopicRepository.getInstance().getCourse("Biology");
            });


        } catch (URISyntaxException e) {
            fail("The test URL isn't correct.");
        }
    }

    @Test
    public void shouldReturnConflictIfCourseExists(){
        try {

            backend.Teacher teacher= new Teacher("Bent Bonche", "bent4321@gmail.com", "Teacher edu");
            TeacherRepository.getInstance().add(teacher);

            backend.Topic sciencesTopic = new Topic("Sciences");
            TopicRepository.getInstance().add(sciencesTopic);

            sciencesTopic.addCourse("biology", teacher, "524", 200);

            MockHttpRequest request = MockHttpRequest.post("principal/register/addCourse/Biology/Sciences/342/bent4321@gmail.com/300");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);

            // Check the status code
            assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
            assertTrue(TopicRepository.getInstance().getAllCourses().size() == 1);
            assertEquals("{\"errorMessage\":\"Course with this name is already present in the system!\"}", response.getContentAsString());

        } catch (URISyntaxException e) {
            fail("The test URL isn't correct.");
        }
    }

    @Test
    public void shouldReturnNotFoundWithNonExistentData(){
        try{
            backend.Teacher teacher= new Teacher("Tino Sammson", "t.samson@gmail.com", "Teacher edu");
            TeacherRepository.getInstance().add(teacher);

            TopicRepository.getInstance().add("Arts");

            // Non-existent topic
            MockHttpRequest topicRequest = MockHttpRequest.post("principal/register/addCourse/Swimming/Motion/102/t.samson@gmail.com/300");
            MockHttpResponse topicResponse = new MockHttpResponse();

            // Non-existent teacher
            MockHttpRequest teacherRequest = MockHttpRequest.post("principal/register/addCourse/Music/Arts/342/nonexistent@gmail.com/102");
            MockHttpResponse teacherResponse = new MockHttpResponse();


            // Invoke the requests
            dispatcher.invoke(topicRequest, topicResponse);
            dispatcher.invoke(teacherRequest, teacherResponse);


            assertAll("Check status codes",
                    () -> assertEquals(Response.Status.NOT_FOUND.getStatusCode(), topicResponse.getStatus()),
                    () -> assertEquals(Response.Status.NOT_FOUND.getStatusCode(), teacherResponse.getStatus())
            );

            assertAll("Check response body",
                    () -> assertEquals("{\"errorMessage\":\"Topic with this name is not found in the system!\"}", topicResponse.getContentAsString()),
                    () -> assertEquals("{\"errorMessage\":\"Teacher with this email is not registered in the system!\"}", teacherResponse.getContentAsString())
            );

        } catch (URISyntaxException e) {
            fail("The test URLs aren't correct.");
        }
    }

    @Test @Disabled
    public void shouldReturnBadRequestWhenInvalidData(){

        //TODO: Test if a bad request is received if data validator fails to validate course's data
    }
}
