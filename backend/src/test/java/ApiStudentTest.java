import API.Student;
import backend.*;
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

public class ApiStudentTest {

    private static Dispatcher dispatcher;
    private static POJOResourceFactory noDefaults;

    @BeforeEach
    public void setup() {
        dispatcher = MockDispatcherFactory.createDispatcher();

        noDefaults = new POJOResourceFactory(Student.class);
        dispatcher.getRegistry().addResourceFactory(noDefaults);

        StudentRepository.getInstance().empty();
        TopicRepository.getInstance().empty();
    }

    @Test
    public void shouldRegisterNewStudentWithSuccess(){
        try {
            MockHttpRequest request = MockHttpRequest.post("student/register/Betty/Williamson/25-03-1987/btWilli@gmail.com");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);


            // Check the status code
            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

            assertDoesNotThrow(() -> {
                StudentRepository.getInstance().get("btWilli@gmail.com");
            });

        } catch (URISyntaxException e) {
            fail("The test URL isn't correct.");
        }
    }


    @Test
    public void shouldEnrolStudentInACourse(){
        try {

            Topic topic = new Topic("Language");

            Teacher mockedTeacher = mock(Teacher.class);
            when(mockedTeacher.isEligible()).thenReturn(true);
            topic.addCourse("French", mockedTeacher, "107", 100);

            Course french = topic.getCourse("French");

            TopicRepository.getInstance().add(topic);

            backend.Student student = new backend.Student("Sammy", "Smith", "30-01-2000", "smith00@gmail.com");
            StudentRepository.getInstance().add(student);

            MockHttpRequest request = MockHttpRequest.put("student/enrol/smith00@gmail.com/french");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);

            // Check the status code
            assertEquals(Response.Status.ACCEPTED.getStatusCode(), response.getStatus());

            assertTrue(french.getCoursePayments().containsKey("smith00@gmail.com"));

        } catch (URISyntaxException e) {
            fail("The test URL isn't correct.");
        }
    }

    @Test
    public void shouldReturnNotFoundWithNonExistentParameterName(){
        try{

            Topic topic = new Topic("Language");

            Teacher mockedTeacher = mock(Teacher.class);
            when(mockedTeacher.isEligible()).thenReturn(true);
            topic.addCourse("French", mockedTeacher, "107", 100);

            Course french = topic.getCourse("French");

            TopicRepository.getInstance().add(topic);

            backend.Student student = new backend.Student("Sammy", "Smith", "30-01-2000", "smith00@gmail.com");
            StudentRepository.getInstance().add(student);


            // Non-existent student
            MockHttpRequest studentEmailRequest = MockHttpRequest.put("student/enrol/betty@gmail.com/french");
            MockHttpResponse studentEmailResponse = new MockHttpResponse();

            // Non-existent course
            MockHttpRequest courseRequest = MockHttpRequest.put("student/enrol/smith00@gmail.com/english");
            MockHttpResponse courseResponse = new MockHttpResponse();


            // Invoke the requests
            dispatcher.invoke(studentEmailRequest, studentEmailResponse);
            dispatcher.invoke(courseRequest, courseResponse);


            assertAll("Check status codes",
                    () -> assertEquals(Response.Status.NOT_FOUND.getStatusCode(), studentEmailResponse.getStatus()),
                    () -> assertEquals(Response.Status.NOT_FOUND.getStatusCode(), courseResponse.getStatus())

            );

            assertAll("Check response body",
                    () -> assertEquals("{\"errorMessage\":\"Student with this email is not found!\"}", studentEmailResponse.getContentAsString()),
                    () -> assertEquals("{\"errorMessage\":\"Course with this name is not found!\"}", courseResponse.getContentAsString())

            );

        } catch (URISyntaxException e) {
            fail("The test URLs aren't correct.");
        }
    }

    @Test @Disabled
    public void shouldReturnBadRequestWhenInvalidStudentData(){

        //TODO: Test if a bad request is received if data validator fails to validate student data
    }

}
