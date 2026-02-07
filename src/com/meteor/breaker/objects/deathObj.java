package com.meteor.breaker.objects;

import com.meteor.breaker.Handler;
import com.meteor.breaker.ID;
import java.awt.*;
import java.util.Random;

public class deathObj extends GameObject {
    private final Random random = new Random();

    public deathObj(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        useRelativeSize(50, 60);
        velX = random.nextInt(5) + 5;
        velY = random.nextInt(5) + 5;
    }

    @Override
    public void tick() {
        y -= (int) velY;
        x -= ((int) velX * random.nextInt(2)) - 1;
        if (x <= 0) {
            handler.remove(this);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, 50, 60);
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }
}
