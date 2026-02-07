package com.meteor.breaker.objects;

import com.meteor.breaker.Game;
import com.meteor.breaker.Handler;
import com.meteor.breaker.ID;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
    private static final float BASE_WINDOW_WIDTH = 800f;
    private static final float BASE_WINDOW_HEIGHT = 600f;

    protected final Handler handler;
    public final ID id;

    protected GameObject parent;
    protected int x;
    protected int y;
    protected float velX;
    protected float velY;

    protected int width;
    protected int height;
    protected float relativeWidth = 1f;
    protected float relativeHeight = 1f;

    private int calculatedWidth;
    private int calculatedHeight;

    protected GameObject(int x, int y, ID id, Handler handler) {
        this(x, y, id, handler, null, 1f, 1f);
    }

    protected GameObject(int x, int y, ID id, Handler handler, GameObject parent, float relativeWidth, float relativeHeight) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.handler = handler;
        this.parent = parent != null ? parent : Game.rootGameObject;
        this.relativeWidth = relativeWidth;
        this.relativeHeight = relativeHeight;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBound();

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public float getVelX() { return velX; }
    public float getVelY() { return velY; }
    public void setVelX(float velX) { this.velX = velX; }
    public void setVelY(float velY) { this.velY = velY; }

    public int getWidth() { return parent == null ? width : calculatedWidth; }
    public int getHeight() { return parent == null ? height : calculatedHeight; }

    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }

    public void setParent(GameObject parent) {
        this.parent = parent != null ? parent : Game.rootGameObject;
    }

    protected void useRelativeSize(int baseWidth, int baseHeight) {
        this.width = baseWidth;
        this.height = baseHeight;
        if (parent != null && parent.getWidth() > 0 && parent.getHeight() > 0) {
            this.relativeWidth = (float) baseWidth / parent.getWidth();
            this.relativeHeight = (float) baseHeight / parent.getHeight();
        }
    }

    protected int scaleX(int pixels) {
        if (parent == null || parent.getWidth() <= 0) {
            return pixels;
        }
        return Math.max(1, Math.round(pixels * (parent.getWidth() / BASE_WINDOW_WIDTH)));
    }

    protected int scaleY(int pixels) {
        if (parent == null || parent.getHeight() <= 0) {
            return pixels;
        }
        return Math.max(1, Math.round(pixels * (parent.getHeight() / BASE_WINDOW_HEIGHT)));
    }

    protected void setRelativeSizeFromPixels(int pixelWidth, int pixelHeight) {
        this.width = pixelWidth;
        this.height = pixelHeight;
        if (parent != null) {
            relativeWidth = (float) pixelWidth / Math.max(1, parent.getWidth());
            relativeHeight = (float) pixelHeight / Math.max(1, parent.getHeight());
        }
    }

    public void calculateDimensions() {
        if (parent == null) {
            calculatedWidth = width;
            calculatedHeight = height;
            return;
        }

        calculatedWidth = Math.max(1, Math.round(parent.getWidth() * relativeWidth));
        calculatedHeight = Math.max(1, Math.round(parent.getHeight() * relativeHeight));
    }
}
