package com.meteor.breaker.objects;

import com.meteor.breaker.Handler;
import com.meteor.breaker.ID;
import java.awt.*;
import java.util.Random;

public class background extends GameObject {
    private final Random random = new Random();

    public background(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        useRelativeSize(100, 10);
        velY = 2;
        width = randomWidth(x, 800);
    }

    @Override
    public void tick() {
        y += (int) velY;
        if (y > 650) {
            handler.remove(this);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRoundRect(x, y, width, 10, 1, 1);
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

    private int randomWidth(int x, int maxWidth) {
        int currentX = x;
        while (true) {
            if ((maxWidth - currentX) > 35) {
                return random.nextInt(maxWidth - currentX - 35);
            }
            currentX = Math.max(0, currentX - 10);
        }
    }
}
