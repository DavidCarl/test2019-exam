import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApiTest {

    @Test
    void helloworld_statuscode() {
        HttpUriRequest request = new HttpGet( "http://localhost:8080/2/api/helloworld" );
        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpClientBuilder.create().build().execute( request );
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
    }

    //This is not the optimal way to do it
    //AFAIK there should be a better library for API request, just cant remember it
    //Ill update ASAP!
    @Test
    void helloworld_content() {
        HttpUriRequest request = new HttpGet( "http://localhost:8080/2/api/helloworld" );
        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpClientBuilder.create().build().execute( request );
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()), 65728);
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        catch (Exception e) { e.printStackTrace(); }

        assertEquals(sb.toString(), "Hello World");
    }
}
