package com.meteor.breaker;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Khushit on 5/17/2018.
 */
public class Handler {
    private final LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();
    private final LinkedList<GameObject> addQueue = new LinkedList<GameObject>();
    private final LinkedList<GameObject> removeQueue = new LinkedList<GameObject>();

    public synchronized void add(GameObject e) {
        addQueue.add(e);
    }

    public synchronized void remove(GameObject e) {
        removeQueue.add(e);
    }

    public synchronized void render(Graphics g) {
        for (GameObject tempobj : gameObjects) {
            tempobj.render(g);
        }
    }

    public synchronized void calculateDimensions() {
        /* Should be in order of parent to child. */
        for (GameObject tempobj : gameObjects) {
            tempobj.calculateDimensions();
        }
    }

    public synchronized List<GameObject> getCollidingObjects(GameObject e, List<ID> filter) {
        ArrayList<GameObject> collidingObjects = new ArrayList<>();

        for (final GameObject temp : gameObjects) {
            if (temp != e && filter.contains(temp.id) && temp.getBound().intersects(e.getBound())) {
                collidingObjects.add(temp);
            }
        }

        return collidingObjects;
    }

    public synchronized void tick() {
        completeAdd();
        completeRemove();

        for (GameObject tempobj : gameObjects) {
            tempobj.tick();
        }

        completeAdd();
        completeRemove();
    }

    public synchronized Optional<GameObject> findFirstById(ID id) {
        for (GameObject object : gameObjects) {
            if (object.id == id) {
                return Optional.of(object);
            }
        }

        return Optional.empty();
    }

    public synchronized List<GameObject> getObjectsSnapshot() {
        return Collections.unmodifiableList(new ArrayList<>(gameObjects));
    }

    public synchronized void clear() {
        gameObjects.clear();
        addQueue.clear();
        removeQueue.clear();
    }

    private synchronized void completeAdd() {
        while (!addQueue.isEmpty()) {
            gameObjects.add(addQueue.poll());
        }
    }

    private synchronized void completeRemove() {
        while (!removeQueue.isEmpty()) {
            gameObjects.remove(removeQueue.poll());
        }
    }
}
