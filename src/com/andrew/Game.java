package com.andrew;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 3081420168871724542L;
    public static final int WIDTH = 1500, HEIGHT = 900;
    private Thread thread;
    private boolean running = false;
    private World world;
    private Thing teapot;

    public Game() {
        world = new World();
        try {
            teapot = new Thing("teapot.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        teapot.orientation.x = Math.PI;
        teapot.position.y = 1;
        teapot.position.x = 4;
        world.addThing(teapot);
        new Window(WIDTH, HEIGHT, "test game", this);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        teapot.orientation.y += .01;
        teapot.orientation.x += .005;
    }

    public void keyEvent(KeyEvent e){
        switch(e.getKeyCode()){
            case 68:
                world.camera.move(1,0,0);
                break;
            case 65:
                world.camera.move(-1,0,0);
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        world.render(g);
        bs.show();
    }

    public static void main(String[] args) throws Exception {
        new Game();
    }

}