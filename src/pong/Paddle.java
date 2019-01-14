package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Paddle {
    final static int WIDTH = 70;
    final static int HEIGHT = 20;

    public static boolean hit = false;

    private int dx;
    private int x;
    private int y;
    private int accl;

    private int type; // 1 for AI-paddle and 2 for human-paddle

    public Paddle(int type) {
        this.type = type;
        switch(type) {
            case 1:
                this.dx = 1;
                x = Game.WIDTH/2 - WIDTH/2;
                y = 0;
                break;
            case 2:
                this.dx = 0;
                x = Game.WIDTH/2 - WIDTH/2;
                y = Game.HEIGHT - HEIGHT;
                break;
        }
    }

    public int getAccl() {
        return this.accl;
    }

    public void setAccl(int input) {
        this.accl = input;
    }

    public int getDx() {
        return this.dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }


    void draw(Graphics g) {
        switch (type) {
            case 1:
                g.setColor(Color.black);
                break;
            case 2:
                g.setColor(Color.green);
                break;
        }
        g.fillRect(x,y, WIDTH, HEIGHT);
    }

    void update(Ball b) {
        switch (type) {
            case 1:
                this.x = b.getX() - WIDTH/2;
                break;
            case 2:
                this.x += this.dx;
                this.dx += this.accl;

                if (this.dx > 13 || this.dx < -13) {
                    this.dx = 0;
                    this.accl = 0;
                }

                if (b.getDy() > 0) {
                    if (b.getX() + b.getRadius() > this.x &&
                            b.getX() - b.getRadius() < this.x + this.WIDTH) {
                        if (b.getY() + b.getRadius() >= this.y) {
                            hit = true;
                        } else {
                            hit = false;
                        }
                    } else {
                        hit = false;
                    }
                }

                break;
        }

        if (this.x < 0) {
            this.x = 0;
        } else if (this.x + WIDTH > Game.WIDTH) {
            this.x = Game.WIDTH - WIDTH;
        }
    }
}
