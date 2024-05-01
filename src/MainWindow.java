import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
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
    private JTextField searchField;
    private static final UStudentModel uStudentModel = new UStudentModel(new UStudent[0]);
    private static final PStudentModel pStudentModel = new PStudentModel(new PStudent[0]);
    private static JMenuItem itemDelete;
    private static JMenuItem itemEdit;
    private static JMenu addMenu;
    public static final int windowWidth = 800;
    public static final int windowHeight = 600;
    private static int tabIndex = 0;
    private static int selectedRow = -1;
    private static final TableRowSorter<UStudentModel> uSorter = new TableRowSorter<>(uStudentModel);
    private static final TableRowSorter<PStudentModel> pSorter = new TableRowSorter<>(pStudentModel);

    public static void main(String[] args) {
        JFrame frame = new JFrame("学生信息管理系统");

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        menuBar.add(getFileMenu(frame));
        menuBar.add(getEditMenu(frame));

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screensize.getWidth() / 2 - windowWidth/2;
        int y = (int) screensize.getHeight() / 2 - windowHeight/2;
        frame.setLocation(x, y);

        frame.setSize(windowWidth, windowHeight);
        frame.setContentPane(new MainWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static JMenu getEditMenu(JFrame frame) {
        JMenu editMenu = new JMenu("编辑");
        addMenu = new JMenu("添加");
        addMenu.setEnabled(false);

        itemDelete = new JMenuItem(new AbstractAction("删除") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    if (tabIndex == 0) {
                        if(JOptionPane.showConfirmDialog(frame,"确定删除？","删除确定", JOptionPane.YES_NO_OPTION)==0) {
                            c.removeUStudent(c.getUStudent()[selectedRow].getId());
                            uStudentModel.setUStudents(c.getUStudent());
                            selectedRow = -1;
                        }
                    } else if (tabIndex == 1) {
                        if(JOptionPane.showConfirmDialog(frame,"确定删除？","删除确定", JOptionPane.YES_NO_OPTION)==0) {
                            c.removePStudent(c.getPStudent()[selectedRow].getId());
                            pStudentModel.setPStudents(c.getPStudent());
                            selectedRow = -1;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "没有选中项", "删除失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        itemDelete.setEnabled(false);

        itemEdit = new JMenuItem(new AbstractAction("修改") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    if (tabIndex == 0) {
                        EditUStudent.show(c, c.getUStudent()[selectedRow]);
                        selectedRow = -1;
                        uStudentModel.setUStudents(c.getUStudent());
                    } else if (tabIndex == 1) {
                        EditPStudent.show(c, c.getPStudent()[selectedRow]);
                        selectedRow = -1;
                        pStudentModel.setPStudents(c.getPStudent());
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "没有选中项", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        itemEdit.setEnabled(false);

        JMenuItem itemAddUStudent = new JMenuItem(new AbstractAction("本科生") {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddUStudent.show(c);
                selectedRow = -1;
                uStudentModel.setUStudents(c.getUStudent());
            }
        });

        JMenuItem itemAddPStudent = new JMenuItem(new AbstractAction("研究生") {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPStudent.show(c);
                selectedRow = -1;
                pStudentModel.setPStudents(c.getPStudent());
            }
        });

        addMenu.add(itemAddUStudent);
        addMenu.add(itemAddPStudent);
        editMenu.add(addMenu);
        editMenu.add(itemDelete);
        editMenu.add(itemEdit);
        return editMenu;
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
                String fname = fileChooser.getName(file);

                if(fname!=null && !fname.endsWith(".xml")){
                    file=new File(fileChooser.getCurrentDirectory(),fname+".xml");
                }

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
                        itemDelete.setEnabled(true);
                        addMenu.setEnabled(true);
                        itemEdit.setEnabled(true);
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
                        itemDelete.setEnabled(true);
                        addMenu.setEnabled(true);
                        itemEdit.setEnabled(true);
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
        UStuTable.setRowSorter(uSorter);
        UStuTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    int row = UStuTable.convertRowIndexToModel(UStuTable.getSelectedRow());
                    EditUStudent.show(c, c.getUStudent()[row]);
                    selectedRow = -1;
                    uStudentModel.setUStudents(c.getUStudent());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                selectedRow = UStuTable.convertRowIndexToView(UStuTable.getSelectedRow());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        PStuTable = new JTable();
        PStuTable.setModel(pStudentModel);
        PStuTable.setRowSorter(pSorter);
        PStuTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    int row = PStuTable.convertRowIndexToModel(PStuTable.getSelectedRow());
                    EditPStudent.show(c, c.getPStudent()[row]);
                    selectedRow = -1;
                    pStudentModel.setPStudents(c.getPStudent());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                selectedRow = PStuTable.convertRowIndexToView(PStuTable.getSelectedRow());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(e -> {
            tabIndex = tabbedPane.getSelectedIndex();
            if(tabIndex == 0) {
                selectedRow = UStuTable.getSelectedRow();
            } else {
                selectedRow = PStuTable.getSelectedRow();
            }
        });

        searchField = new JTextField();
        searchField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchField.getText();

                if (text.trim().isEmpty()) {
                    uSorter.setRowFilter(null);
                    pSorter.setRowFilter(null);
                } else {
                    uSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    pSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchField.getText();

                if (text.trim().isEmpty()) {
                    uSorter.setRowFilter(null);
                    pSorter.setRowFilter(null);
                } else {
                    uSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    pSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String text = searchField.getText();

                if (text.trim().isEmpty()) {
                    uSorter.setRowFilter(null);
                    pSorter.setRowFilter(null);
                } else {
                    uSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    pSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
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
        return uStudents!=null?uStudents.length:0;
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
                return "专业";
            }
            case 7 -> {
                return "总成绩";
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
                return uStudents[rowIndex].getGradesStr();
            }
            case 6 -> {
                return uStudents[rowIndex].getMajor();
            }
            case 7 -> {
                return uStudents[rowIndex].getTotalGrades();
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
        return pStudents!=null?pStudents.length:0;
    }

    @Override
    public int getColumnCount() {
        return 9;
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
            case 8 -> {
                return "总成绩";
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
                return pStudents[rowIndex].getGradesStr();
            }
            case 6 -> {
                return pStudents[rowIndex].getResearch();
            }
            case 7 -> {
                return pStudents[rowIndex].getTutor();
            }
            case 8 -> {
                return pStudents[rowIndex].getTotalGrades();
            }
        }
        return null;
    }
}