package com.jmsousa.fractal;

import com.jmsousa.fractal.util.ColorUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.complex.Complex;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

@Slf4j
public class ZoomedMandelbrot extends Fractal {

    private final double epsilon;
    private final int iterations; // increasing this will give you a more detailed fractal
    private final double distance;
    private final double positionX;
    private final double positionY;

    public ZoomedMandelbrot(int size, int iterations, int zoom, double positionX, double positionY) {
        super(size);
        this.size = size;
        this.iterations = iterations;
        this.positionX = positionX;
        this.positionY = positionY;

        this.distance = 4d / zoom;
        this.epsilon = distance / size;
    }

    public void runAlgorithm() {

        int parts = nbMosaics;
        double algoCanvasSize = distance / parts;

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(16);
        taskExecutor.setMaxPoolSize(16);
        taskExecutor.afterPropertiesSet();

        for (int i = 0; i < parts; i++) {
            for (int j = 0; j < parts; j++) {
                Algorithm algorithm = new Algorithm(positionX + (i * algoCanvasSize), positionY + (j * algoCanvasSize),
                        positionX + ((i + 1) * algoCanvasSize), positionY + ((j + 1) * algoCanvasSize));

                int x = i;
                int y = j;
                taskExecutor.execute(() -> algorithm.execute(x, y, parts));
            }
        }

        while (taskExecutor.getActiveCount() > 0) {
            // Waiting
        }

        taskExecutor.destroy();

        log.info("Finish");
    }

    @RequiredArgsConstructor
    @Getter
    private class Algorithm {

        private final double startX;
        private final double startY;
        private final double offsetX;
        private final double offsetY;

        public void execute(int sliceX, int sliceY, int parts) {
            final int posX = (size / parts) * sliceX;
            final int posY = (size / parts) * sliceY;
            int countX = posX;
            int countY = posY;

            List<Integer> mosaicColors;
            if (mosaic) {
                mosaicColors = ColorUtil.getRandomColors(iterations);
//                mosaicColors = ColorUtil.getScaledColors(17, 183, 195, ColorUtil.COLOR.RED, 5 - Math.abs(sliceX - sliceY), iterations + 1);
            } else {
                mosaicColors = colors;
            }

            for (double x = startX; x < offsetX; x += epsilon) {
                for (double y = startY; y < offsetY; y += epsilon) {
                    if (countX >= size || countY >= size) {
                        continue;
                    }

                    int count = 0;
                    Complex c = new Complex(x, y);
                    Complex z = new Complex(0, 0);
                    while (z.abs() < 2 && count < iterations - 1) {
                        z = z.multiply(z).add(c);
                        count++;
                    }

                    image.setRGB(countX, countY, mosaicColors.get(count));
                    countY++;
                }
                countY = posY;
                countX++;
            }
        }
    }
}
