package com.chongbao.cbplayer.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chongbao.cbplayer.R;
import com.chongbao.cbplayer.constans.Constans;
import com.chongbao.cbplayer.fragment.TVOnlineTypeFragment;

public class IndicatorFragmentAdapter extends FragmentPagerAdapter{
    protected static final Integer[] CONTENT = new Integer[] {
    	R.string.content_type_central,
    	R.string.content_type_internal,
    	R.string.content_type_hk,
    	R.string.content_type_japan,
    	R.string.content_type_europe};
    protected static final Integer[] TYPES = new Integer[]{
    	Constans.TV_TYPE_CENTRAL,
    	Constans.TV_TYPE_INTERNAL,
    	Constans.TV_TYPE_HK,
    	Constans.TV_TYPE_JAPAN,
    	Constans.TV_TYPE_EUROPE
    };
//    protected static final int[] ICONS = new int[] {
//            R.drawable.perm_group_calendar,
//            R.drawable.perm_group_camera,
//            R.drawable.perm_group_device_alarms,
//            R.drawable.perm_group_location
//    };
    private Context mContext;

    private int mCount = CONTENT.length;

    public IndicatorFragmentAdapter(FragmentManager fm,Context ctx) {
        super(fm);
        mContext = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        return TVOnlineTypeFragment.getInstance(TYPES[position % TYPES.length]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return mContext.getString(IndicatorFragmentAdapter.CONTENT[position % CONTENT.length]);
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}