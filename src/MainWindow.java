import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainWindow {
    private static Controller c;
    private JTabbedPane tabbedPane;
    private JPanel mainPanel;
    private JPanel UStuTabPane;
    private JPanel PStuTabPane;
    private JScrollPane UStuPane;
    private JScrollPane PStuPane;
    private JTable UStuTable;
    private JTable PStuTable;
    private static final UStudentModel uStudentModel = new UStudentModel(new UStudent[0]);
    private static final PStudentModel pStudentModel = new PStudentModel(new PStudent[0]);

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu fileMenu = getFileMenu(frame);
        menuBar.add(fileMenu);

        frame.setSize(800, 600);
        frame.setContentPane(new MainWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static JMenu getFileMenu(JFrame frame) {
        JMenu fileMenu = new JMenu("文件");

        JMenuItem itemSave = new JMenuItem(new AbstractAction("保存") {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.save();
            }
        });
        itemSave.setEnabled(false);

        JMenuItem itemNew = new JMenuItem(new AbstractAction("新建…") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(".");

                fileChooser.setFileFilter(new xmlFileFilter());

                fileChooser.showSaveDialog(frame);
                File file = fileChooser.getSelectedFile();

                if(file != null) {
                    try {
                        FileWriter writer = new FileWriter(file);
                        writer.write("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?><Students/>");
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    if (file.getName().endsWith(".xml")) {
                        c = new Controller(file);
                        itemSave.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(frame,"文件格式不正确", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JMenuItem itemOpen = new JMenuItem(new AbstractAction("打开…") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(".");

                fileChooser.setFileFilter(new xmlFileFilter());

                fileChooser.showOpenDialog(frame);
                File file = fileChooser.getSelectedFile();

                if(file!=null) {
                    if (file.getName().endsWith(".xml")) {
                        c = new Controller(file);
                        uStudentModel.setUStudents(c.getUStudent());
                        pStudentModel.setPStudents(c.getPStudent());
                        itemSave.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(frame,"文件格式不正确", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JMenuItem itemExit = new JMenuItem(new AbstractAction("退出") {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        fileMenu.add(itemNew);
        fileMenu.add(itemOpen);
        fileMenu.add(itemSave);
        fileMenu.add(itemExit);
        return fileMenu;
    }

    private void createUIComponents() {
        UStuTable = new JTable();
        UStuTable.setModel(uStudentModel);
        PStuTable = new JTable();
        PStuTable.setModel(pStudentModel);
    }
}

class xmlFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        if (f.isDirectory())return true;
        return f.getName().endsWith(".xml");
    }

    @Override
    public String getDescription() {
        return ".xml";
    }
}

class UStudentModel extends AbstractTableModel {
    private UStudent[] uStudents;

    UStudentModel(UStudent[] uStudents) {
        setUStudents(uStudents);
    }

    public void setUStudents(UStudent[] uStudents) {
        this.uStudents = uStudents;
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return uStudents.length;
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0 -> {
                return "id";
            }
            case 1 -> {
                return "姓名";
            }
            case 2 -> {
                return "年龄";
            }
            case 3->{
                return "班级";
            }
            case 4 -> {
                return "地址";
            }
            case 5 -> {
                return "分数";
            }
            case 6 -> {
                return "专业";
            }
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return uStudents[rowIndex].getId();
            }
            case 1 -> {
                return uStudents[rowIndex].getName();
            }
            case 2 -> {
                return uStudents[rowIndex].getAge();
            }
            case 3->{
                return uStudents[rowIndex].getCls();
            }
            case 4 -> {
                return uStudents[rowIndex].getAddress();
            }
            case 5 -> {
                return uStudents[rowIndex].getGrades();
            }
            case 6 -> {
                return uStudents[rowIndex].getMajor();
            }
        }
        return null;
    }
}

class PStudentModel extends AbstractTableModel {
    private PStudent[] pStudents;

    PStudentModel(PStudent[] pStudents) {
        setPStudents(pStudents);
    }

    public void setPStudents(PStudent[] pStudents) {
        this.pStudents = pStudents;
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return pStudents.length;
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0 -> {
                return "id";
            }
            case 1 -> {
                return "姓名";
            }
            case 2 -> {
                return "年龄";
            }
            case 3->{
                return "班级";
            }
            case 4 -> {
                return "地址";
            }
            case 5 -> {
                return "分数";
            }
            case 6 -> {
                return "研究";
            }
            case 7 -> {
                return "导师";
            }
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return pStudents[rowIndex].getId();
            }
            case 1 -> {
                return pStudents[rowIndex].getName();
            }
            case 2 -> {
                return pStudents[rowIndex].getAge();
            }
            case 3->{
                return pStudents[rowIndex].getCls();
            }
            case 4 -> {
                return pStudents[rowIndex].getAddress();
            }
            case 5 -> {
                return pStudents[rowIndex].getGrades();
            }
            case 6 -> {
                return pStudents[rowIndex].getResearch();
            }
            case 7 -> {
                return pStudents[rowIndex].getTutor();
            }
        }
        return null;
    }
}