package com.meteor.breaker;

import java.awt.*;

public class trails extends GameObject {
    private float life;
    private Color color;
    private float alpha1 = 1;
    public trails(int x, int y, ID id, Handler handler,int width,float life,Color color) {
        super(x, y, id, handler);
        this.life = life;
        this.width = width;
        this.color = color;
    }
    public void tick() {
        if(alpha1 > life)
            alpha1 -= (life-0.00001f);
        else
            handler.remove(this);
    }
    private AlphaComposite makeTrial(float alpha){
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }
    public void render(Graphics g) {
        synchronized (g) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setComposite(makeTrial(alpha1));
            g.setColor(color);
            g.fillRect(x,y,width,32);
            g2d.setComposite(makeTrial(1f));
        }
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x,y,width,32);
    }
}