package com.meteor.breaker;

import com.meteor.breaker.objects.player;
import java.awt.*;

public class HOD {
    private final Handler handler;
    private int level = 1;
    private int points = 0;
    private int bullets = 1000;
    private int counter;

    public HOD(Handler handler) {
        this.handler = handler;
    }

    public void render(Graphics g) {
        player player = (player) handler.findFirstById(ID.player).orElse(null);
        if (player == null || Game.state != Game.STATE.PLAY) {
            return;
        }

        g.setColor(Color.BLACK);
        g.drawString("level: " + level(), 10, 15);
        g.drawString("points:" + player.getPoints(), 10, 30);
        g.drawString("bullets:" + player.getBullets(), 10, 45);
        g.drawString("Missiles:" + player.getMissiles(), 10, 60);
        g.drawString("Health:" + player.getHealth(), 10, 75);
    }

    public void tick() {
        if (Game.state == Game.STATE.PLAY) {
            counter++;
            if (counter % 1000 == 0) {
                level++;
            }
        }
    }

    public void setLevel(int level) { this.level = level; }
    public int level() { return level; }
    public int points() { return points; }
    public int bullets() { return bullets; }
    public void setBullets(int bullets) { this.bullets = bullets; }
    public void setPoints(int points) { this.points = points; }
}
