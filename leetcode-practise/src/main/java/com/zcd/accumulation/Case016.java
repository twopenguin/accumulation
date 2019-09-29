package com.zcd.accumulation;

public class Case016 {

    /**
     * 152. 乘积最大子序列
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        if (nums.length == 0) return 0;
        int tempMax = 1, tempMin = 1, max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 0){
                int temp = tempMax;
                tempMax = tempMin;
                tempMin = temp;
            }
            tempMax = Math.max(tempMax * nums[i], nums[i]);
            tempMin = Math.min(tempMin * nums[i], nums[i]);
            max = Math.max(max, tempMax);
        }
        return max;
    }
}
