package API;
import backend.StudentRepository;
import backend.TeacherRepository;
import backend.TopicRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/principal")
public class Principal {
    TeacherRepository teacherRepository = TeacherRepository.getInstance();
    StudentRepository studentRepository = StudentRepository.getInstance();
    TopicRepository topicRepository = TopicRepository.getInstance();

    @GET
    @Path("/teachers")
    public Response teachers() {
        Gson gsonBuilder = new GsonBuilder().create();
        String teachersJson = gsonBuilder.toJson(teacherRepository.getAllTeachers());
        return Response.ok(teachersJson, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/students")
    public Response students() {
        Gson gsonBuilder = new GsonBuilder().create();
        String studentsJson = gsonBuilder.toJson(studentRepository.getAllStudents());
        return Response.ok(studentsJson, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/topics")
    public Response topics() {
        Gson gsonBuilder = new GsonBuilder().create();
        String topicsJson = gsonBuilder.toJson(topicRepository.getAllTopics());
        return Response.ok(topicsJson, MediaType.APPLICATION_JSON).build();
    }
}
