package com.company.Ngay06012022;

public class PrimeThreads {
    public static void main(String[] args) {
        PrimeThreads pt = new PrimeThreads(new String[]{"1","2","3"});
    }
    public PrimeThreads(String[] target){
        try{
            PrimeFinder[] finders = new PrimeFinder[target.length];
            for(int i = 0 ;i<target.length;i++){
                try{
                    long conut = Long.parseLong(target[i]);
                    System.out.println("Looking for prime : "+conut);
                }catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            boolean compele = false;
            while (!compele){
                compele = true;
                for(int j=0;j< finders.length;j++){
                    if(finders[j]==null)
                        continue;
                    if(!finders[j].aBoolean){
                        compele = false;
                    }else {
                        displayResult(finders[j]);
                        finders[j]=null;
                    }
                }
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayResult(PrimeFinder finder) {
        System.out.println("kết quả : "+finder.target+" is "+finder.prime);
    }
}
