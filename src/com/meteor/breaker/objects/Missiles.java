package com.meteor.breaker.objects;

import com.meteor.breaker.AudioPlayer;
import com.meteor.breaker.Handler;
import com.meteor.breaker.ID;
import java.awt.*;
import org.newdawn.slick.Sound;

public class Missiles extends GameObject {
    private final player player;
    private final Sound missileSound;

    public Missiles(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        useRelativeSize(40, 150);
        velY = 3;
        player = (player) handler.findFirstById(ID.player).orElse(null);
        missileSound = AudioPlayer.getSound("missile");
        missileSound.play();
    }

    @Override
    public void tick() {
        y -= (int) velY;

        if (!handler.getCollidingObjects(this, java.util.List.of(ID.block, ID.FollowBloack)).isEmpty()) {
            blast();
            return;
        }

        if (y < -150) {
            handler.remove(this);
            missileSound.stop();
        }
    }

    private void blast() {
        for (int i = 0; i < 5; i++) {
            handler.add(new deathObj(x, y, ID.deathobj, handler));
        }
        for (GameObject temp : handler.getObjectsSnapshot()) {
            if (temp.getBound().intersects(x - 50, y - 50, 100, 100) && temp.id != ID.player && temp.id != ID.ground) {
                handler.add(new deathObj(x, y, ID.deathobj, handler));
                handler.remove(temp);
                if (player != null) {
                    player.setPoints(player.getPoints() + 20);
                }
            }
        }

        missileSound.stop();
        AudioPlayer.getSound("missile_Blast").play();
        handler.remove(this);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRoundRect(x, y, 40, 100, 100, 50);
        g.setColor(Color.green);
        g.fillRect(x, y + 60, 40, 100);
        handler.add(new trails(x, y, ID.trials, handler, 40, 0.01f, Color.ORANGE));
        g.setColor(Color.black);
        g.drawRect(x - 100, y - 100, 200, 120);
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }
}
