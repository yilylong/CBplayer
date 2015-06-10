package com.chongbao.cbplayer.activity;

import io.vov.vitamio.widget.VideoView;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chongbao.cbplayer.R;
import com.chongbao.cbplayer.utils.MeasureUtil;
import com.chongbao.cbplayer.view.CBProgressBar;

public class VideoViewActivity extends Activity implements OnClickListener{
	public static final String TAG ="VideoViewActivity";
	public static final int MSG_DISIMISS = 0;
	public static final int MSG_SHOW = 1;
	public static final long DELAY_MILLIS = 3000;
	/**视频信息**/
	private TextView videoInfo;
	/**播放/暂停按钮**/
	private ImageView mPlayBtn;
	/**进度条**/
	private SeekBar mProgressBar;
	/**音量/亮度调整显示布局**/
	private RelativeLayout mControlResultRLayout;
	/**整个控制层布局**/
	private RelativeLayout mControlRLayout;
	
	/**音量/亮度图标**/
	private ImageView mControlResultIcon;
	/**音量/亮度值显示**/
	private CBProgressBar mCBProgressBar;
	/**视频播放控件**/
	private VideoView mVideoView;
	private int downX,downY;
	private boolean onTouch = false;
	private int mScreenW,mScreenH;
	private int mTouchSlop;
	/**定时隐藏控制层**/
	private Handler mDismissHander = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_DISIMISS:
				if(!onTouch){
					dismissControlFrame();
				}
				break;
			case MSG_SHOW:
				showControlFrame();
				break;

			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videoview_layout);
		initView();
		initVideo();
		showControlFrame();
		initScreenWidthAndHeight();
		mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
	}


	private void initScreenWidthAndHeight() {
		mScreenW = MeasureUtil.getScreenSize(this)[0];
		mScreenH = MeasureUtil.getScreenSize(this)[1];
	}


	private void initView() {
		mVideoView = (VideoView) findViewById(R.id.video_view);
		videoInfo = (TextView) findViewById(R.id.video_info);
		mPlayBtn = (ImageView) findViewById(R.id.play_btn);
		mProgressBar = (SeekBar) findViewById(R.id.play_progressbar);
		mCBProgressBar = (CBProgressBar) findViewById(R.id.cbprogressbar_control_show);
		mControlResultIcon = (ImageView) findViewById(R.id.icon_control_show);
		mControlResultRLayout = (RelativeLayout) findViewById(R.id.control_showresult_rl);
		mControlRLayout = (RelativeLayout) findViewById(R.id.control_RLayout);
		mPlayBtn.setOnClickListener(this);
	}
	
	private void initVideo() {
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			onTouch = true;
			showControlFrame();
			downX = (int) event.getX();
			downY = (int) event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			onTouch = true;
			// 判断X落点在屏幕的左边还是右边
			if(downX<=mScreenW*1/4){// 在屏幕左边
				Log.i(TAG, "在屏幕左边");
				int dx = (int) (event.getX()-downX);
				int dy = (int) (event.getY()-downY);
				if(Math.abs(dx)<=mTouchSlop&&Math.abs(dy)>mTouchSlop&&dy<0){// 向上滑
					Log.i(TAG, "dx=="+dx+"---dy="+dy+"-----向上滑");
				}else if(Math.abs(dx)<=mTouchSlop&&Math.abs(dy)>mTouchSlop&&dy>0){//向下滑
					Log.i(TAG, "dx=="+dx+"---dy="+dy+"-----向下滑");
				}
			}else if(downX>=mScreenW*3/4){// 在屏幕右边
				Log.i(TAG, "在屏幕右边");
				int dx = (int) (event.getX()-downX);
				int dy = (int) (event.getY()-downY);
				if(dx<=mTouchSlop&&dy<0){// 向上滑
					Log.i(TAG, "dx=="+dx+"---dy="+dy+"-----向上滑");
				}else if(dx<=mTouchSlop&&dy>0){//向下滑
					Log.i(TAG, "dx=="+dx+"---dy="+dy+"-----向下滑");
				}
			}
			
			break;
		case MotionEvent.ACTION_UP:
			onTouch = false;
			setInvisibleDelay();
			downX = 0;
			downY = 0;
			break;

		default:
			break;
		}
		
		return super.onTouchEvent(event);
	}
	
	
	/**
	 * 显示控制层视图
	 */
	private void showControlFrame() {
		mDismissHander.removeMessages(MSG_DISIMISS);
		mControlRLayout.setVisibility(View.VISIBLE);
	}

	private void dismissControlFrame() {
		mControlResultRLayout.setVisibility(View.GONE);
		mControlRLayout.setVisibility(View.INVISIBLE);
	}
	/**
	 *  3秒之后隐藏控制层
	 */
	private void setInvisibleDelay(){
		mDismissHander.removeMessages(MSG_DISIMISS);
		mDismissHander.sendEmptyMessageDelayed(MSG_DISIMISS, DELAY_MILLIS);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play_btn:
			palyVideo();
			break;

		default:
			break;
		}
	}

	/**
	 * 播放视频
	 */
	private void palyVideo() {
		if(mVideoView.isPlaying()){
			mVideoView.pause();
			mPlayBtn.setImageResource(R.drawable.icon_btn_play);
		}else{
			mVideoView.start();
			mPlayBtn.setImageResource(R.drawable.icon_btn_pause);
		}
	}
	


	
}
