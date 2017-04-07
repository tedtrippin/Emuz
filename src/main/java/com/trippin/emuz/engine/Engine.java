package com.trippin.emuz.engine;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import com.trippin.emuz.model.ArenaThing;
import com.trippin.emuz.model.Emu;
import com.trippin.emuz.model.EmuEntrance;

public class Engine {

    private final int GRAVITY = 5;
    private final int EMPTY_PIXEL = -16777216;
    private final int GRAVITY_PLUS_1 = GRAVITY + 1; // For convenience
    private final int MAX_STEP_UP = 3; // Max size of step can walk up
    private final int MAX_STEP_UP_MINUS_1 = MAX_STEP_UP - 1; // For convenience
    private final int COLLISION_HEIGHT_CHECK = MAX_STEP_UP_MINUS_1 + 1 + GRAVITY; // 'Thickness' of area used for forward collision detection
    private final int COLLISION_HEIGHT_CHECK_MINUS_1 = COLLISION_HEIGHT_CHECK - 1; // For convenience

    private boolean running = true;;
    private long speed = 100;
    private final List<ArenaThing> things;
    private final List<EmuEntrance> emuEntrances;
    private List<ArenaThing> newThings;
    private ArenaMask mask;
    private int arenaWidth;
    private int arenaHeight;
    private int arenaHeightMinus1;

    public Engine(ArenaMask mask, long speed, List<EmuEntrance> emuEntrances) {
        this.mask = mask;
        this.speed = speed;
        this.emuEntrances = emuEntrances;
        things = new ArrayList<>(emuEntrances);
        newThings = new ArrayList<>();
        arenaWidth = mask.getArenaWidth();
        arenaHeight = mask.getArenaHeight();
        arenaHeightMinus1 = arenaHeight - 1;
    }

    public void click() {

        things.forEach(t -> t.click(this));
        things.addAll(newThings);
        newThings.clear();
    }

    public Dimension getSize() {
        return new Dimension(arenaWidth, arenaHeight);
    }

    public int getArenaWidth() {
        return arenaWidth;
    }

    public int getArenaHeight() {
        return arenaHeight;
    }

    public List<ArenaThing> getThings() {
        return things;
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        running = false;
    }

    public long getSpeed() {
        return speed;
    }

    public ArenaMask getArenaMask() {
        return mask;
    }

    /**
     * Moves a thing. Returns true if moved out the arena.
     *
     * @param emu
     * @return true if emu moved outside the arena
     */
    public boolean move(ArenaThing thing) {

        if (thing.getSpeed() == 0)
            return false;

        // Get the bounds
        // Get available width up to a max of 'speed'
        int x = thing.getPosX() + thing.getVelocity();
        int width;
        if (x < 0) {
            width = thing.getPosX();
            x = 0;
        } else if (x > arenaWidth) {
            width = arenaWidth - thing.getPosX();
            x = arenaWidth;
        } else {
            width = thing.getSpeed();
        }

        if (thing.getVelocity() > 0)
            x -= (width - 1);

        // Get available height up to a max of COLLISION_HEIGHT_CHECK
        int y = thing.getPosY() - MAX_STEP_UP_MINUS_1;
        int upHeight;
        if (y < 0) {
            upHeight = thing.getPosY() + 1;
            y = 0;
        } else {
            upHeight = MAX_STEP_UP;
        }

        int downHeight;
        if (thing.getPosY() + GRAVITY >= arenaHeight)
            downHeight = arenaHeight - thing.getPosY() - 1;
        else
            downHeight = GRAVITY;

        // Get the pixels
        int[] pixels = mask.getPixels(x, y, width, upHeight + downHeight);

        // First check for falling
        int idx = thing.getVelocity() > 0 ? width -1 : 0;
        idx += upHeight * width;

        boolean falling = true;
        for (int i = downHeight; i > 0; i--) {
            if (pixels[idx] == EMPTY_PIXEL) {
                idx += width;
                continue;
            }

            // Hit ground
            falling = false;
            break;
        }

        if (falling) {
            thing.setPosY(thing.getPosY() + GRAVITY);
            thing.setFalling(true);
            return thing.getPosY() >= arenaHeightMinus1;
        } else {
            thing.setFalling(false);
        }

        // Now check to see if the next step is up/down/level
        idx = 0;
        int heightAdjust = -1;
        for (int i = 0; i < upHeight + downHeight; i++) {
            boolean rowClear = true;
            for (int j = width ; j > 0; j--) {
                if (pixels[idx] != EMPTY_PIXEL)
                    rowClear = false;

                idx++;
            }

            if (rowClear)
                heightAdjust = i;
            else if (heightAdjust > -1)
                break;
        }

        if (heightAdjust < 0) {
            // If height adjust not set then we can't go forward
            thing.turnAround();
        } else {
            thing.setPosX(thing.getPosX() + thing.getVelocity());
            thing.setPosY(thing.getPosY() - upHeight + heightAdjust + 1);
        }

        return thing.getPosX() < 0 || thing.getPosX() >= arenaWidth;
    }

    /**
     * checks if the pixels in front of emu are solid.
     *
     * @param emu
     * @param velocity
     * @return
     */
    public boolean isSolid(ArenaThing thing, int velocity) {

        int x = thing.getPosX() + velocity;
        int width;
        if (x < 0) {
            width = thing.getPosX();
            x = 0;
        } else if (x > arenaWidth) {
            width = arenaWidth - thing.getPosX();
            x = arenaWidth;
        } else {
            width = velocity;
        }

        if (velocity > 0)
            x -= width;

        int[] pixels = mask.getPixels(x, thing.getPosY(), width, 1);
        for (int i : pixels) {
            if (i != -1)
                return false;
        }

        return true;
    }

    public void addEmu(Emu emu) {
        newThings.add(emu);
    }

    public Emu getEmu(int x, int y) {

        ArrayList<ArenaThing> pointlessCopy = new ArrayList<>(things);
        for (ArenaThing thing : pointlessCopy) {
            if (isEmuUnderCursor(thing, x, y))
                return (Emu)thing;
        }

        return null;
    }

    private boolean isEmuUnderCursor(ArenaThing thing, int x, int y) {

        if (!(thing instanceof Emu))
            return false;

        if (x < thing.getPosX() - thing.getHalfWidth())
            return false;

        if (x > thing.getPosX() + thing.getHalfWidth())
            return false;

        if (y < thing.getPosY() - thing.getHeight())
            return false;

        if (y > thing.getPosY())
            return false;

        return true;
    }
}
