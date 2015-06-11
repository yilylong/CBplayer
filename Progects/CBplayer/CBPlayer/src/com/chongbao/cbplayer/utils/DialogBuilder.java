package com.chongbao.cbplayer.utils;


import com.chongbao.cbplayer.R;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;




/**
 * 
 * 类名称：对话框工具类
 * 类描述：创建对话框的工具类，可以设置不同样式，和动画风格
 * 
 * 创建人： ZhaoHaiLong
 * 创建时间：2012-1-20 上午10:37:55
 * 
 *
 */
public class DialogBuilder {
	/**
	 * 蓝色风
	 */
	public static final int DIALOG_STYLE_BLUE=com.chongbao.cbplayer.R.layout.dialog_blue;
	/**
	 * 普通风格
	 */
	public static final int DIALOG_STYLE_NOR=com.chongbao.cbplayer.R.layout.dialog;
	/**
	 * 进度框风格
	 */
	public static final int DIALOG_STYLE_LOADING_TRANSPARENT=R.layout.loading_transparent;
	/**
	 * 缩放动画
	 */
	public static final int DIALOG_ANIM_NORMAL=R.style.DialogAnimation;
	/**
	 * 从下往上滑动动画
	 */
	public static final int DIALOG_ANIM_SLID_BOTTOM=R.style.DialogAnimationSlidBottom;
	/**
	 * 从上往下滑动动画
	 */
	public static final int DIALOG_ANIM_SLID_TOP=R.style.DialogAnimationSlidTop;
	/**
	 * 从右往左滑动动画
	 */
	public static final int DIALOG_ANIM_SLID_RIGHT=R.style.DialogAnimationSlidRight;
	/**
	 * 对话框宽度所占屏幕宽度的比例
	 */
	public static final float WIDTHFACTOR = 0.45f;
	/**
	 * 对话框透明比例
	 */
	public static final float ALPHAFACTOR = 1.0f;
	/**
	 * 对话框处于屏幕顶部位置
	 */
	public static final int DIALOG_LOCATION_TOP=12;
	/**
	 * 对话框处于屏幕中间位置
	 */
	public static final int DIALOG_LOCATION_CENTER=10;
	/**
	 * 对话框处于屏幕底部位置
	 */
	public static final int DIALOG_LOCATION_BOTTOM=11;
	/**
	 * 消息位于对话框的位置 居左
	 */
	public static final int MSG_LAYOUT_LEFT=1;
	/**
	 * 消息位于对话框的位置 居中
	 */
	public static final int MSG_LAYOUT_CENTER=0;
	/**
	 * 上下文
	 */
	private Context context;
	/**
	 * Dialog对象
	 */
	private Dialog dialog;
	/**
	 * 左边（确定）按钮
	 */
	private TextView leftbtn;
	/**
	 * 对话框ID
	 */
	private int dialogId;
	/**
	 * 消息框布局
	 */
	ViewGroup msglayout;

	
	/**
	 * 构造器一 创建一个普通风格dialog
	 * @param context
	 */
	public DialogBuilder(Context context) {
		this(context,0,DIALOG_STYLE_NOR);
	}
	
	// 重构构造函数 方便用户使用内部类监听器时使用
	/**
	 * 构造器二
	 * @param context  上下文
	 * @param dialogId 对话框ID
	 * @param layoutStyle 对话框风格
	 */
	public DialogBuilder(Context context, int dialogId,int layoutStyle) {
		this(context,dialogId,layoutStyle,false);
	}
	
	/**
	 * 构造器三
	 * @param context
	 * @param dialogId 对话框ID
	 * @param layoutStyle 布局样式
	 * @param isSystemAlert 是否是系统弹框（service等地方用到系统级别不依赖activity）
	 */
	public DialogBuilder(Context context, int dialogId,int layoutStyle,boolean isSystemAlert) {
		this(context, dialogId, layoutStyle, isSystemAlert, WIDTHFACTOR, ALPHAFACTOR);
	}

	/**
	 * 构造器四
	 * @param context                上下文
	 * @param dialogId               对话框ID
	 * @param layoutStyle            对话框布局样式
	 * @param widthcoefficient       对话框宽度时占屏幕宽度的比重（0-1）
	 */
	public DialogBuilder(Context context, int dialogId,int layoutStyle, float widthcoefficient) {
		this(context, dialogId, layoutStyle, false, widthcoefficient, ALPHAFACTOR);
	} 

	/**
	 * 对话框生成器五
	 * @param context            上下文
	 * @param dialogId           对话框ID
	 * @param layoutStyle       样式
	 * @param widthcoefficient   对话框宽度所占屏幕宽度的比重（0-1）
	 * @param alpha              对话框透明度 
	 */
	public DialogBuilder(Context context, int dialogId, int layoutStyle,float widthcoefficient,float alpha) {
		this(context, dialogId, layoutStyle, false, widthcoefficient, alpha);
	}

	/**
	 * 构造器六
	 * @param context
	 * @param dialogId 对话框ID
	 * @param layoutStyle 布局样式
	 * @param isSystemAlert 是否是系统弹框（service等地方用到系统级别不依赖activity）
	 * @param widthcoefficient 对话框宽度所占屏幕宽度的比重（0-1）
	 * @param alpha 对话框透明度 
	 */
	public DialogBuilder(Context context, int dialogId,int layoutStyle,boolean isSystemAlert,float widthcoefficient,float alpha) {
		// theme 要传入一个样式去掉系统对话框的标题
		Dialog dialog = new Dialog(context, R.style.Dialog);
		// 设置对话框风格
		dialog.setContentView(layoutStyle);
		Window window = dialog.getWindow();
		// 是否系统级弹框
		if(isSystemAlert){
			window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		}
		// 获取屏幕宽度
		DisplayMetrics metrics = new DisplayMetrics();
		window.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int screenwidth = metrics.widthPixels;
		int width = 0;
		if(widthcoefficient>0){
			width = (int) (screenwidth * widthcoefficient); 
		}else{
			width = (int) (screenwidth * WIDTHFACTOR); 
		}
		// 设置对话框宽度
		window.getAttributes().width = width;
		
		// 设置透明
		WindowManager.LayoutParams lp = window.getAttributes();
		if(alpha>0){
			lp.alpha = 1.0f;
		}else{
			lp.alpha = ALPHAFACTOR;
		}
		window.setAttributes(lp);
		// 设置动画样式
		window.setWindowAnimations(DIALOG_ANIM_NORMAL);
		this.context = context;
		this.dialog = dialog;
		this.dialogId = dialogId;
	}

	/**
	 * 创建对话框
	 * 
	 * @return 
	 */
	public Dialog create() {
		// 判断是否需要创建按钮
		if (leftbtn == null) {
			ViewGroup rootlayout = getView(R.id.dialog_root);
			ViewGroup btnlayout = getView(R.id.dialog_btnlayout);
			if(btnlayout!=null){
				rootlayout.removeView(btnlayout);
			}
		}
		return dialog;
	}

	/**
	 * 设置对话框标题
	 * 
	 * @param title
	 * @return
	 */
	public DialogBuilder setTitle(Object title) {

		TextView dialogtitle = getView(R.id.dialog_title);
		dialogtitle.setText(parseParam(title));
		return this;
	}

	/**
	 * 设置图标
	 * 
	 * @return
	 */

	/*public DialogBuilder setIcon(int resid) {
		ImageView icon = getView(R.id.dialog_icon);
		icon.setImageResource(resid);
		return this;
	}*/

	/**
	 * 给对话框设置动画
	 * @param resId
	 * @return
	 */
	public DialogBuilder setWindowAnimation(int resId){
		this.dialog.getWindow().setWindowAnimations(resId);
		return this;
	}
	
	/**
	 * 设置对话框的位置
	 * @param location
	 * @return
	 */
	public DialogBuilder setDialoglocation(int location){
		Window window = this.dialog.getWindow();
		switch (location) {
		case DIALOG_LOCATION_CENTER:
			window.setGravity(Gravity.CENTER);
			break;
		case DIALOG_LOCATION_BOTTOM:
			window.setGravity(Gravity.BOTTOM);
			break;
		case DIALOG_LOCATION_TOP:
			window.setGravity(Gravity.TOP);
			break;
		default:
			break;
		}
		return this;
	}
	
	/**
	 * 设置对话框的消息内容
	 * 
	 * @param message
	 * @return
	 */
	public DialogBuilder setMessage(Object message) {
		TextView dialogcontent = getView(R.id.dialog_message);
		dialogcontent.setText(parseParam(message));

		return this;
	}
	/**
	 * 设置消息在对话框中的位置 MSG_LAYOUT_LEFT 居左 MSG_LAYOUT_CENTER 居中 默认居中
	 * @param layout
	 * @return 
	 */
	public DialogBuilder setMessageGravity(int layout){
		TextView dialogcontent = getView(R.id.dialog_message);
		if(layout>0){
			if(layout==MSG_LAYOUT_LEFT){
				dialogcontent.setGravity(Gravity.LEFT);
			}else if(layout == MSG_LAYOUT_CENTER ){
				dialogcontent.setGravity(Gravity.CENTER);
			}
		}
		return this;
	}
	
	/**
	 * 设置按钮
	 * @param positivebtn 肯定
	 * @param negivatebtn 否定 (设置为null取消按钮就隐藏)
	 * @param isDissmiss  点击按钮后是否关闭对话框
	 * @param listener    按钮监听器
	 * @return
	 */
	public DialogBuilder setButtons(Object positivebtn, Object negivatebtn,final boolean isDissmiss,
			final onDialogbtnClickListener listener) {
		// 设置积极按钮
		final Button leftbtn = getView(R.id.dialog_posi_btn);
		leftbtn.setText(parseParam(positivebtn));

		// 给按钮绑定监听器
		leftbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isDissmiss){
					dialog.dismiss();
				}
				if (listener != null) {

					listener.onDialogbtnClick(context, dialog, dialogId,
							onDialogbtnClickListener.BUTTON_LEFT);
				}
			}
		});

		// 设置消极按钮
		final Button rightbtn = getView(R.id.dialog_neg_btn);
		if(null!=negivatebtn){
			rightbtn.setText(parseParam(negivatebtn));
			// 给按钮绑定监听器
			rightbtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					if (listener != null) {
						listener.onDialogbtnClick(context, dialog, dialogId,
								onDialogbtnClickListener.BUTTON_RIGHT);
					}
				}
			});
		}else{
			rightbtn.setVisibility(View.GONE);
			LinearLayout.LayoutParams params = (LayoutParams) leftbtn.getLayoutParams();
			params.setMargins((int)context.getResources().getDimension(R.dimen.dialog_btn_single_LeftRightmargin), 0, (int)context.getResources().getDimension(R.dimen.dialog_btn_single_LeftRightmargin), 0);
		}
		this.leftbtn = leftbtn;
		return this;
	}

	/**
	 * 给对话框中间内容设置为一个自定义view
	 * 
	 * @param v
	 * @return
	 */
	public DialogBuilder setView(View v) {
		msglayout = getView(R.id.dialog_msg_layout);
		// 删除原来的textview
		msglayout.removeAllViews();
		// 添加新的view
		msglayout.addView(v);

		return this;
	}

	/**
	 * 根据用户传入的布局文件加载view到对话框
	 * 
	 * @param nameInput
	 * @return
	 */
	public DialogBuilder setView(int nameInput) {
		ViewGroup msglayout = getView(R.id.dialog_msg_layout);
		// 需要传入添加的布局文件的父控件，false表示不需要inflate方法添加到父控件下，让我们自己添加
		return setView(LayoutInflater.from(context).inflate(nameInput,
				msglayout, false));
	}

	/**
	 * 给对话框设置一个数组
	 * 
	 * @param items
	 * @return
	 */
	public DialogBuilder setItems(String[] items,
			final onDialogItemClickListener listener) {
		// 给对话框设置listview
		setView(R.layout.optiondialog_list_view);
		// 给listview 设置数据
		ListView listview = getView(android.R.id.list);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				R.layout.item_option_text, items);
		listview.setAdapter(adapter);

		// 给listview里面的选项设置监听器
		listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 首先关闭对话框
				dialog.dismiss();
				if (listener != null) {

					listener.onDialogItemClick(context, DialogBuilder.this,
							dialog, position);

				}

			}
		});

		return this;
	}

	/**
	 * 重载方法传入一个资源文件
	 * @param resId
	 * @param listener
	 * @return
	 */

	public DialogBuilder setItems(int resId, onDialogItemClickListener listener) {
		String[] items = context.getResources().getStringArray(resId);

		return setItems(items, listener);
	}

	/**
	 * 根据子控件ID得到子控件
	 * 
	 * @param id
	 *            子控件ID
	 * @return 返回子控件
	 */
	public <T extends View> T getView(int id) {

		return (T) dialog.findViewById(id);
	}

	/**
	 * 解析用户传入的数据，字符串或者资源ID
	 * 
	 * @param title
	 * @return
	 */
	private String parseParam(Object param) {
		// 如果是资源id 就通过上下文 获取资源
		if (param instanceof Integer) {
			return context.getString((Integer) param);
		} else if (param instanceof String) {
			return param.toString();
		}
		return null;

	}

	// 内部接口监听器
	
	/**
	 * 自定义监听器监听对话框按钮点击
	 * 
	 * @author zhl
	 * 
	 */
	public interface onDialogbtnClickListener {
		/**
		 *  （区分点击的事左边按钮还是右边按钮）--左边
		 */
		public static final int BUTTON_LEFT = 0;
		/**
		 *  （区分点击的事左边按钮还是右边按钮）--右边
		 */
		public static final int BUTTON_RIGHT = 1;

		/**
		 * 
		 * @param context
		 *            上下文
		 * @param dialog
		 *            点击的哪个对话框
		 * @param dialogId
		 *            使用内部类监听器时标示哪个窗口
		 * @param whichBtn
		 *            点击的哪个按钮 
		 */
		void onDialogbtnClick(Context context, Dialog dialog, int dialogId,
				int whichBtn);

	}

	/**
	 * 自定义监听器监听对话框中的选项点击
	 * 
	 * @author yan
	 * 
	 */
	public interface onDialogItemClickListener {

		/**
		 * 
		 * @param context
		 * @param dialogbuilder 对象
		 * @param dialog
		 * @param position  选项角标
		 */
		public void onDialogItemClick(Context context,
				DialogBuilder dialogbuilder, Dialog dialog, int position);

	}

}
