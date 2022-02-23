package com.jmsousa.fractal;

import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Data
public abstract class Fractal {

    protected List<Integer> colors;
    protected int size = 800;
    protected boolean mosaic = false;
    protected int nbMosaics = 16;

    protected final BufferedImage image;

    protected Fractal(int size) {
        this.image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
    }

    public void print(String name) {
        runAlgorithm();
        save(name);
    }

    public void setColors(List<Integer> colors) {
        this.colors = colors;
    }

    public void setMosaic(int number) {
        this.mosaic = true;
        this.nbMosaics = number;
    }

    protected abstract void runAlgorithm();

    private void save(String name) {
        File file = new File(name + ".png");
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
