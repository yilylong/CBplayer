package com.chongbao.cbplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.chongbao.cbplayer.R;
import com.chongbao.cbplayer.fragment.UserFragment;
import com.chongbao.cbplayer.fragment.HomeFragment;
import com.chongbao.cbplayer.fragment.LocalFragment;
import com.chongbao.cbplayer.fragment.SettingsFragment;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MainActivity extends BaseResideMenuActivity {

	private MainActivity mContext;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = this;
		initTopNavBar();
		if (savedInstanceState == null)
			changeFragment(HomeFragment.getInstance(),HomeFragment.TAG,ResideMenu.DIRECTION_LEFT);
	}

	private void initTopNavBar() {
		
		findViewById(R.id.title_bar_left_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
		findViewById(R.id.title_bar_right_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
					}
				});
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}
	public void jumpToVideoView(View v){
    	Intent i = new Intent(this,VideoViewActivity.class);
    	startActivity(i);
    };

//	private void changeFragment(Fragment targetFragment) {
//		resideMenu.clearIgnoredViewList();
//		getSupportFragmentManager().beginTransaction()
//				.replace(R.id.main_fragment, targetFragment, "fragment")
//				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//				.commit();
//	}


	@Override
	public void openMenu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeMenu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMenuItemClicked(View menuItem, int position, int direction) {
		if (direction == ResideMenu.DIRECTION_LEFT) {
			switch (position) {
			case 0:
				changeFragment(HomeFragment.getInstance(),HomeFragment.TAG,ResideMenu.DIRECTION_LEFT);
				break;
			case 1:
				changeFragment(LocalFragment.getInstance(),LocalFragment.TAG,ResideMenu.DIRECTION_LEFT);
				break;

			default:
				break;
			}
		} else {
			switch (position) {
			case 0:
				changeFragment(UserFragment.getInstance(),UserFragment.TAG,ResideMenu.DIRECTION_RIGHT);
				break;
			case 1:
				changeFragment(SettingsFragment.getInstance(),SettingsFragment.TAG,ResideMenu.DIRECTION_RIGHT);
				break;

			default:
				break;
			}
		}
	}
}
