package pong;

import javax.swing.*;
import java.awt.*;

public class Display {
    private Canvas canvas;
    private JFrame frame;
    private String title;
    private int width, height;

    public Display(String title, int width, int height, Game game) {
        this.width = width;
        this.height = height;
        this.title = title;

        createDisplay(game);
    }

    void createDisplay(Game game) {
        frame = new JFrame();

        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();

        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setBackground(Color.WHITE);

        frame.add(canvas);
        frame.addKeyListener(game);

        frame.pack();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
