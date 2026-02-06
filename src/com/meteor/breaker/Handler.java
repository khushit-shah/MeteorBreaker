package com.meteor.breaker;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Khushit on 5/17/2018.
 */
public class Handler {
    public static LinkedList<GameObject> gameobj = new LinkedList<GameObject>();

    public void add(GameObject e) {
        //if(e.id != ID.background)
            gameobj.add(e);

    }

    public void remove(GameObject e) {
        gameobj.remove(e);
    //   System.out.println(e + " is removed");
    }

    public void render(Graphics g) {
        for (int i = gameobj.size()-1; i >=0 ; i--) {
            GameObject tempobj = gameobj.get(i);
            //if(tempobj.id != ID.background)
                tempobj.render(g);

        }
    }

    public void tick() {
        for (int i = 0; i < gameobj.size(); i++) {
            GameObject tempobj = gameobj.get(i);
            tempobj.tick();
        }
    }
}