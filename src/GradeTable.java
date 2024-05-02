import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class GradeTable extends JDialog {
    private JPanel contentPane;
    private JTabbedPane tabbedPane1;
    private JTable UTable;
    private JTable PTable;
    private final ArrayList<String> uSubjects = new ArrayList<>();
    private final ArrayList<String> pSubjects = new ArrayList<>();

    public GradeTable(Controller c) {
        setContentPane(contentPane);
        setModal(true);

        UStudent[] uStudents = c.getUStudent();
        PStudent[] pStudents = c.getPStudent();

        for (UStudent uStudent : uStudents) {
            Set<String> set = uStudent.getGrades().keySet();
            set.forEach(e -> {
                if (!uSubjects.contains(e)) {
                    uSubjects.add(e);
                }
            });
        }

        for (PStudent pStudent : pStudents) {
            Set<String> set = pStudent.getGrades().keySet();
            set.forEach(e -> {
                if (!pSubjects.contains(e)) {
                    pSubjects.add(e);
                }
            });
        }

        String[] usArr = new String[uSubjects.size()];
        String[] psArr = new String[pSubjects.size()];

        for (int i = 0; i < uSubjects.size(); i++) {
            usArr[i] = uSubjects.get(i);
        }
        for (int i = 0; i < pSubjects.size(); i++) {
            psArr[i] = pSubjects.get(i);
        }

        UTableModel UTableModel = new UTableModel();
        UTableModel.setData(c.getUStudent(), usArr);
        PTableModel PTableModel = new PTableModel();
        PTableModel.setData(c.getPStudent(), psArr);
        UTable.setModel(UTableModel);
        PTable.setModel(PTableModel);
    }

    public static void show(Controller c) {
        GradeTable dialog = new GradeTable(c);
        dialog.setSize(800, 600);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screensize.getWidth() / 2 - dialog.getWidth()/2;
        int y = (int) screensize.getHeight() / 2 - dialog.getHeight()/2;
        dialog.setLocation(x, y);
        dialog.setTitle("成绩表");
        dialog.setVisible(true);
    }

    private void createUIComponents() {
        UTable = new JTable();
        UTable.setAutoCreateRowSorter(true);
        PTable = new JTable();
        PTable.setAutoCreateRowSorter(true);
    }
}

class UTableModel extends AbstractTableModel {
    private UStudent[] uStudents = new UStudent[0];
    private String[] subjects = new String[0];

    void setData(UStudent[] us, String[] s) {
        uStudents = us;
        subjects = s;
    }

    @Override
    public int getRowCount() {
        return uStudents.length;
    }

    @Override
    public int getColumnCount() {
        return subjects.length+3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return uStudents[rowIndex].getId();
        } else if (columnIndex == 1) {
            return uStudents[rowIndex].getName();
        } else if (columnIndex == subjects.length+2) {
            return uStudents[rowIndex].getTotalGrades();
        } else {
            return uStudents[rowIndex].getGrades().getOrDefault(subjects[columnIndex-2], 0);
        }
    }

    @Override
    public String getColumnName(int column) {
        if(column == 0) {
            return "id";
        } else if(column == 1) {
            return "姓名";
        } else if(column == subjects.length+2) {
            return "总成绩";
        } else {
            return subjects[column-2];
        }
    }

    @Override
    public Class<?> getColumnClass(int column) {
        if(column == 0) {
            return Integer.class;
        } else if(column == 1) {
            return String.class;
        } else if(column == subjects.length+2) {
            return Integer.class;
        } else {
            return Integer.class;
        }
    }
}

class PTableModel extends AbstractTableModel {
    private PStudent[] pStudents = new PStudent[0];
    private String[] subjects = new String[0];

    void setData(PStudent[] ps, String[] s) {
        pStudents = ps;
        subjects = s;
    }

    @Override
    public int getRowCount() {
        return pStudents.length;
    }

    @Override
    public int getColumnCount() {
        return subjects.length+3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return pStudents[rowIndex].getId();
        } else if (columnIndex == 1) {
            return pStudents[rowIndex].getName();
        } else if (columnIndex == subjects.length+2) {
            return pStudents[rowIndex].getTotalGrades();
        } else {
            return pStudents[rowIndex].getGrades().getOrDefault(subjects[columnIndex-2], 0);
        }
    }

    @Override
    public String getColumnName(int column) {
        if(column == 0) {
            return "id";
        } else if(column == 1) {
            return "姓名";
        } else if(column == subjects.length+2) {
            return "总成绩";
        } else {
            return subjects[column-2];
        }
    }

    @Override
    public Class<?> getColumnClass(int column) {
        if(column == 0) {
            return Integer.class;
        } else if(column == 1) {
            return String.class;
        } else if(column == subjects.length+2) {
            return Integer.class;
        } else {
            return Integer.class;
        }
    }
}