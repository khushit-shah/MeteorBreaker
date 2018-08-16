package com.meteor.breaker;

import java.awt.*;

/**
 * Created by Khushit on 5/18/2018.
 */
public class HOD {
    private final Handler handler;
    public int level = 1;
    public int points = 0;
    public int bullets = 1000;
    private int counter;
    private GameObject player;
    public HOD(Handler handler){
        counter = 0;
        this.handler = handler;
    }
    public void render(Graphics g){
        for(int i = 0; i< Handler.gameobj.size(); i++){
            if(Handler.gameobj.get(i).id == ID.player){
                player = Handler.gameobj.get(i);
                break;
            }
        }
        synchronized (g) {
            // System.out.println("in HOD render");
            if(Game.state == Game.STATE.PLAY) {
                g.setColor(Color.BLACK);
                g.drawString("level: " + level(), 10, 15);
                g.drawString("points:" + player.getPoints(), 10, 30);
                g.drawString("bullets:" + player.getBullets(), 10, 45);
                g.drawString("Missiles:" + player.getMissiles(), 10, 60);
                g.drawString("Health:" + player.getHealth(), 10, 75);
            }

        }

    }
    public void tick(){
        if(Game.state == Game.STATE.PLAY) {
            counter++;
            tickLevel();
        }
    }
    public void tickLevel(){
        if(counter % 1000 == 0)
                level++;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public int level(){
        return level;
    }
    public int points(){
      return points;
    }
    public int bullets(){
        return bullets;
    }
    public void setBullets(int bullets){
        this.bullets = bullets;
    }
    public void setPoints(int points){
        this.points = points;
    }
}