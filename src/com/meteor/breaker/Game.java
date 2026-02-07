package com.meteor.breaker;

/*
 *
 *
 ****************************************
 *  (-:  1600 LINES OF CODE  :-)        *
 *  TO Build a simple Game In JAVA ;-)*
 ****************************************
 *
 *
 *
 */

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.logging.Logger;
import org.lwjgl.Sys;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 800,
        HEIGHT = 600;

    public enum STATE {
        START,
        PLAY,
        OVER,
    }

    private Menu menu;
    public static STATE state;
    private Thread thread;
    private Spawner spawner;
    private HOD hod;
    private Boolean isRunning = false;
    private Handler handler;

    public static GameObject rootGameObject;
    private boolean recalculateDimensions = true;

    public Game() {
        AudioPlayer.load();
        AudioPlayer.getMusic("background").loop((float) 1, (float) 10);
        state = STATE.START;
        this.requestFocus();
        handler = new Handler();

        handler.add(new Clouds(200, 200, ID.Cloud, handler, Color.lightGray));
        handler.add(new background(WIDTH / 2, 0, ID.background, handler));
        handler.add(new Ground(0, 0, ID.ground, handler));
        hod = new HOD(handler);
        spawner = new Spawner(handler, hod);
        menu = new Menu(handler, hod);
        this.addKeyListener(new keyListener(handler));
        this.requestFocusInWindow();
        this.addMouseListener(new mouseListener(handler, menu));
    }

    public static void main(String args[]) {
        System.out.println("main : " + System.getProperty("java.library.path"));
        Game game = new Game();
        Window gameWindow = new Window(WIDTH, HEIGHT, "MeteorBreaker", game);
        game.start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() throws InterruptedException {
        try {
            thread.join();
            isRunning = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
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
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        int currentWidth = getParent().getWidth();
        int currentHeight = getParent().getHeight();

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.cyan);
        g.fillRect(0, 0, currentWidth, currentHeight);
        handler.render(g);
        menu.render(g);
        if (state == STATE.PLAY) {
            hod.render(g);
        }
        g.dispose();
        bs.show();
    }

    private void tick() {
        if (recalculateDimensions) {
            System.out.println("[Game] Recalculating dimensions");
            handler.calculateDimensions();
            recalculateDimensions = false;
        }
        handler.tick();
        if (state != STATE.PLAY) menu.tick(state);
        if (state == STATE.PLAY) {
            hod.tick();
            spawner.tick();
        }
    }

    @Override
    public void resize(Dimension d) {
        super.resize(d);
        System.out.println(
            "[Game] Resizing game to: " + d.width + "x" + d.height
        );
        rootGameObject.setWidth(d.width);
        recalculateDimensions = true;
    }
}
