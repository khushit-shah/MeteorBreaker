package com.meteor.breaker;

import com.meteor.breaker.objects.player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyListener implements KeyListener {
    private final Handler handler;

    public keyListener(Handler handler) {
        this.handler = handler;
    }

    private player findPlayer() {
        return (player) handler.findFirstById(ID.player).orElse(null);
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
        player player = findPlayer();
        if (player == null) {
            return;
        }

        int keyCode = e.getKeyCode();
        char keyChar = e.getKeyChar();

        if (released) {
            if (keyChar == 'w' || keyChar == 's') player.setVelY(0);
            if (keyChar == 'a' || keyChar == 'd') player.setVelX(0);
            if (keyChar == ' ') player.stopShooting();
            if (keyCode == KeyEvent.VK_ENTER || keyChar == '\n') player.returnJump();
            return;
        }

        if (keyChar == 'w') player.setVelY(player.getVelY() - 5);
        if (keyChar == 's') player.setVelY(player.getVelY() + 5);
        if (keyChar == 'a') player.setVelX(player.getVelX() - 5);
        if (keyChar == 'd') player.setVelX(player.getVelX() + 5);
        if (keyChar == 'm') player.shootMissile();
        if (keyChar == ' ') player.startShooting();

        if (keyChar == 'b' && player.getPoints() >= 700) {
            player.setBullets(player.getBullets() + 1000);
            player.setPoints(player.getPoints() - 700);
        }

        if (keyCode == KeyEvent.VK_ENTER || keyChar == '\n') player.jump();
    }
}
