package com.example.w3c_school;

import com.example.w3c_school.entity.ContentItem;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class TestCodeActivity extends BaseActivity {

	private EditText testCode;
	private WebView show;
	private Button submit;
	private String codeStr;
	private ContentItem item;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_code);
		testCode = (EditText)findViewById(R.id.testCode);
		show = (WebView)findViewById(R.id.show);
		submit = (Button)findViewById(R.id.submit);
		item = (ContentItem) getIntent().getSerializableExtra("item");
		initData();
	}

	
	private void initData(){
		testCode.setText(item.getTryCode());
		submit.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("SetJavaScriptEnabled")
			@Override
			public void onClick(View v) {
				codeStr = testCode.getText().toString();
				show.setWebChromeClient(new WebChromeClient());
				show.setWebViewClient(new WebViewClient());
				show.getSettings().setJavaScriptEnabled(true);
				show.loadDataWithBaseURL(null, codeStr, "text/html", "utf-8", null);
				
				
			}
		});
		
		
		
	}
	
	public void back(View v) {
		finish();
	}
}
