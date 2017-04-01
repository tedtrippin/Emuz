package com.trippin.emuz.model;

import com.trippin.emuz.engine.Engine;

public class EmuEntrance extends AbstractArenaThing {

    private int dispenseSpeed;// Number of engine steps between shitting one out
    private int stepCounter = 0;
    private int dispenseLimit = 0;

    public EmuEntrance(int x, int y, int dispenseSpeed, int dispenseLimit) {
        super(x, y, 0, 50, 20);

        this.dispenseSpeed = dispenseSpeed;
        this.dispenseLimit = dispenseLimit;
    }

    @Override
    public void click(Engine engine) {

        if (dispenseLimit == 0)
            return;

        stepCounter++;
        if (stepCounter < dispenseSpeed)
            return;

        stepCounter = 0;
        dispenseEmu(engine);
    }

    private void dispenseEmu(Engine engine) {

        Emu emu = new Emu(getWidth(), getHeight(), 1);
        engine.addEmu(emu);
        dispenseLimit--;
    }
}
