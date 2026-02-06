package com.meteor.breaker;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Khushit on 5/17/2018.
 */

public class keyListener implements KeyListener {
    private GameObject player;
    private Handler handler;
    public keyListener(Handler handler){
        this.handler = handler;
    }
    public void keyTyped(KeyEvent e) {
        synchronized (e) {
            for(int i=0;i<Handler.gameobj.size(); i++)
            {
                if(Handler.gameobj.get(i).id == ID.player){
                    player = Handler.gameobj.get(i);
                    break;
                }
            }
            char keycode = e.getKeyChar();
            if(keycode  == 'w') player.setvalY((int) (player.valy()-5));
            if(keycode  == 's') player.setvalY((int) (player.valy()+5));
            if(keycode  == 'a') player.setvalX((int) (player.valx()-5));
            if(keycode  == 'd') player.setvalX((int) (player.valx()+5));
            if(keycode ==  'm') player.shootMissile();
            if(keycode == ' ') player.strshoot(handler);
            //if(keycode == 'h') {
             //   if(player.getPoints() >= 1000){
               //     player.setHealth(player.getHealth() + 100);
                //    player.setPoints(player.getPoints() - 1000);
                //}
           // }
            //System.out.println(keycode + "\n" + e);
            if(keycode == 'b') {
                if(player.getPoints() >= 700){
                    player.setBullets(player.getBullets() + 1000);
                    player.setPoints(player.getPoints() - 700);
                }
            }
            if(keycode  == '\n') player.jump();
        }

    }
    public void keyPressed(KeyEvent e) {
        synchronized (e) {
            for(int i=0;i<Handler.gameobj.size(); i++)
            {
                if(Handler.gameobj.get(i).id == ID.player){
                    player = Handler.gameobj.get(i);
                    break;
                }
            }
            char keycode = e.getKeyChar();
            if(keycode  == 'w') player.setvalY((int) (player.valy()-5));
            if(keycode  == 's') player.setvalY((int) (player.valy()+5));
            if(keycode  == 'a') player.setvalX((int) (player.valx()-5));
            if(keycode  == 'd') player.setvalX((int) (player.valx()+5));
            if(keycode == ' ') player.strshoot(handler);
           // if(keycode == 'h') {
             //   if(player.getPoints() >= 10000){
               //     player.setHealth(player.getHealth() + 100);
                 //   player.setPoints(player.getPoints() - 10000);
                //}
            //}
            if(keycode == 'b') {
                if(player.getPoints() >= 70000){
                    player.setBullets(player.getBullets() + 1000);
                    player.setPoints(player.getPoints() - 7000);
                }
            }
            if(keycode == KeyEvent.VK_ENTER) player.jump();

        }
    }
    public void keyReleased(KeyEvent e) {
        synchronized (e) {
            for(int i=0;i<Handler.gameobj.size(); i++)
            {
                if(Handler.gameobj.get(i).id == ID.player){
                    player = Handler.gameobj.get(i);
                    break;
                }
            }
            char keycode = e.getKeyChar();
            if(keycode  == 'w') player.setvalY(0);
            if(keycode  == 's') player.setvalY(0);
            if(keycode  == 'a') player.setvalX(0);
            if(keycode  == 'd') player.setvalX(0);
            if(keycode == ' ') player.stopshooting();
            if(keycode  == '\n') player.returnJump();

        } }
}
