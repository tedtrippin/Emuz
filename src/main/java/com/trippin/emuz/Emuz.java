package com.trippin.emuz;

import javax.swing.JFrame;

import com.trippin.emuz.display.MainMenu;

public class Emuz extends JFrame {

    private static final long serialVersionUID = 1L;

    public static void main(String args[]) {

        Emuz emuz = new Emuz();
        emuz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        emuz.setSize(500, 500);
        emuz.setVisible(true);
    }

    public Emuz() {

        add(new MainMenu(this));
    }
}
