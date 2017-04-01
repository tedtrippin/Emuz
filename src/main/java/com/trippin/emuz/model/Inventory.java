package com.trippin.emuz.model;

public class Inventory {

    private final int[] stock = new int[EmuTypeEnum.values().length];

    private EmuTypeEnum currentEmuType = EmuTypeEnum.BRIDGE_BUILDER;

    public static Inventory createInfiniteInventory() {

        Inventory inventory = new Inventory();
        for (int i = 0; i < inventory.stock.length; i++) {
            inventory.stock[i] = -1;
        }
        return inventory;
    }

    public EmuTypeEnum getCurrentEmuType() {
        return currentEmuType;
    }

    public void setCurrentEmutType(EmuTypeEnum currentEmuType) {
        this.currentEmuType = currentEmuType;
    }

    /**
     * Returns true if the current type is in stock.
     *
     * @return
     */
    public boolean inStock() {
        return stock[(int)currentEmuType.ordinal()] != 0;
    }

    /**
     * Reduces the stock of the current type and returns
     * the type.
     */
    public EmuTypeEnum pop() {

        if (inStock())
            stock[currentEmuType.ordinal()]--;

        return currentEmuType;
    }
}
