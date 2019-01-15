package pong;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Game implements KeyListener, Runnable {

    final static int WIDTH = 400, HEIGHT = 600;

    private Display display;
    private String title;

    private Thread thread;
    private boolean running;
    private BufferStrategy bs;
    private Canvas canvas;
    private Graphics g;
    private Font font;

    private Ball b;
    private Paddle ai;
    private Paddle hp;

    public enum GameState {
        BEGINNING,
        GAME_ON,
        RESTART;
    }

    public static GameState gameState;

     Game(String title, int width, int height) {
        this.title = title;
        running = true;

        gameState = GameState.BEGINNING;

        b = new Ball(WIDTH / 2, HEIGHT / 2, 13);
        ai = new Paddle(1);
        hp = new Paddle(2);

        b.setGameOver(true);
    }

    private void init() {
        display = new Display(title, WIDTH, HEIGHT, this);
        canvas = display.getCanvas();
    }

    private void tick(int fps) {
        try {
            Thread.sleep(1000 / fps);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void draw() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        switch (gameState) {
            case BEGINNING:
                canvas.setBackground(Color.BLACK);
                g.setColor(Color.WHITE);

                font = new Font("Consolas", Font.BOLD, 55);
                g.setFont(font);
                g.drawString("PONG", WIDTH / 3, HEIGHT / 3);

                font = new Font("Consolas", Font.BOLD, 20);
                g.setFont(font);
                g.drawString("Press the SPACE bar to begin...", 35, HEIGHT / 2);
                break;
            case GAME_ON:
                canvas.setBackground(Color.WHITE);

                font = new Font("Consolas", Font.BOLD, 30);
                g.setFont(font);
                g.setColor(Color.pink);
                g.drawString(Score.score + "", WIDTH/2, HEIGHT/2);

                b.draw(g);
                ai.draw(g);
                hp.draw(g);
                break;
            case RESTART:
                canvas.setBackground(Color.PINK);
                g.setColor(Color.BLACK);

                font = new Font("Consolas", Font.BOLD, 50);
                g.setFont(font);
                g.drawString("Game Over!", WIDTH / 5, HEIGHT / 4);

                font = new Font("Consolas", Font.BOLD, 30);
                g.setFont(font);
                g.drawString("Score: " + Score.score, WIDTH/3, HEIGHT/3);

                font = new Font("Consolas", Font.BOLD, 20);
                g.setFont(font);
                g.drawString("Press ENTER to play again...", 50, HEIGHT/2);
                break;
        }

        bs.show();
        g.dispose();
    }

    private void update() {
        if (gameState == GameState.GAME_ON) {
            b.update();
            ai.update(b);
            hp.update(b);
        }
    }

    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public synchronized void run() {
        init();
        while (running) {
            tick(60);
            update();
            draw();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                hp.setAccl(-1);
                break;
            case KeyEvent.VK_RIGHT:
                hp.setAccl(1);
                break;
            case KeyEvent.VK_SPACE:
                if (gameState == GameState.BEGINNING) {
                    gameState = GameState.GAME_ON;
                }
                break;
            case KeyEvent.VK_ENTER:
                if (gameState == GameState.RESTART) {
                    gameState = GameState.GAME_ON;
                    Score.reset();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
