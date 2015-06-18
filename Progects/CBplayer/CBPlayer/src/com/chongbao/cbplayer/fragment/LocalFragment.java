package com.chongbao.cbplayer.fragment;

import java.io.Serializable;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.chongbao.cbplayer.constans.Constans;
import com.chongbao.cbplayer.utils.DialogUtils;


public class LocalFragment extends BaseFragment implements OnItemClickListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TAG = "LocalFragment";
    private ListView mListView;
    private ArrayList<MediaBean> mediaList;
    private LocalMediaAdapter mediaAdapter;
    private int count;
    public static LocalFragment getInstance(){
    	return new LocalFragment();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	Log.i("fragment", "onCreateView");
    	View view = inflater.inflate(R.layout.fmlayout_local, container, false);
    	mListView = (ListView) view.findViewById(R.id.local_media_listview);
    	mediaList = new ArrayList<MediaBean>();
    	mListView.setAdapter(mediaAdapter=new LocalMediaAdapter());
    	mListView.setOnItemClickListener(this);
        return view;
    }
    @Override
    public void onResume() {
    	Log.i("fragment", "onResume");
    	if(count>=1){
    		initData();
    	}
    	count++;
    	super.onResume();
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

    	initData();
    	super.onActivityCreated(savedInstanceState);
    }
    
    private void initData() {
    	showDialog(R.string.msg_dialog_loading_local);
		new MediaScanerTask().execute();
	}

    /**
     * 媒体文件扫描TASK
     * @author zhl
     *
     */
    private class MediaScanerTask extends AsyncTask<Void, Void, ArrayList<MediaBean>>{
    	@Override
    	protected void onPreExecute() {
//    		showDialog();
    	}
		@Override
		protected ArrayList<MediaBean> doInBackground(Void... params) {
			return getMediaData();
		}
		
		@Override
		protected void onPostExecute(ArrayList<MediaBean> result) {
			if(result!=null){
				mediaList.clear();
				mediaList.addAll(result);
			}
			dismissDialog();
			mediaAdapter.notifyDataSetChanged();
		}
    	
    }
    
    private ArrayList<MediaBean> getMediaData() {
    	ArrayList<MediaBean> temp = null;
    	try {
    		temp = new ArrayList<MediaBean>(); 
    		ContentResolver ctResolver = getActivity().getContentResolver();
    		Cursor cursor = ctResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
    				new String[]{MediaStore.Video.Media.DISPLAY_NAME,MediaStore.Video.Media.DURATION,MediaStore.Video.Media.DATA},
    				null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
    				while(cursor.moveToNext()){
    					MediaBean bean = new MediaBean();
    					bean.title=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
    					bean.duration=cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
    					bean.url=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
    					bean.thumb = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
    					temp.add(bean);
    				}
    				
    		return temp;
    		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
    
    
	private class LocalMediaAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mediaList==null?0:mediaList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if(convertView==null){
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(LocalFragment.this.getActivity()).inflate(R.layout.item_local_medialist, parent, false);
				viewHolder.icon = (ImageView) convertView.findViewById(R.id.item_video_icon);
				viewHolder.info = (TextView) convertView.findViewById(R.id.item_video_info);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			MediaBean bean = mediaList.get(position);
			viewHolder.info.setText(bean.title);
			return convertView;
		}
    	
    }
    private class ViewHolder{
    	ImageView icon;
    	TextView info;
    }
    
    
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i = new Intent(this.getActivity(),VideoViewActivity.class);
		i.putExtra(Constans.PARAM_VIDEO, mediaList.get(position));
		startActivity(i);
	}
	
}
