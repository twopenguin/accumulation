package com.zcd.accumulation;

public class Case020 {

    /**
     * 191. 位1的个数
     */
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int sum = 0;
        while(n != 0){
            sum++;
            n&=(n-1);
        }
        return sum;
    }

    public static void main(String[] args) {
        Case020 case020 = new Case020();
        final int i = case020.hammingWeight(-3);
        System.out.println(i);
    }
}
