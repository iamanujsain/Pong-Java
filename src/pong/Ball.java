package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {

    // Abscissa of the Oval
    private int x;

    // Ordinate of the Oval
    private int y;

    // Radius of the ball.
    private int radius;

    // Velocity vectors.
    private int dx;
    private int dy;

    // Abscissa of the center of the ball.
    private int relx;

    // Ordinate of the center of the ball.
    private int rely;

    private boolean gameOver;

    // Constructor.
    public Ball(int x, int y, int radius) {
        this.x = x - radius;
        this.y = y - radius;
        this.radius = radius;
        this.dx = 2;
        this.dy = -5;
        gameOver = false;
    }

    public int getX() {
        return this.relx;
    }

    public int getY() {
        return this.rely;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDx() {
        return this.dx;
    }

    public int getDy() {
        return this.dy;
    }

    public void setDx(int dx) {
        this.dx *= dx;
    }

    public void setDy(int dy) {
        this.dy *= dy;
    }

    public int getRadius() {
        return this.radius;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean b) {
        this.gameOver = b;
    }

    void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(this.x, this.y, 2 * this.radius, 2 * this.radius);
    }

    void update() {
        this.x += this.dx;
        this.y += this.dy;

        this.relx = this.x + this.radius;
        this.rely = this.y + this.radius;

        if (this.dy < 0) {
            if (this.y < Paddle.HEIGHT) {
                this.dy *= -1;
            }
        }

        if (this.relx + this.radius > Game.WIDTH) {
            this.dx *= -1;
        } else if (this.relx - this.radius < 0) {
            this.dx *= -1;
        }

        if (this.rely + this.radius > Game.HEIGHT) {
            if (Paddle.hit) {
                Score.score++;
                this.dy *= -1;
            } else {
                if (Game.gameState == Game.GameState.GAME_ON) {
                    Game.gameState = Game.GameState.RESTART;
                    reset();
                }
            }
        } else if (this.rely - this.radius < 0) {
            this.dy *= -1;
        }
    }

    void reset() {
        this.relx = Game.WIDTH/2;
        this.rely = Game.HEIGHT/2;
        this.setDy(-1);
        Paddle.hit = false;
    }
}