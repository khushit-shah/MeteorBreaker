package com.meteor.breaker.objects;

import com.meteor.breaker.Handler;
import com.meteor.breaker.ID;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Clouds extends GameObject {
    private final Random random = new Random();
    private final Color color;

    public Clouds(int x, int y, ID id, Handler handler, Color color) {
        super(x, y, id, handler);
        useRelativeSize(125, 50);
        this.color = color;
    }

    @Override
    public void tick() {
        int count = 0;
        x++;

        for (GameObject temp : handler.getObjectsSnapshot()) {
            if (temp.id == ID.Cloud) count++;
            if (count > 2 && temp.id == ID.Cloud) {
                handler.remove(temp);
            }
        }

        if (x > scaleX(850)) {
            handler.add(new Clouds(-scaleX(100), random.nextInt(scaleY(300)), ID.Cloud, handler, Color.white));
            handler.add(new Clouds(0, random.nextInt(scaleY(250)), ID.Cloud, handler, randomCloudColor()));
            handler.remove(this);
        }
    }

    private Color randomCloudColor() {
        int value = random.nextInt(3);
        if (value == 0) return Color.lightGray;
        if (value == 1) return Color.GRAY;
        return Color.blue;
    }

    @Override
    public void render(Graphics g) {
        int cloudPartWidth = Math.max(1, getWidth() * 2 / 5);
        int cloudPartHeight = getHeight();
        int step = Math.max(1, cloudPartWidth / 2);

        g.setColor(color);
        g.fillOval(x, y, cloudPartWidth, cloudPartHeight);
        g.fillOval(x + step, y, cloudPartWidth, cloudPartHeight);
        g.fillOval(x + (2 * step), y, cloudPartWidth, cloudPartHeight);
        g.fillOval(x + (3 * step), y, cloudPartWidth, cloudPartHeight);
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }
}
