package com.trippin.emuz.model;

import java.awt.Graphics;

import com.trippin.emuz.engine.Engine;

public abstract class AbstractArenaThing implements ArenaThing {

    private final int width;
    private final int halfWidth;
    private final int height;
    private int posX; // Bottom of thing
    private int posY; // Middle of thing
    private int velocity;
    private int speed;
    private int startingFallingY = -1;

    public AbstractArenaThing(int x, int y, int velocity, int width, int height) {
        posX = x;
        posY = y;
        this.width = width;
        halfWidth = width/2;
        this.height = height;
        setVelocity(velocity);
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(int velocity) {
        this.velocity = velocity;
        speed = Math.abs(velocity);
    }

    @Override
    public void turnAround() {
        velocity = velocity * -1;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHalfWidth() {
        return halfWidth;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isFalling() {
        return startingFallingY > -1;
    }

    @Override
    public void setFalling(boolean falling) {

        if (falling) {
            if (!isFalling())
                startingFallingY = posY;
        } else {
            startingFallingY = -1;
        }
    }

    @Override
    public int getDistanceFallen() {
        return posY - startingFallingY;
    }

    @Override
    public void paint(Graphics g, Engine engine) {

        g.fillRect(
            posX - halfWidth,
            posY - height,
            width,
            height);
    }
}
