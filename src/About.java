import javax.swing.*;
import java.awt.*;

public class About extends JDialog {
    private JPanel contentPane;

    public About() {
        setContentPane(contentPane);
        setModal(true);
    }

    public static void showAbout() {
        About dialog = new About();
        dialog.pack();
        dialog.setTitle("关于");
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screensize.getWidth() / 2 - dialog.getWidth()/2;
        int y = (int) screensize.getHeight() / 2 - dialog.getHeight()/2;
        dialog.setLocation(x, y);
        dialog.setVisible(true);
    }
}
