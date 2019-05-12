package API;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import backend.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.NoSuchElementException;

@Path("/teacher")
public class Teacher {
    TeacherRepository repository = TeacherRepository.getInstance();

    @GET // This annotation indicates GET request
    @Path("/courses/{email}")
    public Response courses(@PathParam("email") String email) {
        try {
            Course[] courses = repository.get(email).getTeachingCourses();

            Gson gsonBuilder = new GsonBuilder().create();
            String coursesJson = gsonBuilder.toJson(courses);
            return Response.ok(coursesJson, MediaType.APPLICATION_JSON).build();

        }catch(NoSuchElementException e){
            return Response.status(404).type(MediaType.APPLICATION_JSON)
                    .entity("{'errorMessage':'Teacher with this email is not found!'}").build();
        }
    }

    @POST // This annotation indicates GET request
    @Path("/register/{name}/{email}/{background}")
    public Response courses(@PathParam("name") String name, @PathParam("email") String email, @PathParam("background") String background) {

        if(repository.add(name,email,background))
            return Response.status(201).build();
        else
            return Response.status(409).entity("{'errorMessage':'Teacher with this email is already registered!'}").build();
    }

    @GET // This annotation indicates GET request
    @Path("/status/{email}")
    public Response status(@PathParam("email") String email) {
        try {
            boolean eligible = repository.get(email).isEligible();

            return Response.ok("{'isEligible': " + eligible + "}", MediaType.APPLICATION_JSON).build();

        }catch(NoSuchElementException e){
            return Response.status(404).type(MediaType.APPLICATION_JSON)
                    .entity("{'errorMessage':'Teacher with this email is not found!'}").build();
        }
    }
}
