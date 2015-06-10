package com.chongbao.cbplayer.utils;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

public class BrightnessUtil {
	/**
	 * 获取屏幕亮度的模式（1，自动，0，手动）
	 * @return
	 */
	public static int getScreenBrightnessMode(Context context){
		int screenMode =0;
		try {
			screenMode = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
		} catch (Exception e) {
			screenMode = 0;
		}
		return screenMode;
	};
	/**
	 * 获取当前系统屏幕亮度值0--255
	 * @param context
	 * @return
	 */
	public static int getCurrentSystemBrightness(Context context){
		int light = 0;
		try {
			light = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
		} catch (Exception e) {
		}
		return light;
	}
	/**
	 * 设置屏幕亮度模式
	 * @param mode(1，自动，0，手动)
	 */
	public static void setBrightnesMode(Context context,int mode){
		if(mode<0){
			return;
		}
		if(mode>1){
			mode = 1;
		}
		try {
			Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, mode);
		} catch (Exception e) {
			
		}
	}
	/**
	 * 保存当前屏幕亮度
	 * @param context
	 * @param light
	 */
	public static void saveBrighness(Context context,int light){
		try {
			Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, light);
		} catch (Exception e) {
			
		}
	}
	/**
	 * 获取当前window的亮度
	 * @param context
	 * @return
	 */
	public static float getCurrentActivityBrightness(Activity context){
		Window w = context.getWindow();
		WindowManager.LayoutParams params = w.getAttributes();
		return params.screenBrightness;
	}
	
	/**
	 * 设置屏幕亮度
	 */
	public static void setBrightness(Activity context,float light){
		Window w = context.getWindow();
		WindowManager.LayoutParams params = w.getAttributes();
		params.screenBrightness = light;
		w.setAttributes(params);
	}
	
}
