package com.jmsousa.fractal;

public class Mosaic01 extends Fractal {

    public Mosaic01(int size) {
        super(size);
    }

    public void runAlgorithm() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                image.setRGB(x, y, (x-y)*(x+y)&(x|y));
            }
        }
    }
}
