// Example program to initialize a Java UI window with a button

import javax.swing.*; // import window utilities

public class Window {

    private final String name = "ExampleWindow";
    private JFrame frame;
    private JButton button;

    public Window(int width, int height) {
        frame = new JFrame(this.name); // initialize window with a title
        frame.setSize(width,height);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Window() {
        this(400, 400);
    }

    public void addButton(String text, int[] boundaries) {
        JButton returnButton = new JButton(text);
        returnButton.setBounds(boundaries[0], boundaries[1], boundaries[2], boundaries[3]);
        this.frame.add(returnButton);
    }


    public static void main(String[] args) {

        Window w = new Window();
        w.addButton("clickMe", new int[]{40, 40, 100, 100});

    }
}
