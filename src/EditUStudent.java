import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class EditUStudent extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField editName;
    private JTextField editAge;
    private JTextField editCls;
    private JTextField editAddress;
    private JTextField editGrade;
    private JTextField editMajor;
    private final Controller controller;
    private static UStudent EditStu;

    public EditUStudent(Controller c) {
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
        String[] add = editAddress.getText().split(" ");
        if(add.length != 4 && !editAddress.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "地址格式不正确");
            return;
        }

        HashMap<String, Integer> grade = new HashMap<>();

        if(!editGrade.getText().isEmpty()) {
            String[] Grades = editGrade.getText().split(";");
            for (String s : Grades) {
                String[] arr = s.split(":");
                if (arr.length != 2) {
                    JOptionPane.showMessageDialog(this, "分数格式不正确");
                    return;
                }
                grade.put(arr[0], Integer.parseInt(arr[1]));
            }
        }

        Address address;
        if(editAddress.getText().isEmpty()) {
            address = new Address();
        } else {
            address = new Address(
                    add[0],
                    add[1],
                    add[2],
                    add[3]
            );
        }

        controller.putStudent(EditStu.getId(), new UStudent(
                editName.getText(),
                Integer.parseInt(editAge.getText().isEmpty()?"0":editAge.getText()),
                EditStu.getId(),
                editCls.getText(),
                address,
                grade,
                editMajor.getText()
        ));

        JOptionPane.showMessageDialog(this,"修改成功");
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void show(Controller c, UStudent editStu) {
        EditStu = editStu;
        EditUStudent dialog = new EditUStudent(c);
        dialog.pack();
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screensize.getWidth() / 2 - dialog.getWidth()/2;
        int y = (int) screensize.getHeight() / 2 - dialog.getHeight()/2;
        dialog.setLocation(x, y);
        dialog.setTitle("编辑学生信息");
        dialog.setVisible(true);
    }

    private void createUIComponents() {
        editName = new JTextField();
        editAge = new JTextField();
        editCls = new JTextField();
        editAddress = new JTextField();
        editGrade = new JTextField();
        editMajor = new JTextField();

        if(EditStu != null) {
            editName.setText(EditStu.getName());
            editAge.setText(String.valueOf(EditStu.getAge()));
            editCls.setText(EditStu.getCls());
            editGrade.setText(EditStu.getGradesStr());
            editMajor.setText(EditStu.getMajor());
            editAddress.setText(EditStu.getAddress().toString());
        } else {
            JOptionPane.showMessageDialog(this, "编辑对象不存在", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
