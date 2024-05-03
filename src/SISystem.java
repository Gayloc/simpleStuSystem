public interface SISystem {
    void addStudent(UStudent u);
    void addStudent(PStudent p);
    void removeUStudent(int id);
    void removePStudent(int id);
    void putStudent(int id, UStudent u);
    void putStudent(int id, PStudent p);
    UStudent[] getUStudentByID(int id);
    UStudent[] getUStudentByName(String str);
    UStudent[] getUStudentByCLS(String str);
    PStudent[] getPStudentByID(int id);
    PStudent[] getPStudentByName(String str);
    PStudent[] getPStudentByCLS(String str);
    UStudent[] getUStudent();
    PStudent[] getPStudent();
}
