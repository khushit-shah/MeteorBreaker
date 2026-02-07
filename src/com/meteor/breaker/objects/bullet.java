package com.meteor.breaker.objects;

import com.meteor.breaker.Handler;
import com.meteor.breaker.ID;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class bullet extends GameObject {
    private final Random random = new Random();
    private Color color = Color.WHITE;

    public bullet(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        useRelativeSize(12, 12);
        velX = random.nextInt(5);
        velY = random.nextInt(5) + 5;
    }

    @Override
    public void tick() {
        y -= (int) velY;
        x += ((int) velX * random.nextInt(2)) - 1;
        color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));

        if (y <= 0) {
            handler.remove(this);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, getWidth(), getHeight());
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }
}
