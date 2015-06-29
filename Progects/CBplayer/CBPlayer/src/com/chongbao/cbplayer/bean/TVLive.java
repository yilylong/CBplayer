package com.chongbao.cbplayer.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class TVLive implements Serializable{
	public int tvType;
	public ArrayList<MediaBean> chanleList = new ArrayList<MediaBean>();
}
