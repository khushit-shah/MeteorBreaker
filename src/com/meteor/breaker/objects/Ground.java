package com.meteor.breaker.objects;

import com.meteor.breaker.Handler;
import com.meteor.breaker.ID;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ground extends GameObject {
    public Ground(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        useRelativeSize(800, 800);
    }

    @Override
    public void tick() {
        x = 0;
        y = scaleY(500);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(scaleX(200), y - scaleY(32), scaleX(32), scaleY(32));
        g.setColor(Color.green);
        g.fillOval(scaleX(344), y - scaleY(52), scaleX(32), scaleY(62));
        g.setColor(Color.ORANGE);
        g.fillRect(scaleX(354), y - scaleY(20), scaleX(12), scaleY(42));
        g.setColor(Color.green);
        g.fillRect(x, y, getWidth(), getHeight());
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }
}
