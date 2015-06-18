package com.chongbao.cbplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.chongbao.cbplayer.R;
import com.chongbao.cbplayer.constans.Constans;
import com.chongbao.cbplayer.fragment.BaseFragment;
import com.chongbao.cbplayer.fragment.HomeFragment;
import com.chongbao.cbplayer.fragment.LocalFragment;
import com.chongbao.cbplayer.fragment.SettingsFragment;
import com.chongbao.cbplayer.fragment.TVFragment;
import com.chongbao.cbplayer.fragment.UserFragment;
import com.special.ResideMenu.ResideMenu;

public class MainActivity extends BaseResideMenuActivity {

	private MainActivity mContext;
	private BaseFragment currentFragment;
	private TextView mTopBarTitle;

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
			int topBarResid = 0;
			String TAG = null;
			if(currentFragment instanceof HomeFragment){
				TAG = HomeFragment.TAG;
				topBarResid = R.string.item_name_home;
			}else if(currentFragment instanceof TVFragment){
				TAG = TVFragment.TAG;
				topBarResid = R.string.item_name_tv;
			}else if(currentFragment instanceof LocalFragment){
				TAG = LocalFragment.TAG;
				topBarResid = R.string.item_name_local;
			}else if(currentFragment instanceof UserFragment){
				TAG = UserFragment.TAG;
				topBarResid = R.string.item_name_user;
			}else if(currentFragment instanceof SettingsFragment){
				TAG = SettingsFragment.TAG;
				topBarResid = R.string.item_name_settings;
			}
			changeFragment(currentFragment,HomeFragment.TAG,ResideMenu.DIRECTION_LEFT);
			setTopBarTitle(topBarResid);
		}else{
			currentFragment = HomeFragment.getInstance();
			changeFragment(currentFragment,HomeFragment.TAG,ResideMenu.DIRECTION_LEFT);
			setTopBarTitle(R.string.item_name_home);
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
		mTopBarTitle = (TextView) findViewById(R.id.top_bar_title);
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}
	public void jumpToVideoView(View v){
    	Intent i = new Intent(this,VideoViewActivity.class);
    	startActivity(i);
    };

    
    public void setTopBarTitle(int resid){
    	if(mTopBarTitle!=null){
    		mTopBarTitle.setText(resid);
    	}
    }


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
				setTopBarTitle(R.string.item_name_home);
				break;
			case 1:
				currentFragment = TVFragment.getInstance();
				changeFragment(currentFragment,TVFragment.TAG,ResideMenu.DIRECTION_LEFT);
				setTopBarTitle(R.string.item_name_tv);
				break;
			case 2:
				currentFragment = LocalFragment.getInstance();
				changeFragment(currentFragment,LocalFragment.TAG,ResideMenu.DIRECTION_LEFT);
				setTopBarTitle(R.string.item_name_local);
				break;

			default:
				break;
			}
		} else {
			switch (position) {
			case 0:
				currentFragment = UserFragment.getInstance();
				changeFragment(currentFragment,UserFragment.TAG,ResideMenu.DIRECTION_RIGHT);
				setTopBarTitle(R.string.item_name_user);
				break;
			case 1:
				currentFragment = SettingsFragment.getInstance();
				changeFragment(currentFragment,SettingsFragment.TAG,ResideMenu.DIRECTION_RIGHT);
				setTopBarTitle(R.string.item_name_settings);
				break;

			default:
				break;
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(Constans.PARAM_FRAGMENT,(Parcelable) currentFragment);
	}

}
