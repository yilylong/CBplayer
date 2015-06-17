package com.chongbao.cbplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chongbao.cbplayer.R;


public class TVFragment extends BaseFragment {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TAG = "TVFragment";
    
	   
    public static TVFragment getInstance(){
    	return new TVFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fmlayout_tv, container, false);
    }

}
