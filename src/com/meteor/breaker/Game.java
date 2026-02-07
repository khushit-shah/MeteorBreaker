package com.meteor.breaker;

import com.meteor.breaker.objects.Clouds;
import com.meteor.breaker.objects.GameObject;
import com.meteor.breaker.objects.Ground;
import com.meteor.breaker.objects.RootGameObject;
import com.meteor.breaker.objects.background;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Logger;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final Logger LOGGER = Log.getLogger(Game.class);

    public enum STATE {
        START,
        PLAY,
        OVER,
    }

    public static STATE state;
    public static RootGameObject rootGameObject;

    private final Handler handler;
    private final Menu menu;
    private final Spawner spawner;
    private final HOD hod;

    private Thread thread;
    private boolean isRunning = false;
    private boolean recalculateDimensions = true;

    public Game() {
        LOGGER.info("Initializing game");
        AudioPlayer.load();
        AudioPlayer.getMusic("background").loop(1f, 10f);
        state = STATE.START;

        handler = new Handler();
        rootGameObject = new RootGameObject(0, 0, ID.root, handler);
        rootGameObject.setWidth(WIDTH);
        rootGameObject.setHeight(HEIGHT);

        addInitialObjects();

        hod = new HOD(handler);
        spawner = new Spawner(handler, hod);
        menu = new Menu(handler, hod);

        addKeyListener(new keyListener(handler));
        addMouseListener(new mouseListener(handler, menu));
        requestFocusInWindow();
    }

    private void addInitialObjects() {
        handler.add(new Clouds(200, 200, ID.Cloud, handler, Color.lightGray));
        handler.add(new background(WIDTH / 2, 0, ID.background, handler));
        handler.add(new Ground(0, 0, ID.ground, handler));
    }

    public static void main(String[] args) {
        LOGGER.info("Starting MeteorBreaker");
        Game game = new Game();
        new Window(WIDTH, HEIGHT, "MeteorBreaker", game);
        game.start();
    }

    public synchronized void start() {
        if (isRunning) {
            return;
        }
        thread = new Thread(this, "game-loop");
        isRunning = true;
        LOGGER.info("Game loop started");
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.warning("Interrupted while stopping game loop");
            }
        }
        LOGGER.info("Game loop stopped");
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        final double ns = 1_000_000_000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                LOGGER.fine("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }

        int currentWidth = getParent() != null ? getParent().getWidth() : WIDTH;
        int currentHeight = getParent() != null ? getParent().getHeight() : HEIGHT;

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.cyan);
        graphics.fillRect(0, 0, currentWidth, currentHeight);
        handler.render(graphics);
        menu.render(graphics);
        if (state == STATE.PLAY) {
            hod.render(graphics);
        }
        graphics.dispose();
        bufferStrategy.show();
    }

    private void tick() {
        if (recalculateDimensions) {
            LOGGER.info("Recalculating object dimensions");
            handler.calculateDimensions();
            recalculateDimensions = false;
        }

        handler.tick();

        if (state != STATE.PLAY) {
            menu.tick(state);
        } else {
            hod.tick();
            spawner.tick();
        }
    }

    @Override
    public void resize(Dimension dimension) {
        super.resize(dimension);
        LOGGER.info("Resizing game to " + dimension.width + "x" + dimension.height);
        rootGameObject.setWidth(dimension.width);
        rootGameObject.setHeight(dimension.height);
        recalculateDimensions = true;
    }

    public static boolean checkCollide(GameObject first, GameObject second) {
        return first.getBound().intersects(second.getBound());
    }
}
