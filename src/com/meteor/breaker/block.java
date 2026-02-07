package com.meteor.breaker;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class block extends GameObject {

    private static final int MIN_WIDTH = 40;

    private final Random r;

    private Boolean deathEffect = false;

    private int count = 0;

    private GameObject player;

    public block(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);

        valy = 3;
        Health = 200;
        r = new Random();

        width = calculateWidth(x, 800);
        player = handler.findFirstById(ID.player).orElse(null);
    }

    private int calculateWidth(int x, int MAX_WIDTH) {
        while (true) {
            int wid = r.nextInt(25);
            if ((MAX_WIDTH - (x + MIN_WIDTH + wid)) <= 35) {
                x -= 10;
                wid = r.nextInt(25);
            }
            if ((MAX_WIDTH - (x + MIN_WIDTH) + wid) >= 35) {
                return wid + MIN_WIDTH;
            }
        }
    }

    @Override
    public void tick() {
        y += valy;

        if (y >= 650) {
            handler.remove(this);
            return;
        }

        if (player == null) {
            player = handler.findFirstById(ID.player).orElse(null);
        }

        List<GameObject> collidingBullets = handler.getCollidingObjects(this, List.of(ID.bullet));

        for (GameObject bullet : collidingBullets) {
            Health -= 20;
            handler.remove(bullet);

            if (Health < 0) {
                deathEffect = true;
                count++;
                if (count < 3 && player != null) {
                    player.setPoints(player.getPoints() + 100);
                }
            }
        }
    }

    public void render(Graphics g) {
        synchronized (g) {
            g.setColor(Color.YELLOW);
            g.fillOval(x, y, width, 30);
            g.setColor(Color.black);
            g.drawString(String.valueOf(this.getHealth()), x, y + 15);
            handler.add(new trails(x, y, ID.trials, handler, width, 0.02f, Color.YELLOW));
            if (deathEffect) {
                count++;
                handler.add(new deathObj(x, y, ID.deathobj, handler));
                if (count > 5) {
                    handler.remove(this);
                }
            }
        }
    }

    public Rectangle getBound() {
        return new Rectangle(x, y, width, 30);
    }
}
