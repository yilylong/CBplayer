package com.chongbao.cbplayer.fragment;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chongbao.cbplayer.R;
import com.chongbao.cbplayer.bean.MediaBean;


public class LocalFragment extends Fragment {
	public static final String TAG = "LocalFragment";
	private static LocalFragment instance = new LocalFragment();
    private ListView mListView;
    private ArrayList<MediaBean> mediaList;
    private LocalFragment(){
    }
    public static LocalFragment getInstance(){
    	return instance;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fmlayout_local, container, false);
    	mListView = (ListView) view.findViewById(R.id.local_media_listview);
    	mediaList = new ArrayList<MediaBean>();
    	mListView.setAdapter(new LocalMediaAdapter());
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	initData();
    	super.onActivityCreated(savedInstanceState);
    }
    
    private void initData() {
		new MediaScanerTask().execute();
	}

    /**
     * 媒体文件扫描TASK
     * @author huang-hua
     *
     */
    private class MediaScanerTask extends AsyncTask<Void, Void, Boolean>{
    	@Override
    	protected void onPreExecute() {
    		
    	}
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
    	
    }
    
	private class LocalMediaAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
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
			// TODO Auto-generated method stub
			return null;
		}
    	
    }
    private class ViewHolder{
    	ImageView icon;
    	TextView info;
    }
    
}
