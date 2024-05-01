import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class searchDialog extends JDialog {
    private JPanel contentPane;
    private JRadioButton byIdRadioButton;
    private JRadioButton byNameRadioButton;
    private JRadioButton byClsRadioButton;
    private JTextField searchField;
    private JButton searchButton;
    private JTabbedPane tabbedPane1;
    private JTable UStuTable;
    private JTable PStuTable;
    private ButtonGroup searchGroup;
    private JButton buttonOK;
    private final Controller controller;
    private static final PStudentModel pStudentModel = new PStudentModel(new PStudent[0]);
    private static final UStudentModel uStudentModel = new UStudentModel(new UStudent[0]);
    private UStudent[] uStudents;
    private PStudent[] pStudents;
    private int selection = 0;

    public searchDialog(Controller c) {
        setContentPane(contentPane);
        setModal(true);
        controller=c;

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(searchField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "请输入查找内容", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                switch (selection) {
                    case 0 -> {
                        if(searchField.getText().matches("[0-9]+")) {
                            uStudents = controller.getUStudentByID(Integer.parseInt(searchField.getText()));
                            pStudents = controller.getPStudentByID(Integer.parseInt(searchField.getText()));
                        } else {
                            JOptionPane.showMessageDialog(contentPane, "id 格式不正确", "错误", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    case 1 -> {
                        uStudents = controller.getUStudentByName(searchField.getText());
                        pStudents = controller.getPStudentByName(searchField.getText());
                    }
                    case 2 -> {
                        uStudents = controller.getUStudentByCLS(searchField.getText());
                        pStudents = controller.getPStudentByCLS(searchField.getText());
                    }
                }

                uStudentModel.setUStudents(uStudents);
                pStudentModel.setPStudents(pStudents);

                JOptionPane.showMessageDialog(contentPane,"成功查找到本科生信息"+uStudents.length+"条，研究生信息"+pStudents.length+"条");
            }
        });

        byIdRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selection = 0;
            }
        });
        byNameRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selection = 1;
            }
        });
        byClsRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selection = 2;
            }
        });
    }

    public static void show(Controller c) {
        searchDialog dialog = new searchDialog(c);
        dialog.pack();
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screensize.getWidth() / 2 - dialog.getWidth()/2;
        int y = (int) screensize.getHeight() / 2 - dialog.getHeight()/2;
        dialog.setLocation(x, y);
        dialog.setVisible(true);
    }

    private void createUIComponents() {
        UStuTable = new JTable();
        PStuTable = new JTable();
        UStuTable.setModel(uStudentModel);
        PStuTable.setModel(pStudentModel);
        UStuTable.setAutoCreateRowSorter(true);
        PStuTable.setAutoCreateRowSorter(true);
    }
}
