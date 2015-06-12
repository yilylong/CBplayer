package com.chongbao.cbplayer.bean;

import java.io.Serializable;

public class MediaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public int type;//1 ，视频0 ，音频
	public long duration;// 时常
	public String thumb;// 缩略图路径
	public String url;// 路径
}
