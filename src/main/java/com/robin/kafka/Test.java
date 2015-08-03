package com.robin.kafka;

/**
 * Created by robinmac on 15-7-30.
 */
public class Test {
    public static void main(String[] args) {
        Integer i=new Integer(3);
        Long l=2l;
        Double d=3d;
        l.hashCode();
        d.hashCode();
        String s="dddd";
        s.hashCode();
        i.hashCode();
        Long lv=(1l <<33)+2;
        System.out.println(lv >>> 32);
    }

}
