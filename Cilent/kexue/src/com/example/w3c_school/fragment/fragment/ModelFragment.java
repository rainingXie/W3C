package com.example.w3c_school.fragment.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.w3c_school.CategorysActivity;
import com.example.w3c_school.ExerciseActivity;
import com.example.w3c_school.R;
import com.example.w3c_school.adapter.CourseAdapter;
import com.example.w3c_school.adapter.CourseAdapter.BookChooseListener;
import com.example.w3c_school.adapter.TestQuestionsAdapter;
import com.example.w3c_school.entity.Category;
import com.example.w3c_school.entity.TestQuestions;
import com.example.w3c_school.view.AutoListView;
import com.example.w3c_school.view.AutoListView.OnRefreshListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ModelFragment extends Fragment implements BookChooseListener,OnRefreshListener{

	// private String content;
	private AutoListView refreshListView;
	private CourseAdapter bookAdapter;
	private List<Category> mBookData;
	private GridView bookShelf;
	private TestQuestionsAdapter contentAdapter;
	private List<TestQuestions> mData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// content = getArguments().getString("content");
		mBookData = new ArrayList<Category>();
		bookAdapter = new CourseAdapter(mBookData, getActivity());
		mData = new ArrayList<TestQuestions>();
		contentAdapter = new TestQuestionsAdapter(mData, getActivity());
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_course_exercise_list, null);
		refreshListView = (AutoListView) view
				.findViewById(R.id.refreshList);
		bookShelf = (GridView) view.findViewById(R.id.bookShelf);
		initData();
		refreshListView.setAdapter(contentAdapter);
		
		bookShelf.setAdapter(bookAdapter);
		bookAdapter.setListener(this);
		refreshListView.setLoadEnable(false);
		refreshListView.setOnRefreshListener(this);
		refreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),ExerciseActivity.class);
				startActivity(intent);
				
			}
		});
		return view;

	}

	private void initData() {
		mBookData.clear();
		mBookData.add(new Category(1, "HTML课程", 1));
		mBookData.add(new Category(1, "HTML网页", 2));
		mBookData.add(new Category(1, "HTML技术", 3));
		mBookData.add(new Category(1, "HTML手册说明", 4));
		
		mData.clear();
		mData.add(new TestQuestions(1,"第一套测试", 1));
		mData.add(new TestQuestions(1,"第二套测试", 1));
		mData.add(new TestQuestions(1,"第三套测试", 1));
		mData.add(new TestQuestions(1,"第四套测试", 1));
	}

	@Override
	public void clickBook(Category c) {
		Intent intent = new Intent(getActivity(),CategorysActivity.class);
		intent.putExtra("category", c);
		startActivity(intent);
		
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

}
