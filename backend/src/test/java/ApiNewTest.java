import static org.junit.jupiter.api.Assertions.*;
import API.test;

import org.junit.jupiter.api.*;
import org.jboss.resteasy.mock.*;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;

import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

public class ApiNewTest {

    private static Dispatcher dispatcher;
    private static POJOResourceFactory noDefaults;

    @BeforeEach
    public void setup() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        //Change the test.class with the API controller class we are trying to test
        noDefaults = new POJOResourceFactory(test.class);
        dispatcher.getRegistry().addResourceFactory(noDefaults);
    }
    // One of our actual tests!
    @Test
    public void helloTest() {
        //The try catch is necessary for URISyntaxException,
        // which occur when malformed URL is passed to MockHttpRequest class methods.
        try {
            // Specify the endpoint we want to test, for our example, we use "/hello/{parameter}"
            MockHttpRequest requestGood = MockHttpRequest.get("test/hello/test");
            MockHttpResponse responseGood = new MockHttpResponse();
            MockHttpRequest requestBad = MockHttpRequest.get("test/hello/notgood");
            MockHttpResponse responseBad = new MockHttpResponse();
            // Invoke the request
            dispatcher.invoke(requestGood, responseGood);
            dispatcher.invoke(requestBad, responseBad);



            // Check the status code
            assertEquals(Response.Status.OK.getStatusCode(), responseGood.getStatus());
            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseBad.getStatus());

            // Check that the message we receive is "hello"
            assertEquals("hello", responseGood.getContentAsString());
            assertEquals("something wrong", responseBad.getContentAsString());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}


