package com.trippin.emuz.misc;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.Ignore;
import org.junit.Test;

import com.trippin.emuz.engine.ArenaMask;

@Ignore
public class TestMaskMerge {

    @Test
    public void test_background()
        throws Exception {

        URL backgroundUrl = ArenaMask.class.getClassLoader().getResource("background1.jpg");
        BufferedImage background = ImageIO.read(backgroundUrl);

        BufferedImage mask = new BufferedImage(2000, 1000, BufferedImage.TYPE_BYTE_BINARY);
        Graphics g = mask.getGraphics();
        g.fillRect(100, 100, 500, 500);

        int[] backgroundPixels = background.getRGB(0, 0, 2000, 1000, null, 0, 2000);
        int[] maskPixels = mask.getRGB(0, 0, 2000, 1000, null, 0, 2000);

        int txOffset = 0;
        int tyOffset = 0;
        for (int i = 0; i < backgroundPixels.length; i++) {

            int isBlack = maskPixels[i];
            int color = backgroundPixels[i];
            if (isBlack == -1)
                backgroundPixels[i] = 0;
        }

        background.setRGB(0, 0, 2000, 1000, backgroundPixels, 0, 2000);

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel l = new JLabel();
        l.setIcon(new ImageIcon(background));
        f.add(l);

        f.pack();
        f.setVisible(true);

        synchronized (this) {
            wait(5000);
        }

    }


    @Test
    public void test_texturePaint()
        throws Exception {

        int arenaWidth = 2000;
        int arenaHeight = 1000;

        URL textureUrl = ArenaMask.class.getClassLoader().getResource("texture1.jpg");
        BufferedImage texture = ImageIO.read(textureUrl);

        BufferedImage map = new BufferedImage(arenaWidth, arenaHeight, texture.getType());

        for (int i = 0; i < arenaWidth; ) {
            for (int j = 0; j < arenaHeight; ) {
                map.getGraphics().drawImage(texture, i, j, null);
                j += texture.getHeight();
            }
            i += texture.getWidth();
        }

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel l = new JLabel();
        l.setIcon(new ImageIcon(map));
        f.add(l);

        f.pack();
        f.setVisible(true);

        synchronized (this) {
            wait(5000);
        }
    }

    @Test
    public void test_texture()
        throws Exception {

        int arenaWidth = 2000;
        int arenaHeight = 1000;

        URL textureUrl = ArenaMask.class.getClassLoader().getResource("texture1.jpg");
        BufferedImage texture = ImageIO.read(textureUrl);

        ColorModel cm = texture.getColorModel();
        BufferedImage map = new BufferedImage(cm, texture.getRaster(), cm.isAlphaPremultiplied(), null);

        BufferedImage mask = new BufferedImage(arenaWidth, arenaHeight, BufferedImage.TYPE_BYTE_BINARY);
        Graphics g = mask.getGraphics();
        g.fillRect(1, 1, 5, 5);

        int tWidth = texture.getWidth();
        int tHeight = texture.getHeight();
        int[] texturePixels = texture.getRGB(0, 0, arenaWidth, arenaHeight, null, 0, arenaWidth);
        int[] maskPixels = mask.getRGB(0, 0, arenaWidth, arenaHeight, null, 0, arenaWidth);

        int tyOffset = 0;
        for (int row = 0; row < arenaHeight; row++) {

            int offset = row * arenaHeight;
            int txOffset = 0;

            for (int column = 0; column < arenaWidth; column++) {

                int isBlack = maskPixels[offset + column];
                if (isBlack == -1)
                    texturePixels[tyOffset + txOffset] = 0;

                txOffset++;
                if (txOffset > tWidth)
                    txOffset = 0;
            }

            tyOffset++;
            if (tyOffset > tHeight)
                tyOffset = 0;
        }

        map.setRGB(0, 0, arenaWidth, arenaHeight, texturePixels, 0, arenaWidth);

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel l = new JLabel();
        l.setIcon(new ImageIcon(map));
        f.add(l);

        f.pack();
        f.setVisible(true);

        synchronized (this) {
            wait(5000);
        }
    }
}
