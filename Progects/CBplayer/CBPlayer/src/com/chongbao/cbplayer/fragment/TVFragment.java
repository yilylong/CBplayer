package com.chongbao.cbplayer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chongbao.cbplayer.R;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午3:28
 * Mail: specialcyci@gmail.com
 */
public class TVFragment extends Fragment {
	public static final String TAG = "TVFragment";
    
    private TVFragment(){
    }
    public static TVFragment getInstance(){
    	return new TVFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fmlayout_tv, container, false);
    }

}
