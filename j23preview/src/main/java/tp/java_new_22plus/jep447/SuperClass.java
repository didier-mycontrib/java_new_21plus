package tp.java_new_22plus.jep447;

import java.util.List;

public class SuperClass {

    public SuperClass(String element) {
        System.out.println("SuperClass constructor arg0: " + element);
    }

}

final class SubClass extends SuperClass {

    public SubClass(List<String> data) {
        String element;
        if (data != null && !data.isEmpty()) {
            element = data.get(0).toLowerCase();
        } else {
            element = "<n/a>";
        }

        super(element);
    }

    static void main(){
        System.out.println("test statement before super in constructor (jep447)");
        var sc1 = new SubClass(List.of("One", "Two", "Three"));
        var sc2 = new SubClass(null);
    }
}

