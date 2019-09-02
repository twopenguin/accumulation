package com.zcd.accumulation.common.util;

import java.util.*;

public class DateUtil_7 {
    /**
     * 获取两个时间之间的日期,排除了最后一天，即 endDate 所在日期
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static List<Date> dateSplit(Date startDate, Date endDate) throws Exception {
        if (!startDate.before(endDate)) {
            throw new Exception("开始时间应该在结束时间之后");
        }
        Long spi = endDate.getTime() - startDate.getTime();
        // 相隔天数
        Long step = spi / (24 * 60 * 60 * 1000);

        List<Date> dateList = new ArrayList<>();
        dateList.add(endDate);
        for (int i = 1; i <= step; i++) {
            // 比上一天减一
            dateList.add(new Date(dateList.get(i - 1).getTime() - (24 * 60 * 60 * 1000)));
        }
        //把最后一天排除在外
        dateList.remove(0);
        return dateList;
    }

    /**
     * 获取传入时间当前天的最后时间
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取指定时间月第一天
     * @param date
     * @return
     */
    public static Date getCurrentStartMonthByTime(Date date) {
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(date);
        //设置为第一天
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        gcLast.set(Calendar.HOUR_OF_DAY, 0);
        gcLast.set(Calendar.MINUTE, 0);
        gcLast.set(Calendar.SECOND, 0);
        gcLast.set(Calendar.MILLISECOND, 0);
        return gcLast.getTime();
    }

    /**
     * 获取指定时间月最后一天
     * @param date
     * @return
     */
    public static Date getCurrentEndMonthByTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //设置日期为本月最大日期
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 判断两个时间是否为同一天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 获取传入的时间是周几
     * @return
     */
    public static int getDayOfWeek(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w == 0 ? 7 : w;
    }

    /**
     * 获取传入时间所在月的最后一个工作
     *
     * @return
     */
    public static Date getLastWorkDayOfThisMonth(Date date) {
        Date currentEndMonthByTime = getCurrentEndMonthByTime(date);
        int dayOfWeek = getDayOfWeek(currentEndMonthByTime);

        if (dayOfWeek >= 6) {
            final long newTime = currentEndMonthByTime.getTime() - (dayOfWeek - 5) * 24 * 60 * 60 * 1000;
            Date result = new Date();
            result.setTime(newTime);
            return result;
        }
        return currentEndMonthByTime;
    }
}
