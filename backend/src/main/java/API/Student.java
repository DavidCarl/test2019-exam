package API;

import backend.Course;
import backend.StudentRepository;
import backend.TopicRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.HashSet;
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

    @GET
    @Path("/info/{email}")
    public Response studentInformation(@PathParam("email") String email) {
        try{
            backend.Student student = repository.get(email);
            Gson gsonBuilder = new GsonBuilder().create();
            String coursesJson = gsonBuilder.toJson(student);
            return Response.ok(coursesJson, MediaType.APPLICATION_JSON).build();
        }catch (NoSuchElementException e){
            return Response.status(404).type(MediaType.APPLICATION_JSON)
                    .entity("{\"errorMessage\":\"Student with this email is not found!\"}").build();
        }
    }

    @GET
    @Path("/courses/{email}")
    public Response courses(@PathParam("email") String email) {
        try {
            HashSet<Course> courses = repository.get(email).getCourses();

            Gson gsonBuilder = new GsonBuilder().create();
            String coursesJson = gsonBuilder.toJson(courses);
            return Response.ok(coursesJson, MediaType.APPLICATION_JSON).build();

        }catch(NoSuchElementException e){
            return Response.status(404).type(MediaType.APPLICATION_JSON)
                    .entity("{\"errorMessage\":\"Student with this email is not found!\"}").build();
        }
    }

    @POST
    @Path("/payment/{email}/{courseName}/{amount}")
    public Response payment(@PathParam("email") String email, @PathParam("courseName") String courseName, @PathParam("amount") int amount) {
        try{
            backend.Student student = repository.get(email);

            try {
                Course course = TopicRepository.getInstance().getCourse(courseName);
                int leftover = course.acceptPayment(student, amount);

                return Response.status(202)
                        .entity("{\"leftoverMoney\":" + leftover + "}").build();

            }catch(Exception e){
                if(e.getClass().equals(NoSuchElementException.class))
                    return Response.status(404).type(MediaType.APPLICATION_JSON)
                            .entity("{\"errorMessage\":\"Course with this name is not found!\"}").build();
                else
                    return Response.status(400).type(MediaType.APPLICATION_JSON)
                            .entity("{\"errorMessage\":\"Payment value not acceptable!\"}").build();
            }
        }catch(NoSuchElementException e){
            return Response.status(404).type(MediaType.APPLICATION_JSON)
                    .entity("{\"errorMessage\":\"Student with this email is not found!\"}").build();
        }
    }

    @GET
    @Path("/paymentStatus/{email}")
    public Response status(@PathParam("email") String email) {
        try {
            HashMap<String, Integer> leftoverAmounts = repository.get(email).getPaymentStatus();

            Gson gsonBuilder = new GsonBuilder().create();
            String paymentsJson = gsonBuilder.toJson(leftoverAmounts);

            return Response.ok(paymentsJson, MediaType.APPLICATION_JSON).build();

        }catch(NoSuchElementException e){
            return Response.status(404).type(MediaType.APPLICATION_JSON)
                    .entity("{\"errorMessage\":\"Student with this email is not found!\"}").build();
        }
    }
}
