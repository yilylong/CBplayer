package com.chongbao.cbplayer.bean;

import java.io.Serializable;

public class MediaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String title;
	public int type=1;//1 ，视频0 ，音频
	public long duration;// 时常
	public String thumb;// 缩略图路径
	/**
	 * 路径
	 */
	public String url;
	public String id;
	/**
	 * 本地用 图片资源
	 */
	public int iconResID;
	/**
	 * 是否是直播
	 */
	public boolean isLive;
	/**
	 * 是否是在线视频
	 */
	public boolean isStream;
}
