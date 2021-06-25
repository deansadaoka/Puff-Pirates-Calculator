package com.puffpiratestudios;

public class EnergyInput {
    public double a, b, c, z;

    public EnergyInput(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.z = a + b + c;
    }
}
