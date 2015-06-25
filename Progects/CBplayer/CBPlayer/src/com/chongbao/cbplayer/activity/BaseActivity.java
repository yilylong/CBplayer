package com.chongbao.cbplayer.activity;

import android.app.Activity;

import com.chongbao.cbplayer.utils.DialogBuilder.onDialogbtnClickListener;
import com.chongbao.cbplayer.utils.DialogUtils;

public class BaseActivity extends Activity {
	
	protected void showTipDialog(String title,String msg,String btnMsg,onDialogbtnClickListener listener){
		DialogUtils.showSingleBtnDialog(this, title, msg, btnMsg, listener).show();
	}
	
}
