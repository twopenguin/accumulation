package com.zcd.accumulation;


public class Case003 {

    /**
     * 26. 删除排序数组中的重复项
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int curPos = 0;
        int preValue = nums[0];
        int i = 1;
        while(i<nums.length){
            if (nums[i] == preValue){
                i++;
            }else {
                curPos ++;
                preValue = nums[i];
                nums[curPos] = preValue;
            }
        }
        return curPos + 1;
    }

    public static void main(String[] args) {
        int[] nums = {0,0,1,1,1,2,2,3,3,4};
        Case003 case003 = new Case003();
        final int i = case003.removeDuplicates(nums);
        System.out.println(i);
        for (int j = 0; j < i; j++) {
            System.out.println(nums[j]);
        }
    }
}
