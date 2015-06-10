package com.chongbao.cbplayer.activity;

import com.chongbao.cbplayer.R;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public abstract class BaseResideMenuActivity extends FragmentActivity implements ResideMenu.OnMenuItemClickListener,ResideMenu.OnMenuListener {
	protected ResideMenu resideMenu;
	protected ResideMenuItem itemOnline;
	protected ResideMenuItem itemLocal;
	protected ResideMenuItem itemUser;
	protected ResideMenuItem itemSettings;
	protected FragmentManager fm;
	private String CurFragmentTag;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		setContentView(R.layout.main);
		initResideMenu();
//		initTopNavBar();
	}

	
	private void initResideMenu() {
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_background);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(this);
		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.6f);

		// create menu items;
		itemOnline = new ResideMenuItem(this, R.drawable.icon_home,
				getString(R.string.item_name_online));
		itemLocal = new ResideMenuItem(this, R.drawable.icon_calendar,
				getString(R.string.item_name_local));
		itemUser = new ResideMenuItem(this, R.drawable.icon_profile,
				getString(R.string.item_name_user));
		itemSettings = new ResideMenuItem(this, R.drawable.icon_settings,
				getString(R.string.item_name_settings));

		resideMenu.addMenuItem(itemOnline, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemLocal, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemUser, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);
		resideMenu.setOnMenuItemClickListener(this);
		resideMenu.setMenuListener(this);

		
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

	public ResideMenu getResideMenu() {
		return resideMenu;
	}

	/**
	 * 切换Fragment
	 * @param targetFragment
	 * @param TAG
	 * @param menuOritation
	 */
	public void changeFragment(Fragment targetFragment,String TAG,int menuOritation){
		if(fm==null){
			fm = getSupportFragmentManager();
		}
		resideMenu.clearIgnoredViewList();
		FragmentTransaction transaction  = obtainFragmentTransaction(menuOritation);
		//先隐藏掉当前fragment
		if(CurFragmentTag!=null&&CurFragmentTag!=TAG){
			Fragment currentFragment = fm.findFragmentByTag(CurFragmentTag);
			currentFragment.onPause();
			transaction.hide(currentFragment);
		}
		Fragment fg = fm.findFragmentByTag(TAG);
		if(fg!=null){
			if(fg.isAdded()&&!fg.isVisible()){
				transaction.show(fg);
				fg.onResume();
			}
		}else{
			transaction.add(R.id.main_fragment,targetFragment, TAG);
		}
		transaction.commit();
		CurFragmentTag = TAG;
	}
	
	/**
	 * 给Fragment 添加动画
	 * @param orderStep
	 * @return
	 */
	private FragmentTransaction obtainFragmentTransaction(int menuOritation) {
		FragmentTransaction ft = fm.beginTransaction();
		// 设置切换动画
		if (menuOritation == ResideMenu.DIRECTION_RIGHT) { // 右边菜单
			ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
		} else {// 左边菜单
			ft.setCustomAnimations(R.anim.slide_right_in,
					R.anim.slide_right_out);
		}
		return ft;
	}
	
}
