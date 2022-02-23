package com.jmsousa.fractal;

import com.jmsousa.fractal.util.ColorUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;

@SpringBootApplication
public class FractalApplication {

    public static void main(String[] args) {
        SpringApplication.run(FractalApplication.class, args);

        runZoomedMandelbrot();
    }

    private static void runMandelbrot() {
        Fractal fractal = new Mandelbrot(800, 500);
        fractal.setColors(ColorUtil.getScaledColors(6, 82, 166, ColorUtil.COLOR.BLUE, 10, 50));
        fractal.print("mandelbrot-15");
    }

    private static void runZoomedMandelbrot() {
        int size = 3600;
        int iterations = 1280;
        int zoom = 8;
        double positionX = -0.75;
        double positionY = -0.8;
        Fractal fractal = new ZoomedMandelbrot(size, iterations, zoom, positionX, positionY);
//        fractal.setColors(ColorUtil.getScaledColors(0, 0, 0, ColorUtil.COLOR.NONE, 1, iterations + 1));
        fractal.setColors(ColorUtil.getGradient(Color.BLACK, Color.WHITE, iterations));
        fractal.print("mandelbrot-gradient-" + size + "-" + iterations + "-" + zoom + "-" + positionX + "-" + positionY);
    }

    private static void runMosaicMandelbrot() {
        int size = 16000;
        int iterations = 50;
        int zoom = 1;
        double positionX = -2;
        double positionY = -2;
        Fractal fractal = new ZoomedMandelbrot(size, iterations, zoom, positionX, positionY);
        fractal.setMosaic(2);
        fractal.print("mandelbrot-mosaic-" + size + "-" + iterations + "-" + zoom + "-" + positionX + "-" + positionY);
    }
}
