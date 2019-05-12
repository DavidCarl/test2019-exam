import API.Teacher;
import backend.Course;
import backend.TeacherRepository;
import backend.TopicRepository;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApiTeacherTest {

    private static Dispatcher dispatcher;
    private static POJOResourceFactory noDefaults;

    @BeforeEach
    public void setup() {
        dispatcher = MockDispatcherFactory.createDispatcher();

        noDefaults = new POJOResourceFactory(Teacher.class);
        dispatcher.getRegistry().addResourceFactory(noDefaults);

        TeacherRepository.getInstance().empty();
    }

    @Test
    public void shouldGetCourses() {
        try {
            backend.Teacher teacher = new backend.Teacher("Lenard Lyndor", "lylly@gmail.com", "Teacher edu");
            TeacherRepository.getInstance().add(teacher);

            Course course1 = new Course("Intro programming", teacher, "101", 100);
            Course course2 = new Course("Advanced programming", teacher, "102", 200);
            Course course3 = new Course("Expert programming", teacher, "103", 300);

            teacher.addCourse(course1);
            teacher.addCourse(course2);
            teacher.addCourse(course3);

            Gson gsonBuilder = new GsonBuilder().create();
            String coursesJson = gsonBuilder.toJson(teacher.getTeachingCourses());

            MockHttpRequest request = MockHttpRequest.get("teacher/courses/lylly@gmail.com");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);


            // Check the status code
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            assertEquals(coursesJson, response.getContentAsString());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnNotFoundWithUnexistentEmail(){
        try{
            MockHttpRequest request = MockHttpRequest.get("teacher/courses/nonexistent@gmail.com");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);

            // Check the status code
            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

            assertEquals("{'errorMessage':'Teacher with this email is not found!'}", response.getContentAsString());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test @Disabled
    public void shouldReturnBadRequestWhenInvalidEmail(){

        //TODO: Test if a bad request is received if email validator fails to validate an email.
    }

    @Test
    public void shouldRegisterNewTeacherWithSuccess(){
        try {
            MockHttpRequest request = MockHttpRequest.post("teacher/register/Lenard%20Lyndor/lylly@gmail.com/Teacher%20edu");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);


            // Check the status code
            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

            assertDoesNotThrow(() -> {
                TeacherRepository.getInstance().get("lylly@gmail.com");
            });

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnConflictIfTeacherExists(){
        try {
            backend.Teacher teacher = new backend.Teacher("Lenard Lyndor", "lylly@gmail.com", "Teacher edu");
            TeacherRepository.getInstance().add(teacher);

            MockHttpRequest request = MockHttpRequest.post("teacher/register/Lenard%20Lyndor/lylly@gmail.com/Teacher%20edu");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);


            // Check the status code
            assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());

            assertEquals("{'errorMessage':'Teacher with this email is already registered!'}", response.getContentAsString());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetEligibility() {
        try {
            backend.Teacher mockedTeacher = mock(backend.Teacher.class);
            when(mockedTeacher.isEligible()).thenReturn(true);
            when(mockedTeacher.getEmail()).thenReturn("lylly@gmail.com");

            backend.Teacher mockedTeacherFalse = mock(backend.Teacher.class);
            when(mockedTeacherFalse.isEligible()).thenReturn(false);
            when(mockedTeacherFalse.getEmail()).thenReturn("subole@gmail.com");

            TeacherRepository.getInstance().add(mockedTeacher);
            TeacherRepository.getInstance().add(mockedTeacherFalse);


            MockHttpRequest requestMocked = MockHttpRequest.get("teacher/status/lylly@gmail.com");
            MockHttpResponse responseMocked = new MockHttpResponse();

            MockHttpRequest requestMockedFalse = MockHttpRequest.get("teacher/status/subole@gmail.com");
            MockHttpResponse responseMockedFalse = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(requestMocked, responseMocked);
            dispatcher.invoke(requestMockedFalse, responseMockedFalse);


            // Check the status code
            assertEquals(Response.Status.OK.getStatusCode(), responseMockedFalse.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), responseMocked.getStatus());

            assertEquals("{'isEligible': true}", responseMocked.getContentAsString());
            assertEquals("{'isEligible': false}", responseMockedFalse.getContentAsString());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnNotFoundWithUnexistentEmailEligibility(){
        try{
            MockHttpRequest request = MockHttpRequest.get("teacher/status/nonexistent@gmail.com");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);

            // Check the status code
            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

            assertEquals("{'errorMessage':'Teacher with this email is not found!'}", response.getContentAsString());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test @Disabled
    public void shouldReturnBadRequestWhenInvalidEmailEligibility(){

        //TODO: Test if a bad request is received if email validator fails to validate an email.
    }
}
