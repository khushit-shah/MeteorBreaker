package com.meteor.breaker;

import com.meteor.breaker.objects.GameObject;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Handler {
    private static final Logger LOGGER = Log.getLogger(Handler.class);

    private final LinkedList<GameObject> gameObjects = new LinkedList<>();
    private final LinkedList<GameObject> addQueue = new LinkedList<>();
    private final LinkedList<GameObject> removeQueue = new LinkedList<>();

    public synchronized void add(GameObject object) {
        addQueue.add(object);
    }

    public synchronized void remove(GameObject object) {
        removeQueue.add(object);
    }

    public synchronized void render(Graphics graphics) {
        for (GameObject object : gameObjects) {
            object.render(graphics);
        }
    }

    public synchronized void calculateDimensions() {
        for (GameObject object : gameObjects) {
            object.calculateDimensions();
        }
    }

    public synchronized List<GameObject> getCollidingObjects(GameObject source, List<ID> filter) {
        ArrayList<GameObject> collisions = new ArrayList<>();
        for (GameObject object : gameObjects) {
            if (object != source && filter.contains(object.id) && object.getBound().intersects(source.getBound())) {
                collisions.add(object);
            }
        }
        return collisions;
    }

    public synchronized void tick() {
        completeAdd();
        completeRemove();

        for (GameObject object : gameObjects) {
            object.tick();
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
        LOGGER.info("Clearing all game objects and queues");
        gameObjects.clear();
        addQueue.clear();
        removeQueue.clear();
    }

    private void completeAdd() {
        while (!addQueue.isEmpty()) {
            GameObject added = addQueue.poll();
            added.calculateDimensions();
            gameObjects.add(added);
        }
    }

    private void completeRemove() {
        while (!removeQueue.isEmpty()) {
            gameObjects.remove(removeQueue.poll());
        }
    }
}
