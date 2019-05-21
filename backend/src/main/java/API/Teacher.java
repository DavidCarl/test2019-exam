package API;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import backend.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.NoSuchElementException;

@Path("/teacher")
public class Teacher {
    ITeacherData teacherRepo = TeacherRepository.getInstance();

    @GET // This annotation indicates GET request
    @Path("/courses/{email}")
    public Response courses(@PathParam("email") String email) {
        try {
            Course[] courses = teacherRepo.get(email).getTeachingCourses();

            Gson gsonBuilder = new GsonBuilder().create();
            String coursesJson = gsonBuilder.toJson(courses);
            return Response.ok(coursesJson, MediaType.APPLICATION_JSON).build();

        }catch(NoSuchElementException e){
            return Response.status(404).type(MediaType.APPLICATION_JSON)
                    .entity("{\"errorMessage\":\"Teacher with this email is not found!\"}").build();
        }
    }

    @POST // This annotation indicates GET request
    @Path("/register/{name}/{email}/{background}")
    public Response courses(@PathParam("name") String name, @PathParam("email") String email, @PathParam("background") String background) {

        if(teacherRepo.add(name,email,background))
            return Response.status(201).build();
        else
            return Response.status(409).entity("{\"errorMessage\":\"Teacher with this email is already registered!\"}").build();
    }

    @GET // This annotation indicates GET request
    @Path("/status/{email}")
    public Response status(@PathParam("email") String email) {
        try {
            boolean eligible = teacherRepo.get(email).isEligible();

            return Response.ok("{\"isEligible\": " + eligible + "}", MediaType.APPLICATION_JSON).build();

        }catch(NoSuchElementException e){
            return Response.status(404).type(MediaType.APPLICATION_JSON)
                    .entity("{\"errorMessage\":\"Teacher with this email is not found!\"}").build();
        }
    }

    @PUT // This annotation indicates GET request
    @Path("/education/{email}/{newBackground}")
    public Response education(@PathParam("email") String email, @PathParam("newBackground") String newBackground) {

        try {
            teacherRepo.get(email).setEduBackground(newBackground);
            return Response.status(202).build();

        }catch(NoSuchElementException e){
            return Response.status(404).type(MediaType.APPLICATION_JSON)
                    .entity("{\"errorMessage\":\"Teacher with this email is not found!\"}").build();
        }
    }
}
