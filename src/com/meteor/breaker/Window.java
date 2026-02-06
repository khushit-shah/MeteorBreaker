package com.meteor.breaker;

import java.awt.Dimension;
import javax.swing.JApplet;
import javax.swing.JFrame;

/**
 * Created by Khushit on 5/14/2018.
 */
public class Window extends JApplet {

    public Window(int x, int y, String title, Game game) {
        JFrame jf = new JFrame(title);
        jf.setPreferredSize(new Dimension(x, y));
        jf.setMaximumSize(new Dimension(x, y));
        jf.setMinimumSize(new Dimension(x, y));
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(true);
        jf.setLocationRelativeTo(null);
        jf.requestFocus();
        jf.add(game);
    }
}
