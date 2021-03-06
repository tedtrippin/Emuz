package com.trippin.emuz.emuTypes;

import java.awt.Graphics;

import com.trippin.emuz.engine.Engine;
import com.trippin.emuz.model.Emu;

public class StopperEmuType implements EmuType {

    private final Emu emu;

    StopperEmuType(Emu emu) {
        this.emu = emu;
    }

    @Override
    public void click(Engine engine) {
    }

    @Override
    public void paint(Graphics g, Engine e) {

        // Paint the stopper
        g.fillRect(
            emu.getPosX() - emu.getHalfWidth(),
            emu.getPosY() - emu.getHeight(),
            emu.getWidth(),
            emu.getHeight());

        // Paint to the map mask so can detect collision
        e.getArenaMask().getMap().getGraphics().fillRect(
            emu.getPosX() - emu.getHalfWidth(),
            emu.getPosY() - emu.getHeight(),
            emu.getWidth(),
            emu.getHeight());
    }
}
