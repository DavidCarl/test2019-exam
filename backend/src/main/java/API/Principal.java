package API;
import backend.Course;
import backend.TeacherRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.NoSuchElementException;

@Path("/principal")
public class Principal {
    TeacherRepository teacherRepository = TeacherRepository.getInstance();

    @GET
    @Path("/teachers")
    public Response teachers() {
        Gson gsonBuilder = new GsonBuilder().create();
        String teachersJson = gsonBuilder.toJson(teacherRepository.getAllTeachers());
        return Response.ok(teachersJson, MediaType.APPLICATION_JSON).build();
    }
}
