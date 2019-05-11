package API; // Note your package will be {{ groupId }}.rest

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/test")
public class test {
    @GET // This annotation indicates GET request
    @Path("/hello/{testparam}")
    public Response hello(@PathParam("testparam") String testparam) {
        if(testparam.equals("test"))
            return Response.status(200).entity("hello").build();
        else
            return Response.status(400).entity("something wrong").build();
    }
}