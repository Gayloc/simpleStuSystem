import java.util.HashMap;

public abstract class Student {
    private final String name;
    private final int age;
    private final int id;
    private final String cls;
    private final Address address;
    private final HashMap<String, Integer> grades;

    public Student(String name, int age, int id, String cls, Address address, HashMap<String, Integer> grades) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.cls = cls;
        this.address = address;
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getCls() {
        return cls;
    }

    public Address getAddress() {
        return address;
    }

    public HashMap<String, Integer> getGrades() {
        return grades;
    }

    public String getGradesStr() {
        StringBuilder sb = new StringBuilder();
        grades.forEach((k, v) -> {
            sb.append(k).append(":").append(v).append(";");
        });
        return sb.toString();
    }
}