package com.example.w3c_school.utils;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class StringPostRequest extends StringRequest {

	private Map<String,String> params;
	public StringPostRequest(int method, String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		initMap();
	}
	
	public StringPostRequest(String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(Request.Method.POST, url, listener, errorListener);
		initMap();
	}

	/* 
	 * 返回post所需的参数，request调用该方法，带走数据
	 */
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params;
	}

	
	/**
	 * 初始化map
	 */
	
	private void initMap(){
		params = new HashMap<String, String>();
	}
	
	/**
	 * 向params添加数据
	 */
	public void putParams(String key,String value){
		this.params.put(key, value);
	}
}
