package com.meteor.breaker.objects;

import com.meteor.breaker.Handler;
import com.meteor.breaker.ID;
import java.awt.*;
import java.util.Random;

public class Clouds extends GameObject {
    private final Random random = new Random();
    private final Color color;

    public Clouds(int x, int y, ID id, Handler handler, Color color) {
        super(x, y, id, handler);
        useRelativeSize(100, 50);
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

        if (x > 850) {
            handler.add(new Clouds(-100, random.nextInt(300), ID.Cloud, handler, Color.white));
            handler.add(new Clouds(0, random.nextInt(250), ID.Cloud, handler, randomCloudColor()));
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
        g.setColor(color);
        g.fillOval(x, y, 50, 50);
        g.fillOval(x + 25, y, 50, 50);
        g.fillOval(x + 50, y, 50, 50);
        g.fillOval(x + 75, y, 50, 50);
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, 50, 50);
    }
}
