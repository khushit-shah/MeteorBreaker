package com.meteor.breaker;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Created by Khushit on 5/17/2018.
 */
public class Handler {

    private static Logger logger = Logger.getLogger(Handler.class.getName());

    public static LinkedList<GameObject> gameobj = new LinkedList<GameObject>();
    private static LinkedList<GameObject> addQueue = new LinkedList<
        GameObject
    >();
    private static LinkedList<GameObject> removeQueue = new LinkedList<
        GameObject
    >();

    public static synchronized void add(GameObject e) {
        logger.info("Object " + e + " added to addQueue.");
        addQueue.add(e);
    }

    public static synchronized void remove(GameObject e) {
        logger.info("Object " + e + " removed addQueue.");
        removeQueue.add(e);
    }

    public static synchronized void render(Graphics g) {
        for (GameObject tempobj : gameobj) {
            tempobj.render(g);
        }
    }

    public static synchronized void calculateDimensions() {
        /* Should be in order of parent to child. */
        for (GameObject tempobj : gameobj) {
            tempobj.calculateDimensions();
        }
    }

    public static List<GameObject> getCollidingObjects(GameObject e, List<ID> filter) {
        ArrayList<GameObject> collidingObjects = new ArrayList<>();

        for (final GameObject temp : gameobj) {
            if (temp != e && filter.contains(temp.id) && temp.getBound().intersects(e.getBound())) {
                collidingObjects.add(temp);
            }
        }

        return collidingObjects;
    }

    public static synchronized void tick() {
        completeAdd();
        completeRemove();

        for (GameObject tempobj : gameobj) {
            tempobj.tick();
        }

        completeAdd();
        completeRemove();
    }

    private static synchronized void completeAdd() {
        while (!addQueue.isEmpty()) {
            gameobj.add(addQueue.poll());
        }
    }

    private static synchronized void completeRemove() {
        while (!removeQueue.isEmpty()) {
            gameobj.remove(removeQueue.poll());
        }
    }
}
