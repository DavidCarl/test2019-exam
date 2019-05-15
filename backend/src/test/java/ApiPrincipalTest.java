import API.Principal;
import backend.StudentRepository;
import backend.TeacherRepository;
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
}
