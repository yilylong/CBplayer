package com.chongbao.cbplayer.activity;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnErrorListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.chongbao.cbplayer.R;
import com.chongbao.cbplayer.bean.MediaBean;
import com.chongbao.cbplayer.bean.TVLive;
import com.chongbao.cbplayer.constans.Constans;
import com.chongbao.cbplayer.utils.BrightnessUtil;
import com.chongbao.cbplayer.utils.DialogBuilder;
import com.chongbao.cbplayer.utils.MeasureUtil;
import com.chongbao.cbplayer.utils.SharedPrencesUtils;
import com.chongbao.cbplayer.view.CBProgressBar;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

public class VideoViewActivity extends BaseActivity implements OnClickListener{
	public static final String TAG ="VideoViewActivity";
	public static final int MSG_DISIMISS = 0;
	public static final int MSG_SHOW = 1;
	public static final int MSG_PROGRESS_UPDATE = 2;
	public static final long DELAY_MILLIS = 3000;
	public static final long PROGRESS_UPDATE_MILLIS = 490;
	/**视频信息**/
	private TextView videoInfo;
	private TextView timeProgress;
	private TextView timeTotal;
	private TextView tvChange;
	/**播放/暂停按钮**/
	private ImageView mPlayBtn;
	/**进度条**/
	private SeekBar mProgressBar;
	/**音量/亮度调整显示布局**/
	private RelativeLayout mControlResultRLayout;
	/**整个控制层布局**/
	private RelativeLayout mControlRLayout;
	
	private LinearLayout loadingTip;
	private FrameLayout fraLayout;
	private ListView chanleListView;
	
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
	private AudioManager mAudioManager;
	private MediaBean video;
	private boolean isPlayFinished;
	private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	private TVLive mTVlive;
	private ChanleChangeAdapter mChanleChangeAdapter;
	/**定时隐藏控制层**/
	private Handler mDismissHander = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_DISIMISS:
				if(!onTouch&&fraLayout.getVisibility()!=View.VISIBLE){
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
	
	private Handler mProgressHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_PROGRESS_UPDATE:
				if(mVideoView!=null&&mVideoView.isPlaying()&&!video.isLive){
					setSeekBar();
					mProgressHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE,PROGRESS_UPDATE_MILLIS);
				}else if(mVideoView!=null&&!mVideoView.isPlaying()){
					setSeekBar();
				}
				break;

			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		setContentView(R.layout.videoview_layout);
		initView();
		showControlFrame();
		initScreenWidthAndHeight();
		mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
		initData();
		initVideo();
		initChanleList();
	}


	

	private void initData() {
		mTVlive = (TVLive) getIntent().getSerializableExtra(Constans.PARAM_TVLIVE);
		video = (MediaBean) getIntent().getSerializableExtra(Constans.PARAM_VIDEO);
	}




	private void initScreenWidthAndHeight() {
		mScreenW = MeasureUtil.getScreenSize(this)[0];
		mScreenH = MeasureUtil.getScreenSize(this)[1];
	}


	private void initView() {
		mVideoView = (VideoView) findViewById(R.id.video_view);
		videoInfo = (TextView) findViewById(R.id.video_info);
		timeProgress = (TextView) findViewById(R.id.time_progress);
		timeTotal = (TextView) findViewById(R.id.time_total);
		mPlayBtn = (ImageView) findViewById(R.id.play_btn);
		mProgressBar = (SeekBar) findViewById(R.id.play_progressbar);
		mCBProgressBar = (CBProgressBar) findViewById(R.id.cbprogressbar_control_show);
		mControlResultIcon = (ImageView) findViewById(R.id.icon_control_show);
		mControlResultRLayout = (RelativeLayout) findViewById(R.id.control_showresult_rl);
		mControlRLayout = (RelativeLayout) findViewById(R.id.control_RLayout);
		loadingTip = (LinearLayout) findViewById(R.id.loading_tip);
		fraLayout = (FrameLayout) findViewById(R.id.fraLayout_chanlelist);
		chanleListView = (ListView) findViewById(R.id.play_frame_chanle_listview);
		tvChange = (TextView) findViewById(R.id.tv_change_chanle);
		tvChange.setOnClickListener(this);
		mPlayBtn.setOnClickListener(this);
		mProgressBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				seek(seekBar.getProgress());
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if(fromUser&&!video.isLive){
					seek(progress);
				}
			}
		});
		
		// 设置屏幕亮度  刚开始进来 默认亮度很低  可以将设置的值保存起来
		setBrightness();
	}
	
	private void setBrightness() {
		float curBrightness = SharedPrencesUtils.getInstance(this).getFloat(Constans.SP_KEY_BRIGHTNESS);
		if(curBrightness!=-1){
			BrightnessUtil.setBrightness(this, curBrightness);
		}
	}


	private void initVideo() {
		
		if(video!=null){
			showLoadingTip();
			videoInfo.setText(video.title);
			if(video.isStream){
				mVideoView.setVideoURI(Uri.parse(video.url));
				mVideoView.setBufferSize(1024*50);
			}else{
				mVideoView.setVideoPath(video.url);
			}
			if(video.isLive){
				disableProgressBar();
			}
			mVideoView.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					dismissLoadingTip();
					setSeekBar();
					setInvisibleDelay();
					mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, mVideoView.getVideoAspectRatio());
					mp.setPlaybackSpeed(1.0f);
					mPlayBtn.setImageResource(R.drawable.icon_btn_pause);
					// 开始更新进度
					startUpdateProgress();

				}

			});
			mVideoView.setOnInfoListener(new OnInfoListener() {
				
				@Override
				public boolean onInfo(MediaPlayer mp, int what, int extra) {
					switch (what) {
					case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
						disableProgressBar();
						break;

					default:
						break;
					}
					return false;
				}

			});
			mVideoView.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.stop();
					mPlayBtn.setImageResource(R.drawable.icon_btn_play);
					mProgressHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);
					isPlayFinished = true;
					showControlFrame();
				}
			});
			mVideoView.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					dismissLoadingTip();
					// TODO Auto-generated method stub
					if(what == MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK){
						showTipDialog(getString(R.string.msg_dialog_tip_title), getString(R.string.msg_dialog_play_valid_stream), getString(R.string.msg_dialog_btn_ok), null);
					}else{
						showTipDialog(getString(R.string.msg_dialog_tip_title), getString(R.string.msg_dialog_play_error), getString(R.string.msg_dialog_btn_ok), new DialogBuilder.onDialogbtnClickListener() {
							
							@Override
							public void onDialogbtnClick(Context context, Dialog dialog, int dialogId,
									int whichBtn) {
								VideoViewActivity.this.finish();
							}
						});
					}
					// return true 截断后面的弹框
					return true;
				}
			});
			
		}
	}
	private void initChanleList() {
		if(video!=null&&video.isLive){
			tvChange.setVisibility(View.VISIBLE);
			chanleListView.setAdapter(mChanleChangeAdapter = new ChanleChangeAdapter());
			chanleListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					changeChanle(position);
				}

			});
			
		}else{
			tvChange.setVisibility(View.GONE);
			fraLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * 切换频道
	 * @param position
	 */
	private void changeChanle(int position) {
		mVideoView.stopPlayback();
//		releaseVideoView();
//		mPlayBtn.setImageResource(R.drawable.icon_btn_play);
//		mVideoView = (VideoView) findViewById(R.id.video_view);
//		video = mTVlive.chanleList.get(position);
//		initVideo();
		
//		videoInfo.setText(video.title);
		mVideoView.invalidate();
		mVideoView.setVideoPath("/storage/sdcard1/DCIM/Camera/VID_20150617_170438.mp4");
//		mVideoView.setBufferSize(1024*50);
//		showLoadingTip();
	}


	private void disableProgressBar() {
		mProgressBar.setEnabled(false);
		mProgressBar.setMax(1);
		mProgressBar.setProgress(1);
	}
	
	private void setSeekBar() {
		if(mProgressBar!=null){
			if(!video.isLive){
				mProgressBar.setEnabled(true);
				mProgressBar.setMax((int)mVideoView.getDuration());
				mProgressBar.setProgress((int)mVideoView.getCurrentPosition());
				timeProgress.setText(getTime(mVideoView.getCurrentPosition()));
				timeTotal.setText(getTime(mVideoView.getDuration()));
			}
			
		}
		
	}

	private void startUpdateProgress(){
		mProgressHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, PROGRESS_UPDATE_MILLIS);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			onTouch = true;
			mProgressBar.requestFocus();
			showControlFrame();
			downX = (int) event.getX();
			downY = (int) event.getY();
			break;
		case MotionEvent.ACTION_POINTER_2_DOWN:
			return true;
		case MotionEvent.ACTION_MOVE:
			onTouch = true;
			int dx = (int) (event.getX()-downX);
			int dy = (int) (event.getY()-downY);
			// 判断X落点在屏幕的左边还是右边
			if(downX<=mScreenW*1/4){// 在屏幕左边
				Log.i(TAG, "在屏幕左边");
				if(Math.abs(dx)<=mTouchSlop&&Math.abs(dy)>mTouchSlop&&dy<0){// 向上滑
					judgeBrightness(dy);
				}else if(Math.abs(dx)<=mTouchSlop&&Math.abs(dy)>mTouchSlop&&dy>0){//向下滑
					judgeBrightness(dy);
				}
			}else if(downX>=mScreenW*3/4){// 在屏幕右边
				Log.i(TAG, "在屏幕右边");
				if(Math.abs(dx)<=mTouchSlop&&Math.abs(dy)>mTouchSlop&&dy<0){// 向上滑
					judgeVolume(dy);
				}else if(Math.abs(dx)<=mTouchSlop&&Math.abs(dy)>mTouchSlop&&dy>0){//向下滑
					judgeVolume(dy);
				}
			}else{//在屏幕中间
				// 判断是横向滑动还是竖向滑动
				if(Math.abs(dy)<=mTouchSlop&&Math.abs(dx)>mTouchSlop&&Math.abs(dx)>50){// 横向滑动
					// TODO 快进/快退
					Log.i(TAG, "dx=="+dx+"---dy="+dy+"-----中间滑动");
					if(video!=null&&!video.isLive){
						seekForward(dx);
					}
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
	 * 快进快退
	 * @param dx
	 */
	private void seekForward(float dx) {
		long duration = mVideoView.getDuration();
		if(dx>0){// 快进
			if(mVideoView.getCurrentPosition()<=duration-50){
				long seek = (long) ((dx/mScreenW/2)*(duration/3));
				long seekTo = mVideoView.getCurrentPosition()+seek;
				if(seekTo>duration){
					seekTo = duration;
				}
				seek(seekTo);
			}
		}else{
			if(mVideoView.getCurrentPosition()>0){
				long seek = (long) ((Math.abs(dx)/mScreenW/2)*(duration/3));
				long seekTo = mVideoView.getCurrentPosition()-seek;
				if(seekTo<=0){
					seekTo = 0;
				}
				seek(seekTo);
			}
		}
	}


	/**
	 * 调整亮度(activity 的亮度值是0-1的浮点数)
	 * @param dy
	 */
	private void judgeBrightness(int dy) {
		float maxBirghtness = 100f;
		// 先关闭系统的自动亮度调节
		BrightnessUtil.setBrightnesMode(this, 0);
		float currentBrightness = BrightnessUtil.getCurrentActivityBrightness(this);
		// 刚开始获取的亮度是负值
		if(currentBrightness<0){
			currentBrightness = 0.05f;
		}
		showBrightnessControl(currentBrightness);
		float percent = Math.abs((float)dy)/mScreenH/5;
		if(dy<0){// 亮度增加
			if(currentBrightness<1){
				currentBrightness+=percent;
				if(currentBrightness>=1){
					currentBrightness=1;
				}
				BrightnessUtil.setBrightness(this, currentBrightness);
				mCBProgressBar.setProgress((int)(maxBirghtness*currentBrightness));
			}
		}else{// 亮度减
			if(currentBrightness>0.05){
				currentBrightness-=percent;
				if(currentBrightness<=0.05){
					currentBrightness=0.05f;
				}
				BrightnessUtil.setBrightness(this, currentBrightness);
				mCBProgressBar.setProgress((int)(maxBirghtness*currentBrightness));
			}
		}
		SharedPrencesUtils.getInstance(this).saveFloat(Constans.SP_KEY_BRIGHTNESS, currentBrightness);
		
	}


	/**
	 * 调整音量
	 * @param dy
	 */
	private void judgeVolume(float dy) {
		if(mAudioManager==null){
			mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		}
		showVolumeControl(mAudioManager);
		int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		int add = (int) ((Math.abs(dy)/mScreenH)*2);
		if(add==0){
			add = 1;
		}
		if(dy<0){// 音量增大
			if(currentVolume<maxVolume){
				currentVolume+=add;
				if(currentVolume>=maxVolume){
					currentVolume = maxVolume;
				}
			}
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
			mCBProgressBar.setProgress(currentVolume);
		}else{// 音量-
			if(currentVolume>0){
				currentVolume-=add;
				if(currentVolume<=0){
					currentVolume = 0;
				}
			}
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
			mCBProgressBar.setProgress(currentVolume);
		}
	}

	/**
	 * 显示音量调节
	 * @param mAudioManager
	 */
	private void showVolumeControl(AudioManager mAudioManager){
		int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		mCBProgressBar.setMax(maxVolume);
		mCBProgressBar.setProgress(currentVolume);
		mControlResultIcon.setImageResource(R.drawable.icon_volume);
		mControlResultRLayout.setVisibility(View.VISIBLE);
		mCBProgressBar.setVisibility(View.VISIBLE);
	}
	/**
	 * 显示亮度调节
	 * @param currentBrightness
	 */
	private void showBrightnessControl(float currentBrightness){
		float maxVolume = 100;
		mCBProgressBar.setMax((int)maxVolume);
		mCBProgressBar.setProgress((int)(maxVolume*currentBrightness));
		mControlResultIcon.setImageResource(R.drawable.icon_brightness);
		mControlResultRLayout.setVisibility(View.VISIBLE);
		mCBProgressBar.setVisibility(View.VISIBLE);
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
		mCBProgressBar.setVisibility(View.INVISIBLE);
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
		case R.id.tv_change_chanle:
			showORdismisstvList();
			break;
		default:
			break;
		}
	}

	private void showORdismisstvList() {
		if(fraLayout.getVisibility()!=View.VISIBLE){
			fraLayout.setVisibility(View.VISIBLE);
			ObjectAnimator animtor = ObjectAnimator.ofFloat(fraLayout, "translationX", 200,0);
			animtor.setDuration(300);
			animtor.start();
		}else{
			ObjectAnimator animtor = ObjectAnimator.ofFloat(fraLayout, "translationX", 0,300);
			animtor.setDuration(300);
			animtor.addListener(new AnimatorListener() {
				@Override
				public void onAnimationStart(Animator arg0) {
				}
				
				@Override
				public void onAnimationRepeat(Animator arg0) {
				}
				
				@Override
				public void onAnimationEnd(Animator arg0) {
					fraLayout.setVisibility(View.GONE);
				}
				
				@Override
				public void onAnimationCancel(Animator arg0) {
				}
			});
			animtor.start();
			setInvisibleDelay();
		}
	}




	/**
	 * 播放视频
	 */
	private void palyVideo() {

		if(mVideoView.isPlaying()){
			mProgressHandler.removeMessages(MSG_PROGRESS_UPDATE);
			mVideoView.pause();
			mPlayBtn.setImageResource(R.drawable.icon_btn_play);
		}else{
			if(isPlayFinished){
				isPlayFinished = false;
				mVideoView.seekTo(0);
			}
			mVideoView.start();
			if(!video.isLive){
				startUpdateProgress();
			}
			mPlayBtn.setImageResource(R.drawable.icon_btn_pause);
		}
	}
	
	
	@Override
	protected void onDestroy() {
		releaseVideoView();
		super.onDestroy();
	}


	private void releaseVideoView() {
		if(mVideoView!=null){
			if(mVideoView.isPlaying()){
				mVideoView.stopPlayback();
			}
			mProgressHandler.removeMessages(MSG_PROGRESS_UPDATE);
			mVideoView = null;
		}
	}
	
	private void seek(long seekTo){
		if(mVideoView!=null&&seekTo<mVideoView.getDuration()){
			mVideoView.seekTo(seekTo);
		}
	}

	private String getTime(long millis){
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
		return formatter.format(millis);
	}
	
	private void showLoadingTip(){
		if(loadingTip!=null){
			loadingTip.setVisibility(View.VISIBLE);
		}
	}
	private void dismissLoadingTip(){
		if(loadingTip!=null){
			loadingTip.setVisibility(View.GONE);
		}
	}
	
	private class ChanleChangeAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mTVlive.chanleList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if(convertView==null){
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(VideoViewActivity.this).inflate(R.layout.item_playframe_chanle_list, parent, false);
				viewHolder.chanleTitle = (TextView) convertView.findViewById(R.id.item_playframe_list_chanle_name);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			MediaBean bean = mTVlive.chanleList.get(position);
			viewHolder.chanleTitle.setText(bean.title);
			return convertView;
		}
		
	}
	private class ViewHolder{
		TextView chanleTitle;
	}
	
}
