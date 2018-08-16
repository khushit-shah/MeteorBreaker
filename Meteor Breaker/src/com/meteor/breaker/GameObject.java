package com.meteor.breaker;

import java.awt.*;

/**
 * Created by Khushir on 5/17/2018.
 */
public abstract class GameObject {
    protected int points;
    protected int x;
    protected int y;
    protected int bullet;
    protected int Health;
    protected int width;
    protected int Missiles;
    protected boolean shooting = false;
    protected Handler handler;
    protected float  valx;
    protected float valy;
    public ID id;
    protected boolean jump = false    ;

    public GameObject(int x, int y,ID id,Handler handler){
        this.id = id;
        this.x = x;
        this.y = y;
        this.handler = handler;
    }

    public abstract void tick();
    public  abstract void render(Graphics g);
    public float valx(){
        return  valx;
    }
    public float valy(){
        return  valy;
    }
    public void setvalX(int valx){
        this.valx = valx;
    }
    public void setvalY(int valy){
        this.valy = valy;
      //  System.out.println(valy);
    }
    public void strshoot(Handler handler){
        shooting = true;
    }
    public void stopshooting(){
        shooting = false;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public int width(){
        return width;
    }
    public int getHealth(){
        return Health;
    }
    public int getPoints(){
        return points;
    }
    public void setPoints(int point){
        points = point;
    }

    public int getBullets() {
        return bullet;
    }
    public void setHealth(int Health){
        this.Health = Health;
    }

    public void setBullets(int bullets) {
        this.bullet = bullets;
    }
    public abstract Rectangle getBound();

    public void shootMissile() {
        if(Missiles > 0){
            handler.add(new Missiles(x,y,ID.Missile,handler));
            Missiles--;
        }
    }

    public int getMissiles(){
        return Missiles;
    }
    public void setMissiles(int Missiles){
        this.Missiles = Missiles;
    }

    public void jump() {
        System.out.println("Player is going to jump");
         jump = true;
    }

    public void returnJump(){
        jump = false;
    }
}