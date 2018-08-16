package com.meteor.breaker;

import java.util.Random;

/**
 * Created by Khushit on 5/18/2018.
 */
public class Spawner {
    private Random random = new Random();
    private Handler handler;
    private HOD hod;
    private int level,bullets,points,playerX;
    private GameObject player;
    private int count = 0,count2 = 0;
    private int count3 = 0;

    public Spawner(Handler handler,HOD hod){
        this.handler = handler;
        this.hod = hod;
        level = hod.level();
        bullets = hod.bullets();
        points = hod.points();

    }
    public void tick(){
        for(int i = 0; i < Handler.gameobj.size(); i++)
        {
            if(Handler.gameobj.get(i).id == ID.player)
            {
                player = Handler.gameobj.get(i);
                break;
            }
        }
        handler.add(new background(random.nextInt(850),0,ID.background,handler));
        analyse(level,player.getBullets(),player.getPoints());
    }
    public void analyse(int level1, int bullets, int points){
        if(hod.level() >  level) {
            level = hod.level();
            System.out.println(level);
            addRow(level,bullets);
        }
        else{
            count2++;
           if(count2 % 1000 == 0) {
                playerX = player.getX();
            }
            if (player.getX() > playerX + random.nextInt(10000)) {
                addRandom();
            }
        }

    }

    private void addRandom() {
        if (count3 <= 20) {
            GameObject temp = new block(random.nextInt(800), random.nextInt(300), ID.block, handler);
            temp.setHealth(random.nextInt(250));
            handler.add(temp);
            count3++;
        }
        else{
            GameObject temp = new FollowBlock(random.nextInt(800),0, ID.FollowBloack, handler);
            temp.setHealth(random.nextInt(300));
            handler.add(temp);
            count3=0;
        }
    }

    public void addRow(int level,int bullets) {
        if (level % 10 == 0) {
            for(int i = 0 ; i<10 ; i++){
                GameObject temp = new FollowBlock(random.nextInt(800),0,ID.FollowBloack,handler);
                temp.setHealth(random.nextInt(200));
                handler.add(temp);
            }
        }
        else if(level <= 10 && level%2 == 0){
            for(int i = 0 ; i<8 ; i++){
                GameObject temp = new block(random.nextInt(800),0,ID.block,handler);
                temp.setHealth(random.nextInt(250));
                handler.add(temp);
            }
        }
        else if(level <= 10 && level%3 == 0){
            for(int i = 0 ; i<9 ; i++){
                GameObject temp = new block(random.nextInt(800),0,ID.block,handler);
                handler.add(new FollowBlock(random.nextInt(850),0,ID.FollowBloack,handler));
                temp.setHealth(random.nextInt(400));
                handler.add(temp);
            }
        }
        else if(level <= 10){
            for(int i = 0 ; i<5 ; i++){
                GameObject temp = new block(random.nextInt(800),0,ID.block,handler);
                temp.setHealth(random.nextInt(500));
                handler.add(temp);
            }
        }
        else if( level >= 10 && level%2==0)
        {
            for(int i = 0 ; i<10 ; i++){
                GameObject temp = new block(800,0,ID.block,handler);
                temp.setHealth(random.nextInt(350));
                handler.add(temp);
            }
        }
        else if( level >= 10 && level % 3 == 0)
        {
            for(int i = 0 ; i<8 ; i++){
                GameObject temp = new block(random.nextInt(800),0,ID.block,handler);
                temp.setHealth(random.nextInt(700));
                handler.add(new FollowBlock(random.nextInt(850),0,ID.FollowBloack,handler));
                handler.add(new FollowBlock(random.nextInt(850),0,ID.FollowBloack,handler));

                handler.add(temp);
            }
        }

    }

}