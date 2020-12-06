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

    /**
     * Create a title JLabel for our UI
     * @param title - the title of the label
     * @return - JLabel object with that title
     */
    protected JLabel createTitleJLabel(String title) {
        JLabel label = new JLabel(title);
        label.setSize(400, 30);
        label.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 24));
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    /**
     * Creates a bounded JLabel
     * @param name - the name of the label
     * @param font_size - the size of the font
     * @param x - coordinate
     * @param y - coordinate
     * @param w - height
     * @param h - width
     * @return - JLabel object
     */
    protected JLabel createBoundedJLabel(String name, int font_size, int x, int y, int w, int h) {
        JLabel label = new JLabel(name);
        label.setFont(new Font("TimesRoman", Font.BOLD, font_size));
        label.setBounds(x, y, h, w);
        return label;
    }

    /**
     * Creates a JButton
     * @param name - the name of the Button
     * @param x - coordinate
     * @param y - coordinate
     * @param w - width
     * @param h - height
     * @return - JButton
     */
    protected JButton createButton(String name, int x, int y, int w, int h) {
        JButton button = new JButton(name);
        button.setBounds(x, y, w, h);
        return button;
    }

}
