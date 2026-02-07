package com.meteor.breaker.objects;

import com.meteor.breaker.Handler;
import com.meteor.breaker.ID;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class trails extends GameObject {
    private final float life;
    private final Color color;
    private float alpha = 1f;

    public trails(int x, int y, ID id, Handler handler, int width, float life, Color color) {
        super(x, y, id, handler);
        setRelativeSizeFromPixels(width, scaleY(32));
        this.life = life;
        this.color = color;
    }

    @Override
    public void tick() {
        if (alpha > life) {
            alpha -= (life - 0.00001f);
        } else {
            handler.remove(this);
        }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.setColor(color);
        g.fillRect(x, y, getWidth(), getHeight());
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }
}
