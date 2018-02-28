package com.example.w3c_school;

import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.example.w3c_school.entity.User;




import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class UpdataPwdActivity extends BaseActivity implements OnClickListener{

	private EditText row_password;
	private EditText new_password;
	private EditText sure_password;
	private User user;
	private String rowPwd;
	protected String newPwd;
	protected String againPwd;
	protected String personPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_pwd_view);
		user = ApplicationDIV.getInstance().getUser();
		findViewById(R.id.sure).setOnClickListener(this);
		
		if(null != user){
			
				initView();
		}
		
	}
	
	
	private void initView(){
	 row_password = (EditText)findViewById(R.id.row_password);
	 new_password = (EditText)findViewById(R.id.new_password);
	 sure_password = (EditText)findViewById(R.id.again_password);
	
	 
	}
	
	private void updatePwd(){
		 personPwd = user.getUserPwd();
		 rowPwd = row_password.getText().toString();
		 newPwd = new_password.getText().toString();
		 againPwd = sure_password.getText().toString();
		StringPostRequest request = new StringPostRequest(UtilsURL.LOGIN_URL, new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				
				 if("".equals(rowPwd)){
					 Toast.makeText(UpdataPwdActivity.this, "原密码不能为空",Toast.LENGTH_SHORT).show();
					 return;
				 }
				 if("".equals(newPwd)){
					 Toast.makeText(UpdataPwdActivity.this, "新密码不能为空",Toast.LENGTH_SHORT).show();
					 return;
				 }
				 if("".equals(againPwd)){
					 Toast.makeText(UpdataPwdActivity.this, "请确认密码",Toast.LENGTH_SHORT).show();
					 return;
				 }
				 if(!personPwd.equals(rowPwd)){
					 Toast.makeText(UpdataPwdActivity.this, "密码错误",Toast.LENGTH_SHORT).show();
					 return;
				 }
				 if(!newPwd.equals(againPwd)){
					 Toast.makeText(UpdataPwdActivity.this, "密码输入不一致",Toast.LENGTH_SHORT).show();
					 return;
				 }
				 if(personPwd.equals(rowPwd) && newPwd.equals(againPwd)){
					 Toast.makeText(UpdataPwdActivity.this, "修改成功",Toast.LENGTH_SHORT).show();
					 finish();
				 }
				
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				 Toast.makeText(UpdataPwdActivity.this, "服务器连接失败，请重试",Toast.LENGTH_SHORT).show();
			}
		});
		
		request.putParams("userNo", user.getUserNo());
		request.putParams("userPwd", newPwd);
		request.putParams("update", "1");

		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}
	
	
	/**
	 * 退出该界面
	 */
	public void back(View v){
		finish();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sure:
			updatePwd();
			break;

		default:
			break;
		}
	}

}
