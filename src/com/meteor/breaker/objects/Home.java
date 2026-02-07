package com.meteor.breaker.objects;

import com.meteor.breaker.Handler;
import com.meteor.breaker.ID;
import java.awt.*;

public class Home extends GameObject {
    public Home(int x, int y, ID id, Handler handler, String type) {
        super(x, y, id, handler);
    }

    @Override
    public void tick() {}

    @Override
    public void render(Graphics g) {}

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, width, height);
    }
}
