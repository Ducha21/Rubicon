package com.company.Ngay05012022.Buoi2;

import java.awt.*;

public class FourDPoint extends Point {
    int z,t;
    public  FourDPoint(int x, int y, int z, int t) {
        super(x, y);
        this.z = z;
        this.t = t;
    }

    public static void main(String[] args) {
      FourDPoint fourDPoint = new FourDPoint(5,5,10,110);
        System.out.println(fourDPoint.x);
        System.out.println(fourDPoint.y);
        System.out.println(fourDPoint.z);
        System.out.println(fourDPoint.t);
    }
}
