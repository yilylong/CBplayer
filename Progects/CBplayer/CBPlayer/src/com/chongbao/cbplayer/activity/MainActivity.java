package com.chongbao.cbplayer.activity;

import io.vov.vitamio.LibsChecker;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.chongbao.cbplayer.R;
import com.chongbao.cbplayer.constans.Constans;
import com.chongbao.cbplayer.fragment.BaseFragment;
import com.chongbao.cbplayer.fragment.TVFragment;
import com.chongbao.cbplayer.fragment.UserFragment;
import com.chongbao.cbplayer.fragment.HomeFragment;
import com.chongbao.cbplayer.fragment.LocalFragment;
import com.chongbao.cbplayer.fragment.SettingsFragment;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MainActivity extends BaseResideMenuActivity {

	private MainActivity mContext;
	private BaseFragment currentFragment;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("VideoViewActivity", "onCreate");
		setContentView(R.layout.main);
		mContext = this;
		initTopNavBar();
		if(savedInstanceState!=null){
			currentFragment = (BaseFragment) savedInstanceState.getSerializable(Constans.PARAM_FRAGMENT);
			changeFragment(currentFragment,HomeFragment.TAG,ResideMenu.DIRECTION_LEFT);
		}else{
			currentFragment = HomeFragment.getInstance();
			changeFragment(currentFragment,HomeFragment.TAG,ResideMenu.DIRECTION_LEFT);
		}
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
				currentFragment = HomeFragment.getInstance();
				changeFragment(currentFragment,HomeFragment.TAG,ResideMenu.DIRECTION_LEFT);
				break;
			case 1:
				currentFragment = TVFragment.getInstance();
				changeFragment(currentFragment,TVFragment.TAG,ResideMenu.DIRECTION_LEFT);
				break;
			case 2:
				currentFragment = LocalFragment.getInstance();
				changeFragment(currentFragment,LocalFragment.TAG,ResideMenu.DIRECTION_LEFT);
				break;

			default:
				break;
			}
		} else {
			switch (position) {
			case 0:
				currentFragment = UserFragment.getInstance();
				changeFragment(currentFragment,UserFragment.TAG,ResideMenu.DIRECTION_RIGHT);
				break;
			case 1:
				currentFragment = SettingsFragment.getInstance();
				changeFragment(currentFragment,SettingsFragment.TAG,ResideMenu.DIRECTION_RIGHT);
				break;

			default:
				break;
			}
		}
	}
	
	@Override
	protected void onRestart() {
		Log.i("VideoViewActivity", "onRestart");
		super.onRestart();
	}
	@Override
	protected void onResume() {
		Log.i("VideoViewActivity", "onResume");
		super.onResume();
	}
	
	@Override
	protected void onStart() {
		Log.i("VideoViewActivity", "onStart");
		super.onStart();
	}
	@Override
	protected void onStop() {
		Log.i("VideoViewActivity", "onStop");
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		Log.i("VideoViewActivity", "onDestroy");
		super.onDestroy();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(Constans.PARAM_FRAGMENT, currentFragment);
	}
	
}
