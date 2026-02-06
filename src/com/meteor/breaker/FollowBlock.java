package com.meteor.breaker;

import java.awt.*;
import java.util.Random;

public class FollowBlock extends GameObject{

    private static final int MIN_WIDTH = 40;

    private Random r ;

    private Boolean deathEffect = false;

    private int count = 0;
    private int speed,diffy,diffx;
    private float distance;
    private GameObject player;

    public FollowBlock(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        valy = 3;
        Health = 200;
        r = new Random();
        valx = 2;
        valy = 3;
        //      x = checkPrevious(x);

        width = calculateWidth(x,800);
        for(int i = 0; i< Handler.gameobj.size(); i++){
            if(Handler.gameobj.get(i).id == ID.player){
                player = Handler.gameobj.get(i);
                break;
            }
        }
    }

    /*private int checkPrevious(int x) {
        for(int i = 0; i< handler.gameobj.size(); i++){
            if(handler.gameobj.get(i).id == ID.block){
                if(checkCollide()){
                  return x+50;
                    //  checkPrevious(x+ 20) ;
                }
                else{
                    return x+2;
                }
            }
        }
        return x;
    }
*/
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
        diffx = player.getX() - x;
        diffy = player.getY()  - y;
        distance = (float) Math.sqrt(Math.pow(diffx,2) + Math.pow(diffy,2));
        x += (diffx/distance)*valx;
        y += (diffy/distance)*valy;
        if (y >= 650) handler.remove(this);
        for (int i = 0; i < Handler.gameobj.size(); i++) {
            GameObject temp = Handler.gameobj.get(i);
            if (temp.id == ID.bullet) {
                if (Game.checkCollide(this,temp)) {
                    Health -= 20;
                    handler.remove(temp);
                }
            }

            if (Health < 0) {
                // System.out.println("Health of block is zero");
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
            g.setColor(Color.DARK_GRAY);
            g.fillOval(x,y,width,30);
            g.setColor(Color.white);
            g.drawString(String.valueOf(this.getHealth()),x,y+15);
            handler.add(new trails(x,y,ID.trials,handler,width,0.02f,Color.RED));
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
// 1600  