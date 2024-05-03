import java.util.HashMap;

public class PStudent extends Student {
    private final String research;
    private final String tutor;

    public PStudent(String name, int age, String id, String cls, Address address, HashMap<String, Integer> grades, String research, String tutor) {
        super(name, age, id, cls, address, grades);
        this.research = research;
        this.tutor = tutor;
    }

    public String getResearch() {
        return research;
    }

    public String getTutor() {
        return tutor;
    }
}
