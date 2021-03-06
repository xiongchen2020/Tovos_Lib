package com.example.commonlib.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.commonlib.CommonContext;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;



/**
 * SharedPreference管理工具类
 */
public class SPUtils {

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";

    private SPUtils() {}

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public static void put(String key, Object object) {
        if (object == null){
            return;
        }

        SharedPreferences sp = CommonContext.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        editor.apply();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对应的方法获取值
     */
    public static Object get(String key, Object defaultObject) {
        SharedPreferences sp = CommonContext.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }


        return null;
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(String key) {
        if (!contains(key)){
            return;
        }
        SharedPreferences sp =  CommonContext.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sp =  CommonContext.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(String key) {
        SharedPreferences sp =  CommonContext.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp =  CommonContext.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        private SharedPreferencesCompat() {
        }

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
               // LogUtil.e("SPUtils反射异常");
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                //LogUtil.e("SPUtils异常");
            }
            editor.commit();
        }
    }

//    public static void saveRtkInfo(String ip,String user,String pwd,String post,String gzd){
//        Context context;
//        SharedPreferences shared = CommonContext.getContext().getSharedPreferences("rtk_data", MODE_PRIVATE);
//        //获取  SharedPreferences.Editor 对象
//        SharedPreferences.Editor editor = shared.edit();
//        //以键值对方式传入数据
//        editor.putString("ip", ip);
//        editor.putString("user", user);
//        editor.putString("pwd", pwd);
//        editor.putString("gzd", gzd);
//        editor.putString("post", post);
//        //提交数据
//        editor.commit();
//    }
//


}
