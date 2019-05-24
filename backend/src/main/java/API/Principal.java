package API;
import backend.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.NoSuchElementException;

@Path("/principal")
public class Principal {
    ITeacherData teacherRepo = TeacherRepository.getInstance();
    IStudentData studentRepo = StudentRepository.getInstance();
    ITopicData topicRepo = TopicRepository.getInstance();

    @GET
    @Path("/teachers")
    public Response teachers() {
        Gson gsonBuilder = new GsonBuilder().create();
        String teachersJson = gsonBuilder.toJson(teacherRepo.getAllTeachers());
        return Response.ok(teachersJson, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/students")
    public Response students() {
        Gson gsonBuilder = new GsonBuilder().create();
        String studentsJson = gsonBuilder.toJson(studentRepo.getAllStudents());
        return Response.ok(studentsJson, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/topics")
    public Response topics() {
        Gson gsonBuilder = new GsonBuilder().create();
        String topicsJson = gsonBuilder.toJson(topicRepo.getAllTopics());
        return Response.ok(topicsJson, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/courses")
    public Response courses() {
        Gson gsonBuilder = new GsonBuilder().create();
        String topicsJson = gsonBuilder.toJson(topicRepo.getAllCourses());
        return Response.ok(topicsJson, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/course/{name}")
    public Response course(@PathParam("name") String courseName) {
        try {
            Gson gsonBuilder = new GsonBuilder().create();
            String studentsJson = gsonBuilder.toJson(topicRepo.getCourse(courseName).getCoursePayments());
            backend.Course course = topicRepo.getCourse(courseName);
            String jsonString = "{\"teacher\":{\"name\":\"" + course.getTeacher().getName() + "\"},"+
                    " \"name\": \"" + course.getName() + "\","+
                    " \"roomNr\": \"" + course.getRoomNr() + "\"," +
                    " \"price\": \"" + course.getPrice() + "\"," +
                    " \"students\": " + studentsJson +
                    " }";
            return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
        }catch (NoSuchElementException e){
            return Response.status(406).entity("{\"errorMessage\":\"No course with the given name exist\"}").build();
        }
    }

    @POST
    @Path("/register/addTopic/{name}")
    public Response addTopic(@PathParam("name") String topicName) {

        if(topicRepo.add(topicName))
            return Response.status(201).build();
        else
            return Response.status(409).entity("{\"errorMessage\":\"Topic with this name is already present in the system!\"}").build();
    }

    @POST
    @Path("/register/addCourse/{courseName}/{topicName}/{roomNumber}/{teacherEmail}/{price}")
    public Response addTopic(@PathParam("courseName") String courseName, @PathParam("topicName") String topicName, @PathParam("roomNumber") String roomNumber, @PathParam("teacherEmail") String teacherEmail, @PathParam("price") int price) {
        try {
            backend.Teacher teacher = teacherRepo.get(teacherEmail);

            try {
                backend.Topic topic = topicRepo.getTopic(topicName);

                if (topic.addCourse(courseName, teacher, roomNumber, price))
                    return Response.status(201).build();
                else
                    return Response.status(409).entity("{\"errorMessage\":\"Course with this name is already present in the system!\"}").build();

            } catch (NoSuchElementException e) {
                return Response.status(404).type(MediaType.APPLICATION_JSON)
                        .entity("{\"errorMessage\":\"Topic with this name is not found in the system!\"}").build();
            }
        } catch (NoSuchElementException e) {
            return Response.status(404).type(MediaType.APPLICATION_JSON)
                    .entity("{\"errorMessage\":\"Teacher with this email is not registered in the system!\"}").build();
        }
    }
}
