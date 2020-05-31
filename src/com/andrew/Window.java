package com.andrew;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.event.KeyEvent;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

public class Window extends Canvas {
    private static final long serialVersionUID = -8255319694373975038L;

    public Window(int w, int h, String t, Game game) {
        JFrame frame = new JFrame(t);
        frame.add(new JTextField());
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new MyDispatcher(game));
        frame.setFocusable(true);
        frame.setPreferredSize(new Dimension(w, h));
        frame.setMinimumSize(new Dimension(w, h));
        frame.setMaximumSize(new Dimension(w, h));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }

    private class MyDispatcher implements KeyEventDispatcher {
        private Game game;
        MyDispatcher(Game g){
            this.game = g;
        }
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e != null) {
                this.game.keyEvent(e);
            }
            return false;
        }
    }
}