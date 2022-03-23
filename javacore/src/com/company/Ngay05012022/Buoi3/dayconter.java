package com.company.Ngay05012022.Buoi3;

import java.util.Scanner;

public class dayconter {
    public static void main(String[] args) {
        countDays();
    }
   public static void countDays(){
        int year;
        year = nhap();
        for(int i = 1; i <=12 ;i++){
            System.out.println("Tháng ");
            if((i==2)){
                if((year %4==0 && year%100 != 0) || year%400 == 0){
                    for(int j = 1;j<=29;j++){
                        System.out.println("\tNgày "+j+"Tháng "+i+"Năm "+year);
                    }
                }else{
                    for (int j=1;j<=28;j++){
                        System.out.println("\tNgày "+j+"Tháng "+i+" Năm "+year);
                    }
                }
            }else{
                if(i == 4 || i== 6 || i == 9 || i == 11){
                    for(int j = 1;j<=30;j++){
                        System.out.println("\tNgày "+j+"Tháng "+i+"Năm "+year);
                    }
                }else if(i == 1 || i == 3 || i == 5 || i==7 || i==10 || i ==12){
                    for(int j = 1;j<=31;j++){
                        System.out.println("\tNgày "+j+"Tháng "+i+"Năm "+year);
                    }
                }
            }
        }
    }
    public static int nhap(){
        int year;
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập năm : ");
        while (true){
            try{
                year = sc.nextInt();
                if(String.valueOf(year).length() == 4){
                    break;
                }
                System.out.println("Nhập lại: ");
            }catch (Exception e){
                sc.nextLine();
            }
        }
        return year;
    }
}
