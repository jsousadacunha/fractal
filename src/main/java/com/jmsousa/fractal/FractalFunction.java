package com.jmsousa.fractal;

import org.apache.commons.math3.complex.Complex;

@FunctionalInterface
public interface FractalFunction {

    String MANDELBROT = "mandelbrot";

    void execute(Complex z, Complex c);
}
