package com.trippin.emuz.model;

public class Player {

    private Inventory inventory;

    public static Player createDefaultPlayer() {

        Inventory inventory = Inventory.createInfiniteInventory();
        Player player = new Player(inventory);

        return player;
    }

    public Player(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
