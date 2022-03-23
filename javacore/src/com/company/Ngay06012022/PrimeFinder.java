package com.company.Ngay06012022;

public class PrimeFinder implements Runnable {
    public long target;
    public long prime;
    public boolean aBoolean = false;
    public Thread runner;

    public PrimeFinder(long target) throws NegativeNumberException {
        if(target<0){
            NegativeNumberException numberException = new NegativeNumberException("acb");
            throw numberException;
        }
        this.target = target;
        if(runner==null){
            runner=new Thread(this);
            runner.start();
        }
    }


    @Override
    public void run() {
        long number = 0 ;
        long kq = 2;
        while(number<target){
           if(check(kq)){
               number++;
               prime = kq;
           }
           kq++;
        }
        aBoolean = true;
        System.out.println("số nguyên tố thứ  :"+this.target+"\tlà: "+ prime);
    }

    public boolean check(long n){
        double canbahai = Math.sqrt(n);
        for(int i=2;i<=canbahai;i++){
            if(n%2==0){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws NegativeNumberException {
        PrimeFinder finder = new PrimeFinder(10);
    }
}
