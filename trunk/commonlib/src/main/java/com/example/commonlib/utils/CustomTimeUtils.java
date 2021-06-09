package com.example.commonlib.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomTimeUtils {

    /**
     * 判断2个时间大小
     * yyyy-MM-dd HH:mm 格式（自己可以修改成想要的时间格式）
     *
     * @param startTime
     * @param endTime
     * @param timeformat "yyyy-MM-dd HH:mm"
     * @return // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
     */
    public static int timeCompare(String startTime, String endTime, String timeformat) {
        int i = 0;
        //注意：传过来的时间格式必须要和这里填入的时间格式相同
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeformat);
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            if (date2.getTime() < date1.getTime()) {
                //结束时间小于开始时间
                i = 1;
            } else if (date2.getTime() == date1.getTime()) {
                //开始时间与结束时间相同
                i = 2;
            } else if (date2.getTime() > date1.getTime()) {
                //结束时间大于开始时间
                i = 3;
            }
        } catch (Exception e) {

        }
        Log.e("TimeUtils", "比较值:" + i);
        return i;
    }

    /**
     * @param timeformat "yyyy-MM-dd  HH:mm:ss"
     * @return
     */
    public static String getNowTime(String timeformat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeformat);// HH:mm:ss

        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date) + "";
    }

    /**
     * long转date
     *
     * @param dateLong
     * @return
     */
    public static Date longToDate(long dateLong, String timeformat) {
        Date dateOld = new Date(dateLong); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, timeformat); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, timeformat); // 把String类型转换为Date类型
        return date;

    }

    /**
     * date转long
     *
     * @param date
     * @return
     */
    public static long dateToLong(Date date) {
        long dateLong = date.getTime();
        return dateLong;
    }

    /**
     * string转date
     *
     * @param str
     * @param timeformat "yyyy-MM-dd HH:mm:ss"
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String str, String timeformat) {
        DateFormat df = new SimpleDateFormat(timeformat);
        Date date = null;
        try {
            date = df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * date转string
     *
     * @param date
     * @param timeformat "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String dateToString(Date date, String timeformat) {
        SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
        String str = sdf.format(date);
        return str;
    }

    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    public static String getNowDate() {
        String sim = "";
        Date date = new Date();
        String time = date.toLocaleString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        sim = dateFormat.format(date);
        Log.e("1111", "现在时间:" + sim);
        return sim;
    }

    public static String ScendsToHours(long times) {
        String time = "";
        int hours = 0;
        hours = (int) ((times / 1000) / 3600);
        int minutes = (int) ((times / 1000) / 60 - hours * 60);
        int seconds = (int) (times/1000 - minutes * 60 - hours * 3600);
        if (hours > 0) {
            time = hours + "时" + minutes + "分" + seconds + "秒";
        } else {
            time = minutes + "分" + seconds + "秒";
        }

        return time;
    }

    public static String ScendsToHoursBySec(float times) {
        ToastUtils.setResultToToast("任务时间:"+times+"秒");
        String time = "";
        int hours = 0;
        hours = (int) (times / 3600);
        int minutes = (int) (times / 60 - hours * 60);
        int seconds = (int) (times - minutes * 60 - hours * 3600);
        if (hours > 0) {
            time = hours + "时" + minutes + "分" + seconds + "秒";
        } else {
            time = minutes + "分" + seconds + "秒";
        }

        return time;
    }

    public static String ScendsToHoursBySecInt(int times) {
        Date date = new Date(times);
        DateFormat format = new SimpleDateFormat("mm:ss");
        return format.format(date);
    }

    /**
     * 获取7天前的日期
     */
    public static long  getWeekStartTime(long nowTime){
        Date newDate = new Date(nowTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        return calendar.getTime().getTime();

    }


    /**
     * 时间戳中获取年
     */
    public static String getYearFromTime(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy");
        return sf.format(d);
    }

    /**
     * 时间戳中获取月
     */
    public static String getMonthFromTime(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("MM");
        return sf.format(d);
    }

    /**
     * 时间戳中获取日
     */
    public static String getDayFromTime(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("dd");
        return sf.format(d);
    }
}
