package com.meteor.breaker;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Khushit on 5/17/2018.
 */
public class Handler {

    public static LinkedList<GameObject> gameobj = new LinkedList<GameObject>();
    private static LinkedList<GameObject> addQueue = new LinkedList<
        GameObject
    >();
    private static LinkedList<GameObject> removeQueue = new LinkedList<
        GameObject
    >();

    public synchronized void add(GameObject e) {
        addQueue.add(e);
    }

    public synchronized void remove(GameObject e) {
        removeQueue.add(e);
    }

    public synchronized void render(Graphics g) {
        for (GameObject tempobj : gameobj) {
            tempobj.render(g);
        }
    }

    public synchronized void calculateDimensions() {
        /* Should be in order of parent to child. */
        for (GameObject tempobj : gameobj) {
            tempobj.calculateDimensions();
        }
    }

    public synchronized void tick() {
        for (GameObject tempobj : gameobj) {
            tempobj.tick();
        }

        completeAdd();
        completeRemove();
    }

    private synchronized void completeAdd() {
        while (!addQueue.isEmpty()) {
            gameobj.add(addQueue.poll());
        }
    }

    private synchronized void completeRemove() {
        while (!removeQueue.isEmpty()) {
            gameobj.remove(removeQueue.poll());
        }
    }
}
