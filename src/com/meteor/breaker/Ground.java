package com.meteor.breaker;

import java.awt.*;
import java.util.Random;

/**
 * Created by Khushit on 5/19/2018.
 */
public class Ground extends GameObject {
    Random r = new Random();
    public Ground(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
    }

    @Override
    public void tick() {
        x = 0;
        y = 500;
    }
    @Override
    public void render(Graphics g) {
        synchronized (g) {
            g.setColor(Color.ORANGE);
            g.fillOval(200,y-32,32,32);
            g.setColor(Color.green);
            g.fillOval(344,y-52,32,62);
            g.setColor(Color.ORANGE);
            g.fillRect(354,y-20,12,42);
            g.setColor(Color.green);
            g.fillRect(x,y,800,800);
        }
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x,y,800,800);
    }

}
