package com.meteor.breaker;

import java.awt.*;
import java.util.Random;

/**
 * Created by Khushit on 5/18/2018.
 */
public class background extends GameObject{
    private int width;
    private Random random = new Random();
    public background(int x, int y , ID id,Handler handler){
        super(x,y,id,handler);
        valy = 2;
        width = getWidth(x,800);
    }
    public void tick() {
        y += valy;
        if(y>650)
            handler.remove(this);
    }
    public void render(Graphics g) {
        synchronized (g) {
            g.setColor(Color.white);
            g.fillRoundRect(x,y,width,10,1,1);
        }
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x,y,width,10);

    }

    public int getWidth(int x,int width1){
        int wid;
        while(true){
            if((width - x) > 35) {
                wid = random.nextInt((width-x-35));
                break;
            }
            else{
             x -= 10;
            }
        }
        return wid;
    }
}
