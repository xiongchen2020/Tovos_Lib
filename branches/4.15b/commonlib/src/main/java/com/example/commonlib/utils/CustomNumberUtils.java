package com.example.commonlib.utils;

import java.math.BigDecimal;
import java.util.Formatter;
import java.util.UUID;

public class CustomNumberUtils {

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
    /**
     * DecimalFormat转换最简便
     */
    public static float keep2DecimalPplaces(double f) {
        float f1 = 0f;
        if (isNumeric(f+"")){
            if (judgeIsDecimal(f+"")){
                BigDecimal bg = new BigDecimal(f);
                f1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();

            }else {
                f1 = (float) f;
            }
        }else {
            f1 = 0f;
        }
        return f1;
    }


    public static String format4(double value) {

        /**
         * %.2f % 表示 小数点前任意位数 2 表示两位小数 格式后的结果为 f 表示浮点型
         **/


        return new Formatter().format("%.2f", value).toString();

    }



    public static String format5(double value) {

        /**
         * %.2f % 表示 小数点前任意位数 2 表示两位小数 格式后的结果为 f 表示浮点型
         **/


        return new Formatter().format("%.4f", value).toString();

    }

    public static String  format6(float value){
        /**
         * %.2f % 表示 小数点前任意位数 2 表示两位小数 格式后的结果为 f 表示浮点型
         **/


        return new Formatter().format("%.4f", value).toString();
    }
    public static boolean judgeIsDecimal(String num){

        boolean isdecimal = false;

        if (num.contains(".")) {

            isdecimal=true;

        }

        return isdecimal;

    }

    public static String getUUID() {

        String uuidStr = UUID.randomUUID().toString();
        uuidStr = uuidStr.substring(0, 8) + uuidStr.substring(9, 13)
                + uuidStr.substring(14, 18) + uuidStr.substring(19, 23)
                + uuidStr.substring(24);
        return uuidStr;
    }
}
