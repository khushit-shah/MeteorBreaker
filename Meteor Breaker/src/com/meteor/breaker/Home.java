package com.meteor.breaker;

import javax.swing.*;
import javax.swing.plaf.ColorChooserUI;
import java.awt.*;
import java.util.Random;

/**
 * Created by Khushit on 5/19/2018.
 */
public class Home extends GameObject {
    private Random reandom;
    private String Type;
    private int height;
    public Home(int x, int y, ID id, Handler handler,String Type) {
        super(x, y, id, handler);
        this.Type = Type;
        reandom = new Random();
        x = reandom.nextInt(750);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(Color.green);
        ColorChooserUI c = new ColorChooserUI() {
            @Override
            public void installUI(JComponent c) {
                super.installUI(c);
            }

        };

    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x,y,width,height);
    }
}
