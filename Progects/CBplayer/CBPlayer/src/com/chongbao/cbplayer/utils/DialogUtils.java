package com.chongbao.cbplayer.utils;

import com.chongbao.cbplayer.R;
import com.chongbao.cbplayer.utils.DialogBuilder.onDialogbtnClickListener;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class DialogUtils {
	/**
	 * 显示一个弹框
	 * @param context
	 * @param title
	 * @param msg
	 * @return
	 */
	public static Dialog showDialog(Context context,String title,String msg){
		return new DialogBuilder(context).setTitle(title).setMessage(msg).create();
	}
	
	/**
	 * 显示一个从底部滑出的弹框
	 * @param context
	 * @param layoutStyle
	 * @param title
	 * @param msg
	 * @return
	 */
	public static Dialog showDialog(Context context,int layoutStyle,String title,String msg){
		if(layoutStyle<0){
			layoutStyle = DialogBuilder.DIALOG_STYLE_NOR;
		}
		return new DialogBuilder(context, 0, layoutStyle)
		.setDialoglocation(DialogBuilder.DIALOG_LOCATION_CENTER)
		.setWindowAnimation(DialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
		.setTitle(title)
		.setMessage(msg)
		.create();
	}
	/**
	 * SHOW一个系统级别的弹框
	 * @param context
	 * @param layoutStyle
	 * @param title
	 * @param msg
	 * @return
	 */
	public static Dialog showSystemDialog(Context context,int layoutStyle,String title,String msg){
		if(layoutStyle<0){
			layoutStyle = DialogBuilder.DIALOG_STYLE_NOR;
		}
		return new DialogBuilder(context, 0, layoutStyle,true)
		.setDialoglocation(DialogBuilder.DIALOG_LOCATION_CENTER)
		.setWindowAnimation(DialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
		.setTitle(title)
		.setMessage(msg)
		.create();
	}
	
	/**
	 * 显示只有一个确认按钮的弹框
	 * @param context
	 * @param layoutStyle
	 * @param title
	 * @param msg
	 * @return
	 */
	public static Dialog showSingleBtnDialog(Context context,String title,String msg,String btnmsg){
		return new DialogBuilder(context, 0, DialogBuilder.DIALOG_STYLE_BLUE)
		.setDialoglocation(DialogBuilder.DIALOG_LOCATION_CENTER)
		.setWindowAnimation(DialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
		.setButtons(btnmsg, null, true, null)
		.setTitle(title)
		.setMessage(msg)
		.create();
	}
	/**
	 * 显示只有一个确认按钮的弹框
	 * @param context
	 * @param layoutStyle
	 * @param title
	 * @param msg
	 * @param isSytemAlert 是否是系统级别的弹框
	 * @return
	 */
	public static Dialog showSingleBtnDialog(Context context,String title,String msg,boolean isSytemAlert,String btnmsg){
		return new DialogBuilder(context, 0, DialogBuilder.DIALOG_STYLE_BLUE,isSytemAlert)
		.setDialoglocation(DialogBuilder.DIALOG_LOCATION_CENTER)
		.setWindowAnimation(DialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
		.setButtons(btnmsg, null, true, null)
		.setTitle(title)
		.setMessage(msg)
		.create();
	}
	/**
	 * 显示一个单个按钮的对话框并添加点击回调接口
	 * @param context
	 * @param title
	 * @param msg
	 * @param btnmsg
	 * @param listener 按钮点击回调接口
	 * @return
	 */
	public static Dialog showSingleBtnDialog(Context context,String title,String msg,String btnmsg,onDialogbtnClickListener listener){
		return new DialogBuilder(context, 0, DialogBuilder.DIALOG_STYLE_BLUE)
		.setDialoglocation(DialogBuilder.DIALOG_LOCATION_CENTER)
		.setWindowAnimation(DialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
		.setTitle(title)
		.setMessage(msg)
		.setButtons(btnmsg, null, true, listener)
		.create();
	}
	
	/**
	 * 显示一个进度框
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog showProgressDialog(Context context,String msg){
//		View view = LayoutInflater.from(context).inflate(resource, null);
		return new DialogBuilder(context, 0, DialogBuilder.DIALOG_STYLE_LOADING_TRANSPARENT)
		.setView(R.layout.waitting_layout)
		.setDialoglocation(DialogBuilder.DIALOG_LOCATION_CENTER)
		.setWindowAnimation(DialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
		.setMessage(msg)
		.create();
	}
	/**
	 * 显示一个进度框(自定义显示view 参考R.layout.waitting_layout 必选有供设置消息的TextView 并且id=dialog_message)
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog showProgressDialog(Context context,String msg,int resLayout){
		return new DialogBuilder(context, 0, DialogBuilder.DIALOG_STYLE_LOADING_TRANSPARENT)
		.setView(resLayout)
		.setDialoglocation(DialogBuilder.DIALOG_LOCATION_CENTER)
		.setWindowAnimation(DialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
		.setMessage(msg)
		.create();
	}
	
}
