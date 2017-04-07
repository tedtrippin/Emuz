package com.trippin.emuz.engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ArenaMask {

    private final int arenaWidth;
    private final int arenaHeight;
    private BufferedImage map; // Mask for the map pixels
    private BufferedImage emuMask; // Mask for emuz, for mouse-over-emu detection

    public static ArenaMask createRandomArena(int width, int height)
        throws IOException {

        URL textureUrl = ArenaMask.class.getClassLoader().getResource("texture1.jpg");
        BufferedImage texture = ImageIO.read(textureUrl);

        BufferedImage mask = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        Graphics g = mask.getGraphics();

        // Draw sides
        g.fillRect(0, 0, 20, height);
        g.fillRect(width - 20, 0, width, height);

        int nextY;
        int x = 20;
        int y = height/2;
        double gradient = 0;

        // Draw random line across
        while (x < width-20) {

            // Randomly change gradient
            gradient += Math.random() - 0.5;
            if (gradient > 5)
                gradient = 5;
            else if (gradient < -5)
                gradient = -5;

            nextY = (int)((double)y + (4.0 * gradient));

            // Plateau if we get too low/high
            if (nextY < 10) {
                gradient = 0;
                nextY = 10;
            } else if (nextY > height-50) {
                gradient = 0;
                nextY = height-10;
            }

            g.fillPolygon(new int[] {x, x, x+20, x+20}, new int[] {height, y, nextY, height}, 4);

            x += 20;
            y = nextY;
        }

        return new ArenaMask(mask, texture);
    }

    public ArenaMask(BufferedImage mask, BufferedImage texture) {

        arenaWidth = mask.getWidth();
        arenaHeight = mask.getHeight();
        emuMask = new BufferedImage(arenaWidth, arenaHeight, BufferedImage.TYPE_BYTE_BINARY);

        applyTexture(mask, texture);
    }

    public void prePaint() {
        emuMask = new BufferedImage(arenaWidth, arenaHeight, BufferedImage.TYPE_BYTE_BINARY);
    }

    /**
     * Gets the requested pixels from the map.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public int[] getPixels(int x, int y, int width, int height) {

        int[] pixels = new int[width * height];
        return map.getRGB(x, y, width, height, pixels, 0, width);
    }

    /**
     * Returns true if the given coords are over an emu.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isEmu(int x, int y) {

        if (x < 0 || x > arenaWidth || y < 0 || y > arenaHeight)
            return false;

        return emuMask.getRGB(x, y) == -1;
    }

    public BufferedImage getMap() {
        return map;
    }

    public BufferedImage getEmuMask() {
        return emuMask;
    }

    public int getArenaWidth() {
        return arenaWidth;
    }

    public int getArenaHeight() {
        return arenaHeight;
    }

    private void applyTexture(BufferedImage mask, BufferedImage texture) {

        // Fill the map with the texture
        map = new BufferedImage(arenaWidth, arenaHeight, texture.getType());
        for (int i = 0; i < arenaWidth; ) {
            for (int j = 0; j < arenaHeight; ) {
                map.getGraphics().drawImage(texture, i, j, null);
                j += texture.getHeight();
            }
            i += texture.getWidth();
        }

        // Apply map
        int[] mapPixels = map.getRGB(0, 0, arenaWidth, arenaHeight, null, 0, arenaWidth);
        int[] maskPixels = mask.getRGB(0, 0, arenaWidth, arenaHeight, null, 0, arenaWidth);
        for (int i = 0; i < mapPixels.length; i++) {
            if (maskPixels[i] != -1)
                mapPixels[i] = 0;
        }
        map.setRGB(0, 0, arenaWidth, arenaHeight, mapPixels, 0, arenaWidth);
    }
}
