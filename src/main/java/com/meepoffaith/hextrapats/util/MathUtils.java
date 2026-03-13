package com.meepoffaith.hextrapats.util;

import net.minecraft.util.math.Vec3d;

public class MathUtils{
    public static final double TAU = 2 * Math.PI;

    /** Modulo that works properly for negative numbers. Taken from Anuken/Arc. */
    public static double mod(double a, double b){
        return ((a % b) + b) % b;
    }

    public static double angleDist(double a, double b){
        a = mod(a, TAU);
        b = mod(b, TAU);

        double distBack = (a - b) < 0 ? a - b + TAU : a - b;
        double distFwd = (b - a) < 0 ? b - a + TAU : b - a;

        return Math.min(distBack, distFwd);
    }

    public static double vecAngleDist(Vec3d a, Vec3d b){
        double dot = a.dotProduct(b);
        double len2 = a.length() * b.length();

        return Math.acos(dot/len2);
    }
}
