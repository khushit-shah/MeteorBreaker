package com.meteor.breaker;

import java.awt.*;
import java.util.Random;

/**
 * Created by Khushit on 5/18/2018.
 */
public class deathObj extends GameObject {
    Random r = new Random();
    Color color;

    public deathObj(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        valx = (float) r.nextInt(5) + 5;
        valy = (float) r.nextInt(5) + 5;
    }

    @Override
    public void tick() {
        y -= valy;
        x -= valx * r.nextInt(2) - 1;
        if(x <= 0)
            handler.remove(this);
    }

    @Override
    public void render(Graphics g) {
      //  System.out.println("in deathOblect")
        synchronized (g) {
            g.setColor(Color.ORANGE);
            g.fillOval(x,y,50,60);
        }
    }

    @Override
    public Rectangle getBound() { return new Rectangle(x,y,50,60);
    }
}
