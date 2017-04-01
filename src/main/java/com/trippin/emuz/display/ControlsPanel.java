package com.trippin.emuz.display;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.trippin.emuz.engine.Engine;
import com.trippin.emuz.model.EmuTypeEnum;
import com.trippin.emuz.model.Inventory;

public class ControlsPanel
    extends JPanel
    implements ActionListener {

    private final JFrame parent;
    private final Engine engine;
    private final JButton stopButton;
    private final Inventory inventory;

    private final JLabel currentEmuTypeLabel;
    private final JButton bridgeBuilderButton;
    private final JButton stopperButton;

    private static final long serialVersionUID = 1L;

    ControlsPanel(JFrame parent, Engine engine, Inventory inventory) {

        this.parent = parent;
        this.engine = engine;
        this.inventory = inventory;

        setMinimumSize(new Dimension(100, 20));

        bridgeBuilderButton = new JButton("Bridge Builder");
        bridgeBuilderButton.addActionListener(this);
        add(bridgeBuilderButton);

        stopperButton = new JButton("Stopper");
        stopperButton.addActionListener(this);
        add(stopperButton);

        currentEmuTypeLabel = new JLabel();
        add(currentEmuTypeLabel);
        setCurrentEmuType(EmuTypeEnum.BRIDGE_BUILDER);

        stopButton = new JButton("stop");
        stopButton.addActionListener(this);
        add(stopButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == bridgeBuilderButton) {
            setCurrentEmuType(EmuTypeEnum.BRIDGE_BUILDER);
        } else if (e.getSource() == stopperButton) {
            setCurrentEmuType(EmuTypeEnum.STOPPER);
        } else if (e.getSource() == stopButton) {
            stop();
        }
    }

    private void setCurrentEmuType(EmuTypeEnum type) {

        inventory.setCurrentEmutType(type);
        currentEmuTypeLabel.setText(type.toString());
    }

    private void stop() {

        engine.stop();

        Container parentContainer = parent.getContentPane();
        parentContainer.removeAll();
        parentContainer.add(new MainMenu(parent));
        parentContainer.validate();
    }
}
