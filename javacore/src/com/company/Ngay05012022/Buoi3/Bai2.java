package com.company.Ngay05012022.Buoi3;

public class Bai2 {
    public static void main(String[] args) {
        toNumber("ten");
    }
    public static void toNumber(String s){
        s = s.toLowerCase();
        //lấy 2 kí tự đầu
        char kitu1 = s.charAt(0);
        char kitu2=s.charAt(1);

        long number = 0;
        switch (kitu1){
            case 'o':
                    number = 1L;
                    break;
            case 't':
                if(kitu2 == 'w'){
                    number = 2L;
                }
                if(kitu2 == 'h'){
                    number = 3L;
                }
                if(kitu2 == 'e'){
                    number = 10L;
                }
                break;
            case 'f':
                if(kitu2 == 'o'){
                    number = 4L;
                }
                if(kitu2 == 'i'){
                    number = 5L;
                }
                break;
            case 's':
                if(kitu2 == 'i'){
                    number = 6L;
                }
                if(kitu2 == 'e'){
                    number = 7l;
                }
                break;
            case 'e' :
                    number = 8L;
                    break;
            case 'n':
                    number = 9L;
                    break;
        }
        System.out.println("the number is : "+number);
    }
}
