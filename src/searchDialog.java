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
                    JOptionPane.showMessageDialog(contentPane, "wrong", "error search", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                switch (selection) {
                    case 0 -> {
                        uStudents = controller.getUStudentByID(Integer.parseInt(searchField.getText()));
                        pStudents = controller.getPStudentByID(Integer.parseInt(searchField.getText()));
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

                JOptionPane.showMessageDialog(contentPane,"success");
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
