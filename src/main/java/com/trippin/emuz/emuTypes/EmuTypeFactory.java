package com.trippin.emuz.emuTypes;

import com.trippin.emuz.model.Emu;
import com.trippin.emuz.model.EmuTypeEnum;

public class EmuTypeFactory {

    public static EmuType getEmuType(EmuTypeEnum type, Emu emu) {

        switch (type) {
            case NORMAL:
                return new NormalEmuType(emu);
            case BRIDGE_BUILDER:
                return new BridgeBuilderEmuType(emu);
            case STOPPER:
                return new StopperEmuType(emu);
            default:
                throw new RuntimeException("No such type");
        }
    }
}
