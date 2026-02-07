package com.meteor.breaker.objects;

import com.meteor.breaker.AudioPlayer;
import com.meteor.breaker.Game;
import com.meteor.breaker.Handler;
import com.meteor.breaker.ID;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class player extends GameObject {
    private static final int GROUND_Y = 450;
    private static final int JUMP_LIMIT = 350;

    private static final int BASE_WIDTH = 32;
    private static final int BASE_HEIGHT = 32;
    private boolean over = false;
    private boolean shooting;
    private boolean jump;
    private int points = 10;
    private int health = 100;
    private int bullets = 1000;
    private int missiles = 10;
    private Color color = new Color(255, 10, 20);

    public player(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        useRelativeSize(BASE_WIDTH, BASE_HEIGHT);
    }

    @Override
    public void tick() {
        int red = Math.max(1, Math.min(255, getHealth() * 255 / 100));
        color = new Color(red, 100, 100);

        for (GameObject obstacle : handler.getCollidingObjects(this, List.of(ID.block, ID.FollowBloack))) {
            if (obstacle.id == ID.block) {
                health -= 2;
            } else {
                health -= 1;
            }
        }

        x += (int) velX;
        if (!jump) {
            y = scaleY(GROUND_Y);
        } else {
            if (y > scaleY(JUMP_LIMIT) && y <= scaleY(GROUND_Y)) {
                y -= scaleY(5);
            } else {
                y += scaleY(1);
            }
        }

        x = Math.max(0, Math.min(scaleX(Game.WIDTH - 35), x));
        y = Math.max(0, Math.min(scaleY(GROUND_Y), y));

        if (health < 0) {
            AudioPlayer.getSound("Game_Over").play();
            Game.state = Game.STATE.OVER;
        }
    }

    @Override
    public void render(Graphics g) {
        if (!over) {
            g.setColor(color);
            g.fillOval(x + scaleX(6), y - scaleY(20), scaleX(20), scaleY(20));
            g.fillRect(x, y, getWidth(), getHeight());
            g.fillRect(x + scaleX(6), y + getHeight(), scaleX(10), scaleY(20));
            g.fillRect(x + scaleX(18), y + getHeight(), scaleX(10), scaleY(20));
            g.drawRect(x, y, getWidth(), getHeight());

            if (shooting && bullets > 0) {
                AudioPlayer.getSound("bullet").play(30f, 100f);
                handler.add(new bullet(x + scaleX(8), y - scaleY(24), ID.bullet, handler));
                bullets--;
            }
        } else {
            g.drawString("GAME OVER ", scaleX(400), scaleY(300));
        }
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public int getBullets() { return bullets; }
    public void setBullets(int bullets) { this.bullets = bullets; }
    public int getMissiles() { return missiles; }
    public void setMissiles(int missiles) { this.missiles = missiles; }
    public void startShooting() { this.shooting = true; }
    public void stopShooting() { this.shooting = false; }
    public void jump() { this.jump = true; }
    public void returnJump() { this.jump = false; }

    public void shootMissile() {
        if (missiles > 0) {
            handler.add(new Missiles(x, y, ID.Missile, handler));
            missiles--;
        }
    }
}
