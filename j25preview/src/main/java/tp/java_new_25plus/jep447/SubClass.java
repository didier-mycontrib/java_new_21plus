package tp.java_new_25plus.jep447;

import java.util.List;

public class SubClass extends SuperClass {

    public SubClass(List<String> data) {
        String element;
        if (data != null && !data.isEmpty()) {
            element = data.get(0).toLowerCase();
        } else {
            element = "<n/a>";
        }

        super(element);
    }

    public static void essai(){
        System.out.println("test statement before super in constructor (jep447)");
        var sc1 = new SubClass(List.of("One", "Two", "Three"));
        var sc2 = new SubClass(null);
    }
}

