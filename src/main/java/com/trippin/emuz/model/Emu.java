package com.trippin.emuz.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.trippin.emuz.emuTypes.EmuType;
import com.trippin.emuz.emuTypes.EmuTypeFactory;
import com.trippin.emuz.engine.Engine;

public class Emu extends AbstractArenaThing {

    private EmuType type;

    // Setup the rectangle for writing to masks
    private static final int[] EMU_RECTANGLE = new int[150];
    static {
        for(int i = EMU_RECTANGLE.length-1; i >= 0; i--) {
            EMU_RECTANGLE[i] = -1;
        }
    }

    public Emu(int x, int y, int velocity) {
        super(x, y, velocity, 10, 15);
        type = EmuTypeFactory.getEmuType(EmuTypeEnum.NORMAL, this);
    }

    public void setType(EmuTypeEnum newType) {
        type = EmuTypeFactory.getEmuType(newType, this);
    }

    @Override
    public void click(Engine engine) {
        type.click(engine);
    }

    @Override
    public void paint(Graphics g, Engine engine) {

        type.paint(g, engine);

        // Write to the emu mask as well
        BufferedImage emuMask = engine.getArenaMask().getEmuMask();
        emuMask.setRGB(getPosX() - getHalfWidth(),
            getPosY() - getHeight(),
            getWidth(),
            getHeight(),
            EMU_RECTANGLE,
            0,
            getWidth());
    }
}
