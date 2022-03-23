package com.company.Ngay05012022.Buoi2;

public class Bai1 {
    public static void main(String[] args) {
        int tatol = 14000;
        tatol += tatol*0.4;
        System.out.println("Số tiền sau năm 1 : "+tatol);
        tatol=tatol-1500;
        System.out.println("Số tiền sau năm 2 : "+tatol);
        tatol+=tatol*0.12;
        System.out.println("Số tiền sau năm 3 : "+tatol);
    }
}
