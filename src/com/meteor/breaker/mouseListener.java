package com.meteor.breaker;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Khushit on 5/20/2018.
 */
public class mouseListener implements MouseListener {
    private  Menu menu;
    private Handler handler;
    public mouseListener(Handler handler,Menu menu){
        this.handler = handler;
        this.menu = menu;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        menu.checkMouse(x,y);

    }

    @Override
    public void mousePressed(MouseEvent e) {
            //null;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
            //null
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //null
    }

    @Override
    public void mouseExited(MouseEvent e) {
            //null
    }
}
