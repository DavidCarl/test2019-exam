import API.Student;
import backend.StudentRepository;
import backend.TeacherRepository;
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

public class ApiStudentTest {

    private static Dispatcher dispatcher;
    private static POJOResourceFactory noDefaults;

    @BeforeEach
    public void setup() {
        dispatcher = MockDispatcherFactory.createDispatcher();

        noDefaults = new POJOResourceFactory(Student.class);
        dispatcher.getRegistry().addResourceFactory(noDefaults);

        StudentRepository.getInstance().empty();
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
    public void shouldReturnConflictIfStudentExists(){
        try {
            backend.Student student = new backend.Student("Sammy", "Smith", "30-01-2000", "smith00@gmail.com");
            StudentRepository.getInstance().add(student);

            MockHttpRequest request = MockHttpRequest.post("student/register/Betty/Williamson/25-03-1987/smith00@gmail.com");
            MockHttpResponse response = new MockHttpResponse();

            // Invoke the request
            dispatcher.invoke(request, response);

            // Check the status code
            assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());

            assertEquals("{'errorMessage':'Student with this email is already registered!'}", response.getContentAsString());

        } catch (URISyntaxException e) {
            fail("The test URL isn't correct.");
        }
    }

}
