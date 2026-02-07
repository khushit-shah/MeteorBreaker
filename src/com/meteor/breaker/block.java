package com.meteor.breaker;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class block extends GameObject{

    private static final int MIN_WIDTH = 40;

    private Random r ;

    private Boolean deathEffect = false;

    private int count = 0;

    private GameObject player;

    public block(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);

        valy = 3;
        Health = 200;
        r = new Random();

        width = calculateWidth(x,800);
        for(int i = 0; i< Handler.gameobj.size(); i++){
            if(Handler.gameobj.get(i).id == ID.player){
                player = Handler.gameobj.get(i);
                break;
            }
        }
    }

    private int calculateWidth(int x, int MAX_WIDTH) {

        while(true) {
            int wid = r.nextInt(25);
            if ((MAX_WIDTH - (x+MIN_WIDTH+wid)) <= 35) {
                 x -= 10;
                wid = r.nextInt(25);
            }
            if((MAX_WIDTH - (x+MIN_WIDTH)+wid) >= 35){
               // System.out.println("wid+MIN_WIDTH = " + (wid+MIN_WIDTH) + " x = " + x);
                return wid+MIN_WIDTH;
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

        List<GameObject> collidingBullets = Handler.getCollidingObjects(this, List.of(new ID[]{ID.bullet}));


        for (GameObject bullet : collidingBullets) {
            Health -= 20;
            Handler.remove(bullet);

            if (Health < 0) {
                deathEffect = true;
                if(Health < 0) {
                    count++;
                    if(count < 3)
                        player.setPoints(player.getPoints() + 100);
                }
            }

        }
    }
    public void render(Graphics g) {
        synchronized (g) {
            g.setColor(Color.YELLOW);
            g.fillOval(x,y,width,30);
            g.setColor(Color.black);
            g.drawString(String.valueOf(this.getHealth()),x,y+15);
            handler.add(new trails(x,y,ID.trials,handler,width,0.02f,Color.YELLOW));
            if(deathEffect){
                count++;
                handler.add(new deathObj(x,y,ID.deathobj,handler));
                if(count > 5)
                    handler.remove(this);
            }
        }
    }
    public Rectangle getBound() {
        return new Rectangle(x,y,width,30);
    }
}
