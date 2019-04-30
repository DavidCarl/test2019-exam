import generator.NameGenerator;

public class main {

    public static void main(String[] args) {
        NameGenerator ng = new NameGenerator();
        Teacher teach = new Teacher(ng.RandomGenderName(), "Teacher Education");
        teach.addCourse("blyatMaster1");
        teach.addCourse("blyatMaster2");
        teach.addCourse("blyatMaster3");
        teach.removeCourse("blyatMaster1");
        teach.addCourse("blyatMaster4");
        teach.printCourses();
        //teach.setPsTeaching(20);
        System.out.println(teach.getVoteRight());
        System.out.println(teach);
    }
}
