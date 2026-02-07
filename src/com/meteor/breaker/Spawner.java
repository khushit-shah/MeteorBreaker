package com.meteor.breaker;

import com.meteor.breaker.objects.FollowBlock;
import com.meteor.breaker.objects.GameObject;
import com.meteor.breaker.objects.background;
import com.meteor.breaker.objects.block;
import com.meteor.breaker.objects.player;
import java.util.Random;

public class Spawner {
    private final Random random = new Random();
    private final Handler handler;
    private final HOD hod;
    private int level;
    private int playerX;
    private player player;
    private int count2 = 0;
    private int count3 = 0;

    public Spawner(Handler handler, HOD hod) {
        this.handler = handler;
        this.hod = hod;
        this.level = hod.level();
    }

    public void tick() {
        player = (player) handler.findFirstById(ID.player).orElse(null);
        if (player == null) {
            return;
        }

        handler.add(new background(random.nextInt(850), 0, ID.background, handler));
        analyse(player.getBullets());
    }

    public void analyse(int bullets) {
        if (hod.level() > level) {
            level = hod.level();
            addRow(level, bullets);
        } else {
            count2++;
            if (count2 % 1000 == 0) {
                playerX = player.getX();
            }
            if (player.getX() > playerX + random.nextInt(10000)) {
                addRandom();
            }
        }
    }

    private void addRandom() {
        if (count3 <= 20) {
            block temp = new block(random.nextInt(800), random.nextInt(300), ID.block, handler);
            temp.setHealth(random.nextInt(250));
            handler.add(temp);
            count3++;
        } else {
            FollowBlock temp = new FollowBlock(random.nextInt(800), 0, ID.FollowBloack, handler);
            temp.setHealth(random.nextInt(300));
            handler.add(temp);
            count3 = 0;
        }
    }

    public void addRow(int level, int bullets) {
        if (level % 10 == 0) {
            for (int i = 0; i < 10; i++) {
                FollowBlock temp = new FollowBlock(random.nextInt(800), 0, ID.FollowBloack, handler);
                temp.setHealth(random.nextInt(200));
                handler.add(temp);
            }
        } else if (level <= 10 && level % 2 == 0) {
            for (int i = 0; i < 8; i++) {
                block temp = new block(random.nextInt(800), 0, ID.block, handler);
                temp.setHealth(random.nextInt(250));
                handler.add(temp);
            }
        } else if (level <= 10 && level % 3 == 0) {
            for (int i = 0; i < 9; i++) {
                block temp = new block(random.nextInt(800), 0, ID.block, handler);
                handler.add(new FollowBlock(random.nextInt(850), 0, ID.FollowBloack, handler));
                temp.setHealth(random.nextInt(400));
                handler.add(temp);
            }
        } else if (level <= 10) {
            for (int i = 0; i < 5; i++) {
                block temp = new block(random.nextInt(800), 0, ID.block, handler);
                temp.setHealth(random.nextInt(500));
                handler.add(temp);
            }
        } else if (level % 2 == 0) {
            for (int i = 0; i < 10; i++) {
                block temp = new block(800, 0, ID.block, handler);
                temp.setHealth(random.nextInt(350));
                handler.add(temp);
            }
        } else if (level % 3 == 0) {
            for (int i = 0; i < 8; i++) {
                block temp = new block(random.nextInt(800), 0, ID.block, handler);
                temp.setHealth(random.nextInt(700));
                handler.add(new FollowBlock(random.nextInt(850), 0, ID.FollowBloack, handler));
                handler.add(new FollowBlock(random.nextInt(850), 0, ID.FollowBloack, handler));
                handler.add(temp);
            }
        }
    }
}
