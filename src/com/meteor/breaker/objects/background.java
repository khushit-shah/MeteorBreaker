package com.meteor.breaker.objects;

import com.meteor.breaker.Handler;
import com.meteor.breaker.ID;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class background extends GameObject {
    private final Random random = new Random();

    public background(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        velY = 2;
        setRelativeSizeFromPixels(randomWidth(x, scaleX(800)), scaleY(10));
    }

    @Override
    public void tick() {
        y += (int) velY;
        if (y > scaleY(650)) {
            handler.remove(this);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRoundRect(x, y, getWidth(), getHeight(), Math.max(1, scaleX(1)), Math.max(1, scaleY(1)));
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

    private int randomWidth(int x, int maxWidth) {
        int currentX = x;
        while (true) {
            if ((maxWidth - currentX) > scaleX(35)) {
                return random.nextInt(Math.max(1, maxWidth - currentX - scaleX(35)));
            }
            currentX = Math.max(0, currentX - scaleX(10));
        }
    }
}
