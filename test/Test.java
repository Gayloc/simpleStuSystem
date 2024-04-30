import java.io.File;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        Controller c = new Controller(new File("students.xml"));
        HashMap<String, Integer> grades = new HashMap<>();
        grades.put("A", 1);

        c.addStudent(new UStudent(
                "name",
                14,
                13,
                "dasdsa",
                new Address(
                        "fafds",
                        "dfasdsa",
                        "sfasf",
                        "fgasfsa"
                ),
                grades,
                "fasfasf"
        ));
        c.save();
    }
}
