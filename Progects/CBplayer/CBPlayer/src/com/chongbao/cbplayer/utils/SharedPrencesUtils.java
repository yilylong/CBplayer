package com.chongbao.cbplayer.utils;

import com.chongbao.cbplayer.constans.Constans;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrencesUtils {
	public static SharedPrencesUtils instance = null;
	private SharedPreferences mSharedPreference;		
	private SharedPrencesUtils(Context context){
		mSharedPreference = context.getSharedPreferences(Constans.SHAREDPRENCES_NAME, context.MODE_PRIVATE);
	}
	public synchronized static SharedPrencesUtils getInstance(Context context){
		if(instance==null){
			instance = new SharedPrencesUtils(context);
		}
		return instance;
	}
	public void saveString(String key,String value){
		mSharedPreference.edit().putString(key, value).commit();
	};
	public void saveFloat(String key,float value){
		mSharedPreference.edit().putFloat(key, value).commit();
	};
	public void saveBoolean(String key,boolean value){
		mSharedPreference.edit().putBoolean(key, value).commit();
	};
	
	public float getFloat(String key){
		return mSharedPreference.getFloat(key, -1);
	}
}
