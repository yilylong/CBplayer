package com.chongbao.cbplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chongbao.cbplayer.R;


public class SettingsFragment extends BaseFragment  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TAG = "SettingsFragment";
	   
    public static SettingsFragment getInstance(){
    	return  new SettingsFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fmlayout_settings, container, false);
    }

}
