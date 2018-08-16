package com.meteor.breaker;


import java.awt.*;
/**
 * Created by Khushit on 5/17/2018.
 */
public class player extends GameObject {
    private boolean over = false;
    private Color color = new Color(255,10,20) ;
    private int count = 0;
    player(int x, int y, ID id,Handler handler){
        super(x,y,id,handler);
        valx = 0;
        valy = 10;
        points = 10;
        Health = 100;
        bullet = 1000;
        Missiles = 10;
    }
    public void tick() {
        int red = getHealth() * 255/100;
        if(red >= 255)
            red = 255;
        if(red  <= 0)
            red = 1;
        color = new Color(red,100,100);
        for(int i = 0; i < Handler.gameobj.size(); i++)
        {
            if(Handler.gameobj.get(i).id == ID.block ) {
                if(Game.checkCollide(this, Handler.gameobj.get(i))){
                    setHealth(getHealth() - 2);
                    color = new Color(red,100,100);
                }

            }
            if(Handler.gameobj.get(i).id == ID.FollowBloack) {
                if(Game.checkCollide(this, Handler.gameobj.get(i))){
                    setHealth(getHealth() - 1);
                    color = new Color(red,100,100);
                }

            }
        }

        x += valx;
            if(!jump)
                 y = 450;
            if(jump) {
                System.out.println(y);
                int limit = 350;
                if (y > limit  && y <= 450) {
                    valy = 5;
                    System.out.println(y);
                    y -= valy;
                    System.out.println("after valy-y"+y);
                }
                if (y <= limit) {
                    valy = 1;
                    System.out.println(y);
                    y += valy;
                    System.out.println(y);
                }
             //   if (y >= 450) {
               //     System.out.println(y);
                 //   y = 450;
                 //}
            }
         if(x>=Game.WIDTH-35) {
             x = Game.WIDTH - 35;
           // color  = Color.MAGENTA;
         }
         if(x<=0) {
             x = 0;
         //   color = Color.green;
         }
        if(y<=0){
            y=0;
           // color = Color.pink;
        }
        if(y >= 450) {
            y = 450;
          //  color = Color.red;
         }

        if(getHealth() < 0 ) {
           AudioPlayer.getSound("Game_Over").play();
            Game.state = Game.STATE.OVER;
        }
    }

    @Override
    public void render(Graphics g) {
        if(!over) {
            int bulletcount = 0;
            //render player
            g.setColor(Color.red);
            g.setColor(color);
            g.fillOval(x+6,y-20,20,20);
            g.fillRect(x, y, 32, 32);
            g.fillRect(x+ 6,y+32,10,20);
            g.fillRect(x+18,y+32,10,20);
            g.drawRect(x,y,32,32);
            //System.out.println(getBound());

            if (shooting) {
                if(bullet > 0) {
                    AudioPlayer.getSound("bullet").play((float)30,(float)100);
                    handler.add(new bullet(x + 8, y - 24, ID.bullet, handler));
                    bullet -= 1;
                }
            }
        }
        else{
            g.drawString("GAME OVER ",400,300);
        }
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x,y,32,32);
    }
}