package com.meteor.breaker;

import java.awt.Graphics;
import java.awt.Rectangle;

public class RootGameObject extends GameObject {

    public RootGameObject(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
    }

    @Override
    public void render(Graphics g) {}

    @Override
    public void tick() {}

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }
}
