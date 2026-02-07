package com.meteor.breaker;

import java.awt.*;
import java.util.List;

/**
 * Created by Khushit on 5/17/2018.
 */
public class player extends GameObject {
    private static final int GROUND_Y = 450;
    private static final int JUMP_LIMIT = 350;

    private boolean over = false;
    private Color color = new Color(255, 10, 20);

    player(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        valx = 0;
        valy = 10;
        points = 10;
        Health = 100;
        bullet = 1000;
        Missiles = 10;
    }

    public void tick() {
        int red = getHealth() * 255 / 100;
        if (red >= 255) red = 255;
        if (red <= 0) red = 1;
        color = new Color(red, 100, 100);

        for (GameObject obstacle : handler.getCollidingObjects(this, List.of(ID.block, ID.FollowBloack))) {
            if (obstacle.id == ID.block) {
                setHealth(getHealth() - 2);
            } else if (obstacle.id == ID.FollowBloack) {
                setHealth(getHealth() - 1);
            }
            color = new Color(red, 100, 100);
        }

        x += valx;
        if (!jump) {
            y = GROUND_Y;
        }
        if (jump) {
            if (y > JUMP_LIMIT && y <= GROUND_Y) {
                valy = 5;
                y -= valy;
            }
            if (y <= JUMP_LIMIT) {
                valy = 1;
                y += valy;
            }
        }
        if (x >= Game.WIDTH - 35) {
            x = Game.WIDTH - 35;
        }
        if (x <= 0) {
            x = 0;
        }
        if (y <= 0) {
            y = 0;
        }
        if (y >= GROUND_Y) {
            y = GROUND_Y;
        }

        if (getHealth() < 0) {
            AudioPlayer.getSound("Game_Over").play();
            Game.state = Game.STATE.OVER;
        }
    }

    @Override
    public void render(Graphics g) {
        if (!over) {
            g.setColor(color);
            g.fillOval(x + 6, y - 20, 20, 20);
            g.fillRect(x, y, 32, 32);
            g.fillRect(x + 6, y + 32, 10, 20);
            g.fillRect(x + 18, y + 32, 10, 20);
            g.drawRect(x, y, 32, 32);

            if (shooting && bullet > 0) {
                AudioPlayer.getSound("bullet").play((float) 30, (float) 100);
                handler.add(new bullet(x + 8, y - 24, ID.bullet, handler));
                bullet -= 1;
            }
        } else {
            g.drawString("GAME OVER ", 400, 300);
        }
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, 32, 32);
    }
}
