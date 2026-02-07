package com.meteor.breaker;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Khushit on 5/17/2018.
 */

public class keyListener implements KeyListener {
    private GameObject player;
    private final Handler handler;

    public keyListener(Handler handler) {
        this.handler = handler;
    }

    private GameObject findPlayer() {
        player = handler.findFirstById(ID.player).orElse(null);
        return player;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        handleKey(e, false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        handleKey(e, false);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        handleKey(e, true);
    }

    private void handleKey(KeyEvent e, boolean released) {
        GameObject currentPlayer = findPlayer();
        if (currentPlayer == null) {
            return;
        }

        int keyCode = e.getKeyCode();
        char keyChar = e.getKeyChar();

        if (released) {
            if (keyChar == 'w' || keyChar == 's') currentPlayer.setvalY(0);
            if (keyChar == 'a' || keyChar == 'd') currentPlayer.setvalX(0);
            if (keyChar == ' ') currentPlayer.stopshooting();
            if (keyCode == KeyEvent.VK_ENTER || keyChar == '\n') currentPlayer.returnJump();
            return;
        }

        if (keyChar == 'w') currentPlayer.setvalY((int) (currentPlayer.valy() - 5));
        if (keyChar == 's') currentPlayer.setvalY((int) (currentPlayer.valy() + 5));
        if (keyChar == 'a') currentPlayer.setvalX((int) (currentPlayer.valx() - 5));
        if (keyChar == 'd') currentPlayer.setvalX((int) (currentPlayer.valx() + 5));
        if (keyChar == 'm') currentPlayer.shootMissile();
        if (keyChar == ' ') currentPlayer.strshoot(handler);

        if (keyChar == 'b') {
            if (currentPlayer.getPoints() >= 700) {
                currentPlayer.setBullets(currentPlayer.getBullets() + 1000);
                currentPlayer.setPoints(currentPlayer.getPoints() - 700);
            }
        }
        if (keyCode == KeyEvent.VK_ENTER || keyChar == '\n') currentPlayer.jump();
    }
}
