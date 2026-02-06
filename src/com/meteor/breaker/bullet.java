package com.meteor.breaker;

import java.awt.*;
import java.util.Random;

/**
 * Created by Khushit on 5/18/2018.
 */
public class bullet extends GameObject {
    Random r = new Random();
    Color color;
    public bullet(int x,int y,ID id,Handler handler){
        super(x,y,id,handler);
        valx = (float) r.nextInt(5);
        valy = (float) r.nextInt(5) + 5;
    }
    @Override
    public void tick() {
        y -= valy;
        color = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
        x+= valx * r.nextInt(2) - 1;
        if(y <= 0)
            handler.remove(this);
    }

    @Override
    public void render(Graphics g) {
        synchronized (g) {
            g.setColor(color);
            g.fillOval(x,y,12,12);
        }
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x,y,12,12);
    }
}