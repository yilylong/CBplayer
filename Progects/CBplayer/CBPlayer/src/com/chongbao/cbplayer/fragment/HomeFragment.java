package com.chongbao.cbplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chongbao.cbplayer.R;
import com.chongbao.cbplayer.activity.MainActivity;
import com.special.ResideMenu.ResideMenu;


public class HomeFragment extends BaseFragment {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TAG = "HomeFragment";
    private View parentView;
    private ResideMenu resideMenu;
   
    public static HomeFragment getInstance(){
    	return new HomeFragment();
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
