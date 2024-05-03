import java.io.File;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        Controller c = new Controller(new File("C:\\Users\\gu186\\Desktop\\ss.xml"));

        for(int i=0;i<100;i++) {
            HashMap<String, Integer> grades = new HashMap<>();
            grades.put("Java", (int) (Math.random() * 100));
            grades.put("C", (int) (Math.random() * 100));
            grades.put("Golang", (int) (Math.random() * 100));
            grades.put("C++", (int) (Math.random() * 100));

            c.addStudent(new UStudent(
                    "胡振鹏"+i,
                    114514,
                    String.valueOf(i),
                    "电竞班",
                    new Address(
                            "江西省",
                            "南昌市",
                            "南京东路",
                            "1"
                    ),
                    grades,
                    "电竞"
            ));

            grades.put("Java", (int) (Math.random() * 100));
            grades.put("C", (int) (Math.random() * 100));
            grades.put("Golang", (int) (Math.random() * 100));
            grades.put("C++", (int) (Math.random() * 100));

            c.addStudent(new PStudent(
                    "吕文潇"+i,
                    114514,
                    String.valueOf(i),
                    "走路班",
                    new Address(
                            "江西省",
                            "南昌市",
                            "南京东路",
                            "1"
                    ),
                    grades,
                    "走路",
                    "夏零零"
            ));
        }
        c.save();
    }
}
