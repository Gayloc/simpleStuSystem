import javax.swing.*;
import javax.swing.filechooser.FileFilter;
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