package com.chongbao.cbplayer.fragment;

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


public class LocalFragment extends Fragment implements OnItemClickListener{
	public static final String TAG = "LocalFragment";
	private static LocalFragment instance = new LocalFragment();
    private ListView mListView;
    private ArrayList<MediaBean> mediaList;
    private Dialog progressDialog;
    private LocalMediaAdapter mediaAdapter;
    private int count;
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
    	mListView.setAdapter(mediaAdapter=new LocalMediaAdapter());
    	mListView.setOnItemClickListener(this);
        return view;
    }
    @Override
    public void onResume() {
    	if(count>=1){
    		initData();
    	}
    	count++;
    	super.onResume();
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	initData();
    }
    
    private void initData() {
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
    		showDialog();
    	}
		@Override
		protected ArrayList<MediaBean> doInBackground(Void... params) {
			getMediaData();
			return mediaList;
		}
		
		@Override
		protected void onPostExecute(ArrayList<MediaBean> result) {
			dismissDialog();
			mediaAdapter.notifyDataSetChanged();
		}
    	
    }
    
    private void getMediaData() {
    	mediaList.clear();
    	try {
    		ContentResolver ctResolver = getActivity().getContentResolver();
    		Cursor cursor = ctResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
    				new String[]{MediaStore.Video.Media.DISPLAY_NAME,MediaStore.Video.Media.DURATION,MediaStore.Video.Media.DATA},
    				null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
    				while(cursor.moveToNext()){
    					MediaBean bean = new MediaBean();
    					bean.name=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
    					bean.duration=cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
    					bean.url=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
    					bean.type=1;
    					mediaList.add(bean);
    				}
    		
		} catch (Exception e) {
			e.printStackTrace();
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
			viewHolder.info.setText(bean.name);
			return convertView;
		}
    	
    }
    private class ViewHolder{
    	ImageView icon;
    	TextView info;
    }
    
    private void showDialog(){
    	if(progressDialog==null){
    		progressDialog = DialogUtils.showProgressDialog(getActivity(), getString(R.string.msg_dialog_loading_local));
    	}
    	progressDialog.show();
    }
    @SuppressLint("NewApi")
	private void dismissDialog(){
    	if(progressDialog!=null&&!getActivity().isDestroyed()){
    		progressDialog.dismiss();
    	}
    }
    
    
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i = new Intent(this.getActivity(),VideoViewActivity.class);
		i.putExtra(Constans.PARAM_VIDEO, mediaList.get(position));
		startActivity(i);
	}
}
