import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ApiTest {

    @Test
    void firstapitest() {
        HttpUriRequest request = new HttpGet( "http://localhost:8080/2/api/helloworld" );
        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpClientBuilder.create().build().execute( request );
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(httpResponse.getStatusLine().getStatusCode());
    }
}
