package com.b14h.libs;

public class Blub {

    private double ratio;

    public Blub(double ratio) {
        this.ratio = ratio;
    }

    public double toBlub(double eur){
        return eur / ratio ;
    }

    public double toEur(double blub){
        return blub / ratio;
    }


}
