package com.zcd.accumulation.jdkbase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TestCom {
    public static void main(String[] args) {
        List<Long> driverIds = new ArrayList<>();
        driverIds.add(35L);
        driverIds.add(1564L);
        String sql = new StringBuffer("select * from driver_attendance_config_ext where driverId in (")
                .append(driverIds.toString().replace("[", "").replace("]", ""))
                .append(") and delflag =1 ").toString();
        System.out.println(sql);
    }
}
