package clientUI;

import javax.swing.*;
import java.awt.*;

public abstract class FrameUI {

    /**
     * centerFrame() centers the frame on the users' screen.
     */
    protected void centerFrame(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dimension.width - frame.getSize().width) / 2;
        int y = (dimension.height - frame.getSize().height) / 2;
        frame.setLocation(x, y);
    }

    protected JLabel createTitleJLabel(String title) {
        JLabel label = new JLabel(title);
        label.setSize(400, 30);
        label.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 24));
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    protected JLabel createBoundedJLabel(String name, int size, int x, int y, int h, int w) {
        JLabel label = new JLabel(name);
        label.setFont(new Font("TimesRoman", Font.BOLD, size));
        label.setBounds(x, y, h, w);

        return label;
    }

    protected JButton createButton(String name, int x, int y, int w, int h) {
        JButton button = new JButton(name);
        button.setBounds(x, y, w, h);
        return button;
    }

}
