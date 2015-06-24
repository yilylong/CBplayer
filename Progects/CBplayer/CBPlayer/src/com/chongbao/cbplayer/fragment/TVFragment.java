package com.chongbao.cbplayer.fragment;

import java.util.ArrayList;

import org.twentyfirstsq.sdk.network.WebUtil;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chongbao.cbplayer.R;
import com.chongbao.cbplayer.activity.MainActivity;
import com.chongbao.cbplayer.activity.VideoViewActivity;
import com.chongbao.cbplayer.adapter.IndicatorFragmentAdapter;
import com.chongbao.cbplayer.bean.MediaBean;
import com.chongbao.cbplayer.constans.Constans;
import com.special.ResideMenu.ResideMenu;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.yixia.zi.utils.ToastHelper;

public class TVFragment extends BaseFragment {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TAG = "TVFragment";
	private ViewPager mPager;
	private TitlePageIndicator indicator;
	private IndicatorFragmentAdapter mAdapter;
	private ResideMenu resideMenu;

	public static TVFragment getInstance() {
		return new TVFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fmlayout_tv, container, false);
		mPager = (ViewPager) view.findViewById(R.id.pager);
		indicator = (TitlePageIndicator) view.findViewById(R.id.indicator);
		mPager.setAdapter(mAdapter = new IndicatorFragmentAdapter(getActivity()
				.getSupportFragmentManager(), getActivity()));
		indicator.setViewPager(mPager);
		
		return view;
	}
	@Override
	public void onResume() {
		MainActivity parentActivity = (MainActivity) getActivity();
		resideMenu = parentActivity.getResideMenu();
		resideMenu.addIgnoredView(mPager);
		super.onResume();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

}
