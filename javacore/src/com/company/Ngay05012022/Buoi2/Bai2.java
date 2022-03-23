package com.company.Ngay05012022.Buoi2;

import java.util.Scanner;

public class Bai2 {
    public static void main(String[] args) {
        bai2();
    }

    public static void bai2(){
        int a,b;
        System.out.println("Nhập a = ");
        a = nhap();
        System.out.println("Nhập b = ");
        b = nhap();
        System.out.print("Kết quả : "+ (a/b)+"\t");
        System.out.print("Kết quả 2 : "+a%b);
    }
    public static int nhap(){
        Scanner sc  = new Scanner(System.in);
        int n;
        while(true){
            try{
                n = sc.nextInt();
                break;
            }catch (Exception e){
                System.out.println();
                sc.nextLine();
            }
        }
        return n;
    }
}
