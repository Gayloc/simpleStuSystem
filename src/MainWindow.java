import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainWindow {
    private static Controller c;
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTable UStuTable;
    private JTable PStuTable;
    private JPanel UStuTabPane;
    private JPanel PStuTabPane;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu fileMenu = getFileMenu(frame);
        menuBar.add(fileMenu);

        frame.setSize(800, 600);
        frame.setContentPane(new MainWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static JMenu getFileMenu(JFrame frame) {
        JMenu fileMenu = new JMenu("文件");

        JMenuItem itemNew = new JMenuItem(new AbstractAction("新建…") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(".");
                fileChooser.showSaveDialog(frame);
                File file = fileChooser.getSelectedFile();

                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?><Students/>");
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                c=new Controller(file);
            }
        });

        JMenuItem itemOpen = new JMenuItem(new AbstractAction("打开…") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(".");
                fileChooser.showOpenDialog(frame);
                File file = fileChooser.getSelectedFile();
                c=new Controller(file);
            }
        });
        fileMenu.add(itemNew);
        fileMenu.add(itemOpen);
        return fileMenu;
    }
}
