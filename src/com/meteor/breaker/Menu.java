package com.meteor.breaker;

import com.meteor.breaker.objects.Clouds;
import com.meteor.breaker.objects.FollowBlock;
import com.meteor.breaker.objects.Ground;
import com.meteor.breaker.objects.background;
import com.meteor.breaker.objects.player;
import java.awt.*;
import java.util.Random;
import java.util.logging.Logger;

public class Menu {
    private static final Logger LOGGER = Log.getLogger(Menu.class);

    private final Random random = new Random();
    private final Handler handler;
    private final HOD hod;
    private Game.STATE state;
    private int count = 0;
    private int gameOverRenderCount = 0;

    public Menu(Handler handler, HOD hod) {
        this.handler = handler;
        this.hod = hod;
    }

    public void tick(Game.STATE state) {
        this.state = state;
        count++;
        if (count % 50 == 0) {
            handler.add(new background(random.nextInt(800), 0, ID.background, handler));
        }
    }

    public void render(Graphics g) {
        if (state == Game.STATE.START) {
            renderMenu(g, "Meteor Crusher");
            return;
        }

        if (state == Game.STATE.OVER) {
            gameOverRenderCount++;
            if (gameOverRenderCount < 3) {
                handler.clear();
                handler.add(new Clouds(200, 200, ID.Cloud, handler, Color.lightGray));
                handler.add(new Ground(0, 0, ID.ground, handler));
            }
            handler.add(new background(random.nextInt(800), 0, ID.background, handler));
            renderMenu(g, "GAME OVER:-(");
        }
    }

    private void renderMenu(Graphics g, String title) {
        g.setColor(Color.black);
        g.setFont(new Font("Times New Roman", Font.BOLD, 60));
        g.drawString(title, 200, 100);
        g.drawRect(250, 200, 300, 90);
        g.drawString("Play!", 330, 270);
        g.drawRect(250, 300, 300, 90);
        g.drawString("Setting/shop", 252, 370);
        g.drawRect(250, 400, 300, 90);
        g.drawString("Help!", 330, 460);
    }

    public void checkMouse(int x, int y) {
        if (state == Game.STATE.PLAY) {
            return;
        }

        if (x > 250 && x < 550 && y > 200 && y < 290) {
            LOGGER.info("Starting a new game from menu click");
            player player = new player(300, 300, ID.player, handler);
            player.setPoints(100);
            player.setHealth(100);
            player.setBullets(1000);

            handler.add(player);
            handler.tick();
            handler.add(new FollowBlock(0, 0, ID.FollowBloack, handler));
            handler.tick();

            hod.setLevel(1);
            hod.setBullets(1000);
            hod.setPoints(10);
            Game.state = Game.STATE.PLAY;
            gameOverRenderCount = 0;
            state = Game.STATE.PLAY;
        }
    }
}
