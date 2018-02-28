package com.example.w3c_school;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.w3c_school.entity.Exercise;
import com.example.w3c_school.entity.TestQuestions;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class ExerciseActivity extends BaseActivity {

	private LinearLayout pager;
	private List<Exercise> exercises;

	private TextView title_name;
	private Button submit;

	private TestQuestions question;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_questions_view);
		pager = (LinearLayout) findViewById(R.id.pager);
		title_name = (TextView) findViewById(R.id.title_name);
		submit = (Button) findViewById(R.id.submit);
		exercises = new ArrayList<Exercise>();
		question = (TestQuestions) getIntent().getSerializableExtra("questions");
		//initData();
		getConData();		
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				@SuppressWarnings("unchecked")
				List<Exercise> eeList = (List<Exercise>) msg.obj;
				if(eeList.size()>0&&null!=eeList){
					exercises.clear();
					exercises.addAll(eeList);
					initView();
				}
				break;

			default:
				break;
			}
		}
		
	};

	/**
	 * 将数据添加到界面
	 */
	@SuppressLint({ "UseSparseArrays", "InflateParams" })
	private void initView() {
		TestQuestions questions = (TestQuestions) getIntent()
				.getSerializableExtra("questions");
		title_name.setText(questions.getPageName());
		for (int i = 0; i < exercises.size(); i++) {
			final View view = LayoutInflater.from(this).inflate(
					R.layout.test_layout, null);
			final int no = i + 1;
			TextView questionView = (TextView) view.findViewById(R.id.question);
			questionView.setText((i + 1) + ". " + exercises.get(i).getTitle());
			RadioGroup group = (RadioGroup) view.findViewById(R.id.chooseItem);
			group.setOrientation(RadioGroup.VERTICAL);
			RadioButton itemButton = (RadioButton) LayoutInflater.from(this)
					.inflate(R.layout.choose_radio_btn, null);
			itemButton.setText(exercises.get(i).getItemA());
			itemButton.setId(1);
			group.addView(itemButton);
			itemButton = (RadioButton) LayoutInflater.from(this).inflate(
					R.layout.choose_radio_btn, null);
			itemButton.setText(exercises.get(i).getItemB());
			itemButton.setId(2);
			group.addView(itemButton);
			itemButton = (RadioButton) LayoutInflater.from(this).inflate(
					R.layout.choose_radio_btn, null);
			itemButton.setText(exercises.get(i).getItemC());
			itemButton.setId(3);
			group.addView(itemButton);
			itemButton = (RadioButton) LayoutInflater.from(this).inflate(
					R.layout.choose_radio_btn, null);
			itemButton.setText(exercises.get(i).getItemD());
			itemButton.setId(4);
			group.addView(itemButton);

			final TextView answerView = (TextView) view
					.findViewById(R.id.answer);
			answerView.setVisibility(View.GONE);

			group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					String str = null;
					switch (checkedId) {
					case 1:
						str = "A";
						break;
					case 2:
						str = "B";
						break;
					case 3:
						str = "C";
						break;
					case 4:
						str = "D";
						break;

					default:
						break;
					}
					if (str.equals(exercises.get(no - 1).getAnswer())) {
						answerView.setTextColor(Color.BLACK);
					} else {
						answerView.setTextColor(Color.RED);

					}
					answerView.setText(no + "." + str + "\t正确答案："
							+ exercises.get(no - 1).getAnswer());
					

				}

			});
			pager.addView(view);
		}

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int child = pager.getChildCount();
				for (int i = 0; i < child; i++) {
					LinearLayout viewChild = (LinearLayout) pager.getChildAt(i);
					TextView answer = (TextView) viewChild
							.findViewById(R.id.answer);
					answer.setVisibility(View.VISIBLE);
				}

			}
		});

	}
	
	
	private void getConData() {
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						
						Gson gson = new Gson();
						List<Exercise> eList = gson.fromJson(arg0,
								new TypeToken<ArrayList<Exercise>>() {
								}.getType());
						
						if (eList.size() > 0 && null != eList) {
							Message msg = handler.obtainMessage();
							msg.obj = eList;
							msg.what = 1;
							handler.sendMessage(msg);
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(getApplicationContext(), "服务器连接失败，请重试！",
								Toast.LENGTH_SHORT).show();

					}

					
				});
		request.putParams("find", "testpagers");
		request.putParams("tag", question.getTag()+"");
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	private void initData() {
		exercises.clear();
		exercises.add(new Exercise(1, "你喜欢吃什么水果？", "A.香蕉", "B.苹果", "C.橘子",
				"D.葡萄", "A"));
		exercises.add(new Exercise(2, "你喜欢吃什么水果？", "A.香蕉", "B.苹果", "C.橘子",
				"D.葡萄", "B"));
	}

	public void back(View view) {
		finish();
	}

}
