import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;

public class AddUStudent extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField NewId;
    private JTextField NewName;
    private JTextField NewAge;
    private JTextField NewCls;
    private JTextField NewAddress;
    private JTextField NewMajor;
    private JTextField NewGrade;
    private final Controller controller;

    public AddUStudent(Controller c) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        controller = c;

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // 点击 X 时调用 onCancel()
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // 遇到 ESCAPE 时调用 onCancel()
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        String[] add = NewAddress.getText().split(" ");
        if(add.length != 4) {
            JOptionPane.showMessageDialog(this, "地址格式不正确");
            return;
        }

        HashMap<String, Integer> grade = new HashMap<>();

        if(!NewGrade.getText().isEmpty()) {
            String[] Grades = NewGrade.getText().split(";");
            for (String s : Grades) {
                String[] arr = s.split(":");
                if (arr.length != 2) {
                    JOptionPane.showMessageDialog(this, "分数格式不正确");
                    break;
                }
                grade.put(arr[0], Integer.parseInt(arr[1]));
            }
        }

        Address address = new Address(
                add[0],
                add[1],
                add[2],
                add[3]
        );

        controller.addStudent(new UStudent(
                NewName.getText(),
                Integer.parseInt(NewAge.getText()),
                Integer.parseInt(NewId.getText()),
                NewCls.getText(),
                address,
                grade,
                NewMajor.getText()
        ));
        dispose();
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }

    public static void show(Controller c) {
        AddUStudent dialog = new AddUStudent(c);
        dialog.setTitle("添加本科生");
        dialog.pack();
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screensize.getWidth() / 2 - dialog.getWidth()/2;
        int y = (int) screensize.getHeight() / 2 - dialog.getHeight()/2;
        dialog.setLocation(x, y);
        dialog.setVisible(true);
    }
}
