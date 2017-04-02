package com.trippin.emuz.display;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.trippin.emuz.engine.ArenaMask;
import com.trippin.emuz.engine.Engine;
import com.trippin.emuz.model.EmuEntrance;

public class MainMenu
    extends JPanel
    implements ActionListener {

    private static final long serialVersionUID = 1L;

    private final JFrame parent;
    private final JButton startButton;

    public MainMenu(JFrame parent) {

        this.parent = parent;

        startButton = new JButton("Start");
        startButton.addActionListener(this);
        add(startButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == startButton)
            start();
    }

    private void start() {

        // Create random mask
        ArenaMask arenaMask = ArenaMask.createRandomArena(1000, 1000);
//        ArenaMask arenaMask = ArenaMask.createFlatArena(500, 500, 100);

        // Create a new engine
        EmuEntrance emuEntrance = new EmuEntrance(70, 30, 40, 10);
        List<EmuEntrance> emuEntrances = new ArrayList<>();
        emuEntrances.add(emuEntrance);
        Engine engine = new Engine(arenaMask, 20, emuEntrances);

        // Create the game panel
        GamePanel gamePanel = new GamePanel(parent, engine);
        Container parentContainer = parent.getContentPane();
        parentContainer.removeAll();
        parentContainer.add(gamePanel);
        parentContainer.validate();
    }
}
