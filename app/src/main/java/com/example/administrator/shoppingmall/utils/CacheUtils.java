package com.example.administrator.shoppingmall.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/8/7.
 */

public class CacheUtils {

	/**
	 * 得到保存的String类型的数据
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getString(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
		return sp.getString(key,"");
	}

	/**
	 * 保存String类型的数据
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveString(Context context, String key,String value) {
		SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
		sp.edit().putString(key,value).commit();
	}
}
