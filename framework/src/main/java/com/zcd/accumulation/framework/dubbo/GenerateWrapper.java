package com.zcd.accumulation.framework.dubbo;

import com.alibaba.dubbo.common.bytecode.Wrapper;
import com.zcd.accumulation.framework.dubbo.service.IDemoService;

public class GenerateWrapper {
    public static void main(String[] args) {
        Wrapper wrapper = Wrapper.getWrapper(IDemoService.class);
        
    }
}
