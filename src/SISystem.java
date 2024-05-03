public interface SISystem {
    void addStudent(UStudent u);
    void addStudent(PStudent p);
    void removeUStudent(String id);
    void removePStudent(String id);
    void putStudent(String id, UStudent u);
    void putStudent(String id, PStudent p);
    UStudent[] getUStudentByID(String id);
    UStudent[] getUStudentByName(String str);
    UStudent[] getUStudentByCLS(String str);
    PStudent[] getPStudentByID(String id);
    PStudent[] getPStudentByName(String str);
    PStudent[] getPStudentByCLS(String str);
    UStudent[] getUStudent();
    PStudent[] getPStudent();
}
