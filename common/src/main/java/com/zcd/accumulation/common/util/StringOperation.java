package com.zcd.accumulation.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类中放一些，字符串的操作
 */
public class StringOperation {

    /**
     * 字符串拼接，比较不错的方式,使用 commons-lang 包下面的StringUtils.join 
     * @return
     */
    public static String stringJoin(){
        List<Long> coll = new ArrayList<>();
        return StringUtils.join(coll,",");
    }
    
}
