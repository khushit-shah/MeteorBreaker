package com.meteor.breaker;

import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;

/**
 * Created by Khushir on 5/14/2018.
 */
public class Window extends JApplet{
    public Window(int x, int y, String title , Game game){
        JFrame jf = new JFrame(title);
        jf.setPreferredSize(new Dimension(x,y));
        jf.setMaximumSize(new Dimension(x,y));
        jf.setMinimumSize(new Dimension(x,y));
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.requestFocus();
        jf.add(game);
        game.start();
    }
}
