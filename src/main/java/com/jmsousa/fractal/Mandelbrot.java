package com.jmsousa.fractal;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.complex.Complex;

@Slf4j
public class Mandelbrot extends Fractal {

    private final double epsilon;
    private final int iterations; // increasing this will give you a more detailed fractal

    public Mandelbrot(int size, int iterations) {
        super(size);
        this.size = size;
        this.iterations = iterations;

        this.epsilon = 4f / size;
    }

    public void runAlgorithm() {
        float x;
        float y;

        int half = (size / 2);
        int quart = half / 2;

        Complex z;
        Complex c;
        int iterations;
        for (x = -2; x <= 2; x += epsilon) {
            for (y = -2; y <= 2; y += epsilon) {
                int posX = Math.round(x * quart) + half;
                int posY = Math.round(y * quart) + half;

                if (posX >= size || posY >= size) {
                    log.warn("Position out of bounds. X={} Y={}", posX, posY);
                    continue;
                }

                iterations = 0;
                c = new Complex(x, y);
                z = new Complex(0, 0);
                while (z.abs() < 2 && iterations < this.iterations) {
                    z = z.multiply(z).add(c);
                    iterations++;
                }

                image.setRGB(posX, posY, colors.get(iterations));
            }
        }
    }
}
