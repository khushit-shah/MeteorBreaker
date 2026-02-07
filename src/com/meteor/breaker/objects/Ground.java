package com.meteor.breaker.objects;

import com.meteor.breaker.Handler;
import com.meteor.breaker.ID;
import java.awt.*;

public class Ground extends GameObject {
    public Ground(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        useRelativeSize(800, 800);
    }

    @Override
    public void tick() {
        x = 0;
        y = 500;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(200, y - 32, 32, 32);
        g.setColor(Color.green);
        g.fillOval(344, y - 52, 32, 62);
        g.setColor(Color.ORANGE);
        g.fillRect(354, y - 20, 12, 42);
        g.setColor(Color.green);
        g.fillRect(x, y, 800, 800);
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }
}
