package com.chongbao.cbplayer.fragment;

import java.io.Serializable;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chongbao.cbplayer.R;
import com.chongbao.cbplayer.activity.MainActivity;
import com.chongbao.cbplayer.activity.VideoViewActivity;
import com.chongbao.cbplayer.utils.DialogUtils;
import com.special.ResideMenu.ResideMenu;


public class BaseFragment extends Fragment implements Parcelable {
	private static final long serialVersionUID = 1L;
	private Dialog progressDialog;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
	}
	
	@SuppressLint("NewApi")
	protected void showDialog(int resID) {
		if (progressDialog == null) {
			progressDialog = DialogUtils.showProgressDialog(getActivity(),
					getString(resID));
		}
		if (!getActivity().isDestroyed()) {
			progressDialog.show();
		}
	}

	@SuppressLint("NewApi")
	protected void dismissDialog() {
		if (progressDialog != null && !getActivity().isDestroyed()) {
			progressDialog.dismiss();
		}
	}

}
