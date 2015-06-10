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
 * Time: 下午1:31
 * Mail: specialcyci@gmail.com
 */
public class LocalFragment extends Fragment {
	public static final String TAG = "LocalFragment";
	private static LocalFragment instance = new LocalFragment();
    
    private LocalFragment(){
    }
    public static LocalFragment getInstance(){
    	return instance;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fmlayout_local, container, false);
    }

}
