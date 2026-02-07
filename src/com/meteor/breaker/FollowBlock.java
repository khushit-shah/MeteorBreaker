package com.meteor.breaker;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class FollowBlock extends GameObject {

    private static final int MIN_WIDTH = 40;

    private final Random r;

    private Boolean deathEffect = false;

    private int count = 0;
    private int diffy, diffx;
    private float distance;
    private GameObject player;

    public FollowBlock(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        Health = 200;
        r = new Random();
        valx = 2;
        valy = 3;

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
        if (player == null) {
            player = handler.findFirstById(ID.player).orElse(null);
        }
        if (player == null) {
            return;
        }

        diffx = player.getX() - x;
        diffy = player.getY() - y;
        distance = (float) Math.sqrt(Math.pow(diffx, 2) + Math.pow(diffy, 2));
        if (distance != 0) {
            x += (diffx / distance) * valx;
            y += (diffy / distance) * valy;
        }
        if (y >= 650) {
            handler.remove(this);
            return;
        }

        List<GameObject> collidingBullets = handler.getCollidingObjects(this, List.of(ID.bullet));
        for (GameObject bullet : collidingBullets) {
            Health -= 20;
            handler.remove(bullet);
        }

        if (Health < 0) {
            deathEffect = true;
            count++;
            if (count < 3) {
                player.setPoints(player.getPoints() + 100);
            }
        }
    }

    public void render(Graphics g) {
        synchronized (g) {
            g.setColor(Color.DARK_GRAY);
            g.fillOval(x, y, width, 30);
            g.setColor(Color.white);
            g.drawString(String.valueOf(this.getHealth()), x, y + 15);
            handler.add(new trails(x, y, ID.trials, handler, width, 0.02f, Color.RED));
            if (deathEffect) {
                count++;
                handler.add(new deathObj(x, y, ID.deathobj, handler));
                if (count > 5) {
                    handler.remove(this);
                }
            }
        }
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, width, 30);
    }
}
