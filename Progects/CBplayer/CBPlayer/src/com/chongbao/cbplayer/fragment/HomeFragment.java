package com.chongbao.cbplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chongbao.cbplayer.R;
import com.chongbao.cbplayer.activity.MainActivity;
import com.chongbao.cbplayer.activity.VideoViewActivity;
import com.special.ResideMenu.ResideMenu;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午1:33
 * Mail: specialcyci@gmail.com
 */
public class HomeFragment extends Fragment {
	public static final String TAG = "HomeFragment";
    private View parentView;
    private ResideMenu resideMenu;
    private static HomeFragment instance = new HomeFragment();
    
    private HomeFragment(){
    }
    public static HomeFragment getInstance(){
    	return instance;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fmlayout_home, container, false);
        setUpViews();
        return parentView;
    }

    
    
    private void setUpViews() {
        MainActivity parentActivity = (MainActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        parentView.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

        // add gesture operation's ignored views
        FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
        resideMenu.addIgnoredView(ignored_view);
    }

}
