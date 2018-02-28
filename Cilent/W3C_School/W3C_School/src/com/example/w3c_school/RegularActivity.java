package com.example.w3c_school;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RegularActivity extends BaseActivity{

	private WebView regular;
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regular);
		regular = (WebView) findViewById(R.id.regular);
		regular.loadUrl("file:///android_asset/html/regular.html");
		progressDialog = new ProgressDialog(RegularActivity.this);//提示可为空
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("正在加载...");
		progressDialog.show();
		
		regular.setWebViewClient(new WebViewClient(){



			@Override
			public void onPageFinished(WebView view, String url) {
				if(progressDialog!=null&&progressDialog.isShowing()){//数据加载完毕时
				      progressDialog.dismiss();
				 }
			}
			
		});
	}
	public void back(View view) {
		finish();
	}

}
