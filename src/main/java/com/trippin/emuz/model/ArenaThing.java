package com.trippin.emuz.model;

import java.awt.Graphics;

import com.trippin.emuz.engine.Engine;

public interface ArenaThing {

    /**
     * Gets the x offset of the middle of this thing
     * relative to the left of the screen
     *
     * @return
     */
    public int getPosX();

    /**
     * Sets the x offset of this thing.
     *
     * @param x
     */
    public void setPosX(int x);

    /**
     * Gets the y offset of the bottom of this thing
     * relative to the top of the screen. Eg. a value
     * of zero would mean the bottom pixel of this thing
     * is on the top row of pixels on the arena.
     *
     * @return
     */
    public int getPosY();

    /**
     * Sets the y offset of this thing.
     *
     * @param y
     */
    public void setPosY(int y);

    /**
     * Gets the velocity (horizontal) of the thing. Positive
     * for right, negative for left.
     *
     * @return
     */
    public int getVelocity();

    /**
     * Sets the velocity of this thing.
     *
     * @param speed
     */
    public void setVelocity(int velocity);

    /**
     * Inverts Velocity.
     */
    public void turnAround();

    /**
     * Gets the speed of the thing. Same value
     * as velocity but always positive.
     *
     * @return
     */
    public int getSpeed();

    /**
     * Returns true if this thing is falling.
     *
     * @return
     */
    public boolean isFalling();

    /**
     * Sets whether this thing is falling.
     *
     * @param falling
     */
    public void setFalling(boolean falling);

    /**
     * Gets the distance fallen so far. Only meaningful if
     * {@link isFalling} returns true.
     *
     * @return
     */
    public int getDistanceFallen();

    /**
     * Gets the width of this thing, for collision detection.
     *
     * @return
     */
    public int getWidth();

    /**
     * For convenience, gets the width/2.
     *
     * @return
     */
    public int getHalfWidth();

    /**
     * Gets the height of this thing, for collision detection.
     *
     * @return
     */
    public int getHeight();

    /**
     * Called by engine each click.
     */
    public void click(Engine engine);

    /**
     * Paint the thing.
     * @param g
     * @param engine
     */
    public void paint(Graphics g, Engine engine);
}
