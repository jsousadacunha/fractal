package com.jmsousa.fractal.util;

import com.jmsousa.fractal.Fractal;
import lombok.experimental.UtilityClass;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class ColorUtil {

    private static final int MAX_COLORS = 255;

    public enum COLOR {
        RED, GREEN, BLUE, NONE
    }

    public static List<Integer> getRandomColors(int nbColors) {
        List<Integer> colors = new ArrayList<>(nbColors);

        for (int i = 0; i < nbColors; i++) {
            int red = ThreadLocalRandom.current().nextInt(MAX_COLORS);
            int green = ThreadLocalRandom.current().nextInt(MAX_COLORS);
            int blue = ThreadLocalRandom.current().nextInt(MAX_COLORS);

            colors.add(new Color(red, green, blue).getRGB());
        }

        return colors;
    }

    public static List<Integer> getScaledColors(int red, int green, int blue, COLOR pivot, int increment, int nbColors) {
        List<Integer> colors = new ArrayList<>(nbColors);

        for (int i = 0; i < nbColors; i++) {
            if (i % MAX_COLORS == 0) {
                switch (pivot) {
                    case RED:
                        red += increment;
                        break;
                    case GREEN:
                        green += increment;
                        break;
                    case BLUE:
                        blue += increment;
                        break;
                }
            }

            if (pivot != COLOR.RED) {
                red += increment;
            }
            if (pivot != COLOR.GREEN) {
                green += increment;
            }
            if (pivot != COLOR.BLUE) {
                blue += increment;
            }

            colors.add(new Color(red % MAX_COLORS, green % MAX_COLORS, blue % MAX_COLORS).getRGB());
        }

        return colors;
    }

    public static List<Integer> getGradient(Color first, Color last, int nbColors) {
        List<Integer> colors = new ArrayList<>(nbColors);

        BufferedImage image = new BufferedImage(nbColors, 1, BufferedImage.TYPE_4BYTE_ABGR);
        GradientPaint gradient = new GradientPaint(0f, 0f, first, nbColors,0,last);
        Graphics2D g = image.createGraphics();

        g.setPaint(gradient);
        g.fillRect(0, 0, nbColors,1);

        for (int i = 0; i < nbColors; i++) {
            colors.add(image.getRGB(i, 0));
        }

        g.dispose();

        return colors;
    }
}
