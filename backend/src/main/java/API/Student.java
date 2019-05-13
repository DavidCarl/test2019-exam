package API;

import backend.StudentRepository;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/student")
public class Student {
    StudentRepository repository = StudentRepository.getInstance();

    @POST
    @Path("/register/{firstName}/{lastName}/{birthday}/{email}")
    public Response register(@PathParam("firstName") String fName, @PathParam("lastName") String lName, @PathParam("birthday") String birthday, @PathParam("email") String email) {

        if(repository.add(fName,lName,birthday, email))
            return Response.status(201).build();
        else
            return Response.status(409).entity("{'errorMessage':'Student with this email is already registered!'}").build();
    }
}
