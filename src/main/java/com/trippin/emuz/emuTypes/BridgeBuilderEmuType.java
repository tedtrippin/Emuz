package com.trippin.emuz.emuTypes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.trippin.emuz.engine.Engine;
import com.trippin.emuz.model.Emu;
import com.trippin.emuz.model.EmuTypeEnum;

public class BridgeBuilderEmuType implements EmuType {

    private static final int BUILD_SPEED = 40; // Clicks per brick laid
    private static final int NUMBER_OF_BRICKS = 10;
    private static final int BRICK_WIDTH = 10;
    private static final int MOVE_FORWARD = BRICK_WIDTH - 1; // How far to move forward after laying

    private final Emu emu;
    private int clickCount = 0;
    private int bricksLaid = 0;

    BridgeBuilderEmuType(Emu emu) {
        this.emu = emu;
    }

    @Override
    public void click(Engine engine) {

        clickCount++;
        if (clickCount < BUILD_SPEED)
            return;

        clickCount = 0;

        BufferedImage map = engine.getArenaMask().getMap();
        if (emu.getVelocity() > 0) {
            map.getGraphics().drawLine(emu.getPosX(), emu.getPosY(), emu.getPosX() + BRICK_WIDTH, emu.getPosY());
            emu.setPosX(emu.getPosX() + MOVE_FORWARD);
        } else {
            map.getGraphics().drawLine(emu.getPosX() - BRICK_WIDTH, emu.getPosY(), emu.getPosX(), emu.getPosY());
            emu.setPosX(emu.getPosX() - MOVE_FORWARD);
        }

        emu.setPosY(emu.getPosY() - 1); // Move up height of brick -> 1

        if (engine.isSolid(emu, BRICK_WIDTH))
            emu.turnAround();

        bricksLaid++;
        if (bricksLaid == NUMBER_OF_BRICKS)
            emu.setType(EmuTypeEnum.NORMAL);
    }

    @Override
    public void paint(Graphics g, Engine e) {

        g.fillRect(
            emu.getPosX() - emu.getHalfWidth(),
            emu.getPosY() - emu.getHeight(),
            emu.getWidth(),
            emu.getHeight());
    }
}
