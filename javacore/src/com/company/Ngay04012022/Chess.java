package com.company.Ngay04012022;

import java.util.Scanner;

public  class Chess {
    public static String color;
    public static int startingPosition;

    public static void nhap(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập màu : ");
        String color = sc.nextLine();
        System.out.println("Nhập điểm bắt đầu : ");
        int startingPosition= sc.nextInt();
        sc.nextLine();
        System.out.println("Cách di chuyển : ");
        String forwardMovement = sc.nextLine();
        System.out.println("....");
        String sideMovement = sc.nextLine();
    }
}
