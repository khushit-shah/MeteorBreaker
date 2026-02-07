package com.meteor.breaker.objects;

import com.meteor.breaker.Handler;
import com.meteor.breaker.ID;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

public class block extends GameObject {
    private static final int MIN_WIDTH = 40;
    private final Random random = new Random();
    private int health = 200;
    private boolean deathEffect;
    private int deathCount;
    private player player;

    public block(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        velY = 3;
        setRelativeSizeFromPixels(calculateWidth(x, scaleX(800)), scaleY(30));
    }

    private int calculateWidth(int x, int maxWidth) {
        while (true) {
            int w = random.nextInt(scaleX(25)) + scaleX(MIN_WIDTH);
            if ((maxWidth - (x + w)) > scaleX(35)) {
                return w;
            }
            x = Math.max(0, x - scaleX(10));
        }
    }

    @Override
    public void tick() {
        y += (int) velY;
        if (y >= scaleY(650)) {
            handler.remove(this);
            return;
        }

        if (player == null) {
            player = (player) handler.findFirstById(ID.player).orElse(null);
        }

        List<GameObject> bullets = handler.getCollidingObjects(this, List.of(ID.bullet));
        for (GameObject b : bullets) {
            health -= 20;
            handler.remove(b);
            if (health < 0) {
                deathEffect = true;
                if (player != null && deathCount < 3) {
                    player.setPoints(player.getPoints() + 100);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, getWidth(), getHeight());
        g.setColor(Color.black);
        g.drawString(String.valueOf(health), x, y + Math.max(1, getHeight() / 2));
        handler.add(new trails(x, y, ID.trials, handler, getWidth(), 0.02f, Color.YELLOW));
        if (deathEffect) {
            deathCount++;
            handler.add(new deathObj(x, y, ID.deathobj, handler));
            if (deathCount > 5) {
                handler.remove(this);
            }
        }
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
