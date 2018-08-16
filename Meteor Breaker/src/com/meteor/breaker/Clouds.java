package com.meteor.breaker;

import java.awt.*;
import java.util.Random;

/**
 * Created by Khushit on 5/19/2018.
 */
public class Clouds extends GameObject {
    private Random random;
    private Color color;
    public Clouds(int x, int y, ID id, Handler handler,Color color) {
        super(x, y, id, handler);
        this.color = color;
        random = new Random();
    }

    public void tick() {
        int count = 0;
            x++;
            for(int i = 0; i< Handler.gameobj.size(); i++)
            {
                GameObject temp = Handler.gameobj.get(i);
                if(temp.id == ID.Cloud)
                    count++;
                if(count >2 && temp.id == ID.Cloud)
                {
                    handler.remove(temp);
                }
            }
            if(x > 850){
                if(random.nextInt(3) == 0) {
                    handler.add(new Clouds(-100, random.nextInt(300), ID.Cloud, handler, Color.white));
                    handler.add(new Clouds(0, random.nextInt(250), ID.Cloud, handler, Color.lightGray));
                    handler.remove(this);
                }else if(random.nextInt(3) == 1) {
                    handler.add(new Clouds(-100, random.nextInt(300), ID.Cloud, handler, Color.GRAY));
                    handler.add(new Clouds(0, random.nextInt(250), ID.Cloud, handler, Color.blue));
                    handler.remove(this);
                }
                else if(random.nextInt(3) == 2) {
                    handler.add(new Clouds(-100, random.nextInt(300), ID.Cloud, handler, Color.white));
                    handler.add(new Clouds(0, random.nextInt(250), ID.Cloud, handler, Color.blue));
                    handler.remove(this);
                }
            }
    }

    public void render(Graphics g) {
            g.setColor(color);
            g.fillOval(x,y,50,50);
            g.fillOval(x+25,y,50,50);
            g.fillOval(x+50,y,50,50);
            g.fillOval(x+75,y,50,50);
   //         System.out.println("in Rendering of Clouds");
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x,y,50,50);
    }
}
