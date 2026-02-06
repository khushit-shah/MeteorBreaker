package com.meteor.breaker;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Created by Khushit on 5/17/2018.
 */
public abstract class GameObject {

    protected GameObject parent;

    protected int points;
    protected int x;
    protected int y;
    protected int bullet;
    protected int Health;

    protected int width;
    protected int height;

    protected float relativeWidth;
    protected float relativeHeight;

    protected int Missiles;
    protected boolean shooting = false;
    protected Handler handler;
    protected float valx;
    protected float valy;
    public ID id;
    protected boolean jump = false;

    private int cachedCalculatedWidth;
    private int cachedCalculatedHeight;

    public GameObject(int x, int y, ID id, Handler handler) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.handler = handler;
        this.parent = null;
        this.relativeWidth = 1f;
        this.relativeHeight = 1f;
    }

    public GameObject(int x, int y, ID id, Handler handler, GameObject parent) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.handler = handler;
        this.parent = parent;
    }

    public GameObject(
        int x,
        int y,
        ID id,
        Handler handler,
        float relativeWidth,
        float relativeHeight,
        GameObject parent
    ) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.handler = handler;
        this.parent = parent;
        this.relativeWidth = relativeWidth;
        this.relativeHeight = relativeHeight;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public float valx() {
        return valx;
    }

    public float valy() {
        return valy;
    }

    public void setvalX(int valx) {
        this.valx = valx;
    }

    public void setvalY(int valy) {
        this.valy = valy;
    }

    public void strshoot(Handler handler) {
        shooting = true;
    }

    public void stopshooting() {
        shooting = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        if (parent == null) return width;
        return cachedCalculatedWidth;
    }

    public int getHeight() {
        if (parent == null) return height;
        return cachedCalculatedHeight;
    }

    public void setWidth(int width) {
        assert (parent == null);
        this.width = width;
    }

    public void setHeight(int height) {
        assert (parent == null);
        this.height = height;
    }

    public void calculateDimensions() {
        if (parent == null) {
            cachedCalculatedWidth = width;
            cachedCalculatedHeight = height;
        } else {
            cachedCalculatedWidth = Math.round(
                parent.getWidth() * relativeWidth
            );
            cachedCalculatedHeight = Math.round(
                parent.getHeight() * relativeHeight
            );
        }
    }

    public int getHealth() {
        return Health;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int point) {
        points = point;
    }

    public int getBullets() {
        return bullet;
    }

    public void setHealth(int Health) {
        this.Health = Health;
    }

    public void setBullets(int bullets) {
        this.bullet = bullets;
    }

    public abstract Rectangle getBound();

    public void shootMissile() {
        if (Missiles > 0) {
            handler.add(new Missiles(x, y, ID.Missile, handler));
            Missiles--;
        }
    }

    public int getMissiles() {
        return Missiles;
    }

    public void setMissiles(int Missiles) {
        this.Missiles = Missiles;
    }

    public void jump() {
        jump = true;
    }

    public void returnJump() {
        jump = false;
    }
}
