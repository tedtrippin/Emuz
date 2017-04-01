package com.trippin.emuz.display;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JViewport;

import com.trippin.emuz.engine.Engine;
import com.trippin.emuz.model.Emu;
import com.trippin.emuz.model.EmuTypeEnum;
import com.trippin.emuz.model.Player;

public class ArenaPanel
    extends JPanel {

    private static final long serialVersionUID = 1L;
    private final Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    private final Cursor highlightedCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);

    private final JViewport parent;
    private final Engine engine;
    private final Player player;
    private int mouseX;
    private int mouseY;

    ArenaPanel (JViewport parent, Engine engine, Player player) {

        this.parent = parent;
        this.engine = engine;
        this.player = player;

        setPreferredSize(engine.getSize());
        setAutoscrolls(true);

        MouseListener dragListener = new MouseListener();
        addMouseListener(dragListener);
        addMouseMotionListener(dragListener);
    }

    int i = 0;
    @Override
    public void paint(Graphics g) {

        super.paint(g);

        engine.getArenaMask().prePaint();

        // TODO - for now we're just using the mask as the map
        g.drawImage(engine.getArenaMask().getMapMask(), 0, 0, null);

        g.setColor(Color.GREEN);

        // TODO - Why does this throw ConcurrentModificationException???
        engine.getThings().forEach(t -> t.paint(g, engine));

        // Check if mouse over emu
        if (engine.getArenaMask().isEmu(mouseX, mouseY))
            setCursor(highlightedCursor);
        else
            setCursor(defaultCursor);
    }

    /**
     * MouseAdapter used for dragging the arena with mouse
     * and for clicking on an emu.
     *
     * @author robert.haycock
     *
     */
    private class MouseListener extends MouseAdapter {

        private Point origin;

        @Override
        public void mouseClicked(MouseEvent e) {

            Emu emu = engine.getEmu(e.getX(), e.getY());
            if (emu == null) {
System.out.println("NO EMU");
                return;
            }

            if (!player.getInventory().inStock())
                return;

            EmuTypeEnum type = player.getInventory().pop();
            emu.setType(type);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            origin = new Point(e.getPoint());
        }

        @Override
        public void mouseDragged(MouseEvent e) {

            if (origin == null)
                return;

            int deltaX = origin.x - e.getX();
            int deltaY = origin.y - e.getY();

            Rectangle view = parent.getViewRect();
            view.x += deltaX;
            view.y += deltaY;
            scrollRectToVisible(view);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            ArenaPanel.this.mouseX = e.getX();
            ArenaPanel.this.mouseY = e.getY();
        }
    }
}