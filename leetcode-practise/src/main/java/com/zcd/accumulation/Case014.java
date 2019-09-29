package com.zcd.accumulation;

public class Case014 {

    /**
     * 136. 只出现一次的数字
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {4,1,2,1,2};
        Case014 case014 = new Case014();
        final int i = case014.singleNumber(array);
        System.out.println(i);
    }
    
}
