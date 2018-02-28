package com.example.w3c_school.fragment;

import com.example.w3c_school.LoginActivity;
import com.example.w3c_school.MyVideoActivity;
import com.example.w3c_school.NoteActivity;
import com.example.w3c_school.NoteListActivity;
import com.example.w3c_school.R;
import com.example.w3c_school.SaveActivity;
import com.example.w3c_school.SelfInfoActivity;
import com.example.w3c_school.UpdataPwdActivity;
import com.example.w3c_school.entity.ApkInfo;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.service.VersionUpdateService;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.Constant;
import com.example.w3c_school.utils.ImageLoaderUitil;
import com.example.w3c_school.utils.UtilsURL;
import com.example.w3c_school.view.CircleImageView;
import com.umeng.socialize.net.u;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class InfoFragment extends Fragment implements OnClickListener {

	// private String content;

	private User user;
	private TextView versionName;
	private ApkInfo apkInfo;
	private CheckBox selfLogin;
	private TextView name;
	private CircleImageView photo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// content = getArguments().getString("content");
		user = ApplicationDIV.getInstance().getUser();
		apkInfo = ApplicationDIV.getInstance().getApkInfo();
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_info, null);
		versionName = (TextView) view.findViewById(R.id.versionName);
		name= (TextView) view.findViewById(R.id.name);
		photo = (CircleImageView) view.findViewById(R.id.photo);
		view.findViewById(R.id.myDownload).setOnClickListener(this);
		view.findViewById(R.id.user_info).setOnClickListener(this);
		view.findViewById(R.id.note).setOnClickListener(this);
		view.findViewById(R.id.save).setOnClickListener(this);
		view.findViewById(R.id.updatePwd).setOnClickListener(this);
		selfLogin = (CheckBox) view.findViewById(R.id.auto_login);
		setVersionName();
		//initView();
		return view;
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		user = ApplicationDIV.getInstance().getUser();
		if (null!=user) {
			initView();
		}
		
	}

	private void initView(){
		SharedPreferences sp = getActivity().getSharedPreferences(Constant.SP_FILE_NAME,
				getActivity().MODE_PRIVATE);

		name.setText(user.getUserName());

		ImageLoaderUitil.display(user.getUserImg(), photo,getActivity());
		boolean self_login_flag = sp.getBoolean("self_login", true);
		selfLogin.setChecked(self_login_flag);
		selfLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				SharedPreferences sp =getActivity().getSharedPreferences(
						Constant.SP_FILE_NAME, getActivity().MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putBoolean("self_login", isChecked);
				editor.commit();

			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.myDownload:
			intent = new Intent(getActivity(), MyVideoActivity.class);
			startActivity(intent);
			break;
		case R.id.user_info:
			if (user == null) {
				intent = new Intent(getActivity(), LoginActivity.class);
			} else {
				intent = new Intent(getActivity(), SelfInfoActivity.class);

			}
			startActivity(intent);
			break;
		case R.id.note:
			intent = new Intent(getActivity(), NoteListActivity.class);
			startActivity(intent);
			break;
		case R.id.save:
			intent = new Intent(getActivity(), SaveActivity.class);
			startActivity(intent);
			break;
		case R.id.updatePwd:
			intent = new Intent(getActivity(), UpdataPwdActivity.class);
			startActivity(intent);
			break;
		case R.id.versionName:
			updateVersion();
			setVersionName();
			break;
		default:
			break;
		}

	}
	
	/**
	 * 版本检测及更新
	 */
	public void updateVersion() {
		int vcode;
		// 将版本号和当前安装版本的编号进行对比
		PackageManager pm = getActivity().getPackageManager();
		PackageInfo pki = null;
		try {
			pki = pm.getPackageInfo("com.example.w3c_school", 0);
			int curCode = pki.versionCode;// 当前版本编号
			if (null != apkInfo) {
				vcode = apkInfo.getVid();
			} else {
				vcode = curCode;
			}
			if (vcode > curCode) {
				// 版本有更新
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("版本更新").setMessage("检测到新版本，是否立即更新");
				builder.setPositiveButton("是",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {// which是按钮id

								// 准备下载
								Intent intent = new Intent(
										getActivity(),
										VersionUpdateService.class);
								String url = UtilsURL.ROOT_URL
										+ apkInfo.getUrl();
								intent.putExtra("url", url);
								getActivity().startService(intent);
								dialog.dismiss();// 消失
							}
						});
				builder.setNegativeButton("否",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();// 消失
							}
						});
				AlertDialog alertDialog = builder.create();
				alertDialog.setCancelable(false);
				alertDialog.show();

			} else {
				Toast.makeText(getActivity(), "当前为最新版本",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置版本
	 */
	public void setVersionName() {
		PackageManager pm = getActivity().getPackageManager();
		PackageInfo pki = null;
		try {
			pki = pm.getPackageInfo("com.example.w3c_school", 0);
			String curName = pki.versionName;// 当前版本名称
			versionName.setText(curName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

	}

}
