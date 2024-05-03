import java.util.HashMap;

public class UStudent extends Student {
    private final String major;

    public UStudent(String name, int age, String id, String cls, Address address, HashMap<String, Integer> grades, String major) {
        super(name, age, id, cls, address, grades);
        this.major = major;
    }

    public String getMajor() {
        return major;
    }
}
