package com.example.w3c_school.fragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.w3c_school.CategorysActivity;
import com.example.w3c_school.R;
import com.example.w3c_school.adapter.ShelfAdapter;
import com.example.w3c_school.db.DBHelper;
import com.example.w3c_school.entity.Book;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.j256.ormlite.dao.Dao;

public class BookFragment extends BaseFragment implements OnRefreshListener2<ListView>{

	private List<Book> books;
	private ShelfAdapter adapter;
	private PullToRefreshListView myShelf;
	private boolean isInit = false;
	private long startTime;
	
	private Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		books = new ArrayList<Book>();
		adapter = new ShelfAdapter(books, getActivity());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.shelf, null);
		myShelf = (PullToRefreshListView) view.findViewById(R.id.shelf_list);
		myShelf.setAdapter(adapter);
		myShelf.setMode(Mode.BOTH);
		myShelf.setOnRefreshListener(this);
		initView();
		
		startTime = System.currentTimeMillis();
		isInit = true;
		if (isVisible) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					getConData();
				}
			}, 800);
		}
		return view;
	}

	private void initView() {
		myShelf.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Book book = (Book) adapter.getItem(arg2-1);
				Intent intent = new Intent(getActivity(),
						CategorysActivity.class);
				intent.putExtra("book", book);
				startActivity(intent);

			}
		});
	}

	private void getConData() {
		initData();
		books.clear();
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						myShelf.onRefreshComplete();
						Gson gson = new Gson();
						List<Book> conData = gson.fromJson(arg0,
								new TypeToken<ArrayList<Book>>() {
								}.getType());
						if (conData.size() > 0 && null != conData) {
							books.addAll(conData);
							adapter.notifyDataSetChanged();
							Dao<Book, String> bookDao = DBHelper.getInstance(
									getActivity()).getBookDao();
							for (Book b : conData) {
								try {
									Book book = bookDao.queryForId(b
											.getFunctionId()+"");
									if (null != book) {
										bookDao.delete(book);
									}
									bookDao.create(b);
								} catch (SQLException e) {
									e.printStackTrace();
								}
								
							}

						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						myShelf.onRefreshComplete();
						initData();
						Toast.makeText(getActivity(), "服务器连接失败，请重试",
								Toast.LENGTH_SHORT).show();
					}
				});
		
		request.putParams("find", "types");
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	private void initData() {
		Dao<Book, String> bookDao = DBHelper.getInstance(getActivity())
				.getBookDao();
		try {
			List<Book> localBooks = bookDao.queryForAll();
			if (localBooks.size() > 0 && null != localBooks) {
				books.clear();
				books.addAll(localBooks);
				adapter.notifyDataSetChanged();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// books.add(new Book("1", "HTML"));
		// books.add(new Book("2", "HTML5"));
		// books.add(new Book("3", "CSS"));
		// books.add(new Book("4", "JavaScript"));

	}

	@Override
	public void lazyLoadRefresh() {
		long endTime = System.currentTimeMillis();
		if (isVisible && isInit) {
			if ((books.size() > 0 && (endTime - startTime) > 1000 * 60)
					|| books.size() <= 0) {
				//initData();
				getConData();
				startTime = System.currentTimeMillis();
			}

		}

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		
		handler .postDelayed(new Runnable() {

			@Override
			public void run() {
				
				getConData();
				myShelf.onRefreshComplete();

			}
		}, 6000);
		
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		myShelf.onRefreshComplete();
		
	}

}
