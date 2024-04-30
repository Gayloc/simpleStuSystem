import java.io.File;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        Controller c = new Controller(new File("students.xml"));
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
                new HashMap<String, Integer>(),
                "fasfasf"
        ));
        c.save();
    }
}
