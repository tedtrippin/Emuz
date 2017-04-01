package com.trippin.emuz.emuTypes;

import java.awt.Graphics;

import com.trippin.emuz.engine.Engine;

public interface EmuType {

    void click(Engine engine);

    void paint(Graphics g, Engine e);
}
