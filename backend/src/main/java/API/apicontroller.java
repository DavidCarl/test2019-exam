package API;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class apicontroller extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    public apicontroller() {
        //Register any APIs controllers here
        singletons.add(new test());
        singletons.add(new Teacher());
        singletons.add(new Student());
        singletons.add(new Principal());
    }
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}