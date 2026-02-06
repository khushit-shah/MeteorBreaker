package com.meteor.breaker;

import java.awt.*;
import java.util.Random;


/**
 * Created by Khushit on 5/19/2018.
 */
public class Menu {
    private Random random;
    private Game.STATE state;
    private Handler handler;
    private int count = 0,coutn1 = 0;
    private HOD hod;
    private boolean newGame = true;

    public Menu(Handler handler,HOD hod) {
        this.hod = hod;
        random = new Random();
        this.handler = handler;
    }

    public void tick(Game.STATE state){
        this.state = state;
        if(count % 50 == 0)
            handler.add(new background(random.nextInt(800),0,ID.background,handler));
    }

    public void render(Graphics g) {
        if (state == Game.STATE.START) {
            g.setColor(Color.black);
            g.setFont(new Font("Times New Roman", 100, 60));
            g.drawString("Meteor Crusher", 200, 100);
            g.setColor(Color.black);
            g.drawRect(250, 200, 300, 90);
            g.drawString("Play!", 330, 270);
            g.drawRect(250, 300, 300, 90);
            g.drawString("Setting/shop", 252, 370);
            g.drawRect(250, 400, 300, 90);
            g.drawString("Help!", 330, 460);

        }
        if (state == Game.STATE.OVER) {
            coutn1++;
            g.setColor(Color.black);
            if(coutn1 < 3 && newGame) {
                for (int i = 0; i < Handler.gameobj.size(); i++) {
                    handler.remove(Handler.gameobj.get(i));
                }
                handler.gameobj.remove();
                handler.add(new Clouds(200,200,ID.Cloud,handler,Color.lightGray));
                handler.add(new Ground(0,0,ID.ground,handler));

            }
            handler.add(new background(random.nextInt(800),0,ID.background,handler));
            g.setFont(new Font("Times New Roman", 100, 60));
            g.drawString("GAME OVER:-(", 200, 100);
            g.setColor(Color.black);
            g.drawRect(250, 200, 300, 90);
            g.drawString("Play!", 330, 270);
            g.drawRect(250, 300, 300, 90);
            g.drawString("Setting/shop", 252, 370);
            g.drawRect(250, 400, 300, 90);
            g.drawString("Help!", 330, 460);
        }
    }
    public void checkMouse(int x, int y) {
        if (state != Game.STATE.PLAY) {
            if (x > 250 && x < 550) if (y > 200 && y < 290) {
                GameObject player = new player(300, 300, ID.player, handler);
                player.setPoints(100);
                player.setHealth(100);
                player.setBullets(1000);
                handler.add(player);
                handler.add(new FollowBlock(0,0,ID.FollowBloack,handler));
                hod.setLevel(1);
                hod.setBullets(1000);
                hod.setPoints(10);
                Game.state = Game.STATE.PLAY;
                coutn1 = 0;
                state = Game.STATE.PLAY;
            }
        }
    }
}
