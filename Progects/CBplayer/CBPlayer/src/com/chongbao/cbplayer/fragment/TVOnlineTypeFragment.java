package com.chongbao.cbplayer.fragment;

import java.util.ArrayList;

import org.twentyfirstsq.sdk.network.WebUtil;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
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
import com.chongbao.cbplayer.activity.VideoViewActivity;
import com.chongbao.cbplayer.bean.MediaBean;
import com.chongbao.cbplayer.bean.TVLive;
import com.chongbao.cbplayer.constans.Constans;
import com.yixia.zi.utils.ToastHelper;


public class TVOnlineTypeFragment extends BaseFragment implements OnItemClickListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TAG = "TVOnlineTypeFragment";
    private ListView mListView;
    private ArrayList<MediaBean> TVList;
    private TVListAdapter listAdapter;
    private int TVtype;
    private TVLive mTVlive;
    
    public static TVOnlineTypeFragment getInstance(int tvType){
    	TVOnlineTypeFragment fg = new TVOnlineTypeFragment();
    	fg.TVtype = tvType;
    	return fg;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.tv_online_type, container, false);
    	mListView = (ListView) view.findViewById(R.id.tv_listview);
    	TVList = new ArrayList<MediaBean>();
    	mListView.setAdapter(listAdapter = new TVListAdapter());
    	mListView.setOnItemClickListener(this);
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	initTVList();
    }
    /**
     * 加载电视列表
     */
	private void initTVList() {
		// TODO 后期可从服务器加载，现在数据量小没有采用异步
		mTVlive = new TVLive();
		showDialog(R.string.msg_dialog_loading_TV);
		String[] tvtitles = null;
		String[] urls = null;
		TypedArray icons =null; 
		switch (TVtype) {
		case Constans.TV_TYPE_CENTRAL:
			tvtitles = getResources().getStringArray(R.array.array_tv_list_central);
			urls = getResources().getStringArray(R.array.array_tv_url_central);
			icons = getResources().obtainTypedArray(R.array.array_tv_icon_central);
			mTVlive.tvType=Constans.TV_TYPE_CENTRAL;
			break;
		case Constans.TV_TYPE_INTERNAL:
			tvtitles = getResources().getStringArray(R.array.array_tv_list_internal);
			urls = getResources().getStringArray(R.array.array_tv_url_internal);
			icons = getResources().obtainTypedArray(R.array.array_tv_icon_internal);
			mTVlive.tvType=Constans.TV_TYPE_INTERNAL;
			break;
		case Constans.TV_TYPE_HK:
			tvtitles = getResources().getStringArray(R.array.array_tv_list_hk);
			urls = getResources().getStringArray(R.array.array_tv_url_hk);
			icons = getResources().obtainTypedArray(R.array.array_tv_icon_hk);
			mTVlive.tvType=Constans.TV_TYPE_HK;
			break;
		case Constans.TV_TYPE_JAPAN:
			tvtitles = getResources().getStringArray(R.array.array_tv_list_japan);
			urls = getResources().getStringArray(R.array.array_tv_url_japan);
			icons = getResources().obtainTypedArray(R.array.array_tv_icon_japan);
			mTVlive.tvType=Constans.TV_TYPE_JAPAN;
			break;
		case Constans.TV_TYPE_EUROPE:
			tvtitles = getResources().getStringArray(R.array.array_tv_list_europe);
			urls = getResources().getStringArray(R.array.array_tv_url_europe);
			icons = getResources().obtainTypedArray(R.array.array_tv_icon_europe);
			mTVlive.tvType=Constans.TV_TYPE_EUROPE;
			break;

		default:
			break;
		}
		TVList.clear();
		for(int i = 0;i<tvtitles.length;i++){
			MediaBean bean = new MediaBean();
			bean.title = tvtitles[i];
			bean.url = urls[i];
			bean.isLive = true;
			bean.isStream = true;
			bean.iconResID = icons.getResourceId(i, 0);
			TVList.add(bean);
		}
		mTVlive.chanleList = TVList;
		icons.recycle();
		listAdapter.notifyDataSetChanged();
		dismissDialog();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(WebUtil.isConnected(getActivity())){
			if(WebUtil.isWifi(getActivity())){
				Intent i = new Intent(getActivity(),VideoViewActivity.class);
				i.putExtra(Constans.PARAM_VIDEO, TVList.get(position));
				i.putExtra(Constans.PARAM_TVLIVE, mTVlive);
				startActivity(i);
			}else{
				// 走流量弹框提醒
			}
		}else{
			ToastHelper.showToast(getActivity(), R.string.msg_toast_no_network);
		}
	}
    
	private class TVListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return TVList==null?0:TVList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if(convertView==null){
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(TVOnlineTypeFragment.this.getActivity()).inflate(R.layout.item_tvlist, parent, false);
				viewHolder.icon = (ImageView) convertView.findViewById(R.id.item_tv_icon);
				viewHolder.info = (TextView) convertView.findViewById(R.id.item_tv_info);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			MediaBean bean = TVList.get(position);
			viewHolder.info.setText(bean.title);
			if(bean.iconResID>0){
				viewHolder.icon.setImageResource(bean.iconResID);
			}
			return convertView;
		}
		
	}

	private class ViewHolder {
		ImageView icon;
		TextView info;
	}

}
