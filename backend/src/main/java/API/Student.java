package API;

import backend.Course;
import backend.StudentRepository;
import backend.TopicRepository;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.NoSuchElementException;

@Path("/student")
public class Student {
    StudentRepository repository = StudentRepository.getInstance();

    @POST
    @Path("/register/{firstName}/{lastName}/{birthday}/{email}")
    public Response register(@PathParam("firstName") String fName, @PathParam("lastName") String lName, @PathParam("birthday") String birthday, @PathParam("email") String email) {

        if(repository.add(fName,lName,birthday, email))
            return Response.status(201).build();
        else
            return Response.status(409).entity("{\"errorMessage\":\"Student with this email is already registered!\"}").build();
    }

    @PUT
    @Path("/enrol/{email}/{courseName}")
    public Response enrol(@PathParam("email") String email, @PathParam("courseName") String courseName) {
        try{
            backend.Student student = repository.get(email);

            try {
                Course course = TopicRepository.getInstance().getCourse(courseName);
                course.enroll(student);

                return Response.status(202).build();

            }catch(NoSuchElementException e){
                return Response.status(404).type(MediaType.APPLICATION_JSON)
                        .entity("{\"errorMessage\":\"Course with this name is not found!\"}").build();
            }

        }catch(NoSuchElementException e){
            return Response.status(404).type(MediaType.APPLICATION_JSON)
                    .entity("{\"errorMessage\":\"Student with this email is not found!\"}").build();
        }

    }

    }
