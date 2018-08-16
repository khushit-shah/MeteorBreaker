package com.meteor.breaker;

import org.newdawn.slick.Sound;

import java.awt.*;

/**
 * Created by Khushit on 5/20/2018.
 */
public class Missiles extends GameObject {
    private GameObject player;
    public Sound s;
    public Missiles(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        valy = 3;
        for (int i = 0; i < Handler.gameobj.size(); i++) {
            if(Handler.gameobj.get(i).id == ID.player){
                player = Handler.gameobj.get(i);
                break;
            }
            s = AudioPlayer.getSound("missile");
            s.play();
        }
    }

    @Override
    public void tick() {
        y -= valy;
        for (int i = 0; i < Handler.gameobj.size(); i++) {
            GameObject block = Handler.gameobj.get(i);
                if(block.id == ID.block || block.id == ID.FollowBloack )
                   if( Game.checkCollide(this,block)){
                    blast();
                }
        }

        if(y < -150){
            handler.remove(this);
            s.stop();
        }
    }

    private void blast() {
        for (int i = 0; i < 5; i++) {
            handler.add(new deathObj(x,y,ID.deathobj,handler));
        }
        for (int i = 0; i < Handler.gameobj.size(); i++) {
            GameObject temp = Handler.gameobj.get(i);
            if(temp.getBound().intersects(x-50,y-50,100,100) && temp.id != ID.player  && temp.id != ID.ground){
                handler.add(new deathObj(x,y,ID.deathobj,handler));
                handler.remove(temp);
                player.setPoints(player.getPoints() + 20);
            }

        }
        s.stop();
        AudioPlayer.getSound("missile_Blast").play();
        handler.remove(this);
    }
    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRoundRect(x,y,40,100,100,50);
        g.setColor(Color.green);
        g.fillRect(x,y+60,40,100);
        handler.add(new trails(x,y,ID.trials,handler,40,0.01f,Color.ORANGE));
        g.setColor(Color.black);
      g.drawRect(x-100,y-100,200,120);
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x,y,40,150);
    }
}
