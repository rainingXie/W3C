package com.example.w3c_school.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.w3c_school.R;
import com.example.w3c_school.entity.Note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class NoteAdapter extends BaseAdapter {
	
	private List<Note> mData;
	private Context mContext;
	// 屏幕宽度,由于我们用的是HorizontalScrollView,所以按钮选项应该在屏幕外
	private int mScreentWidth;
	private View view;
	private OnNoteDeleteListener listener;
	
	/**
	 *  构造方法侧滑
	 * @param mData
	 * @param mContext
	 * @param mScreentWidth
	 */
	public NoteAdapter(List<Note> mData, Context mContext, int mScreentWidth) {
		super();
		// 初始化
		this.mData = mData;
		this.mContext = mContext;
		this.mScreentWidth = mScreentWidth;
	}

	
	public void setListener(OnNoteDeleteListener listener) {
		this.listener = listener;
	}


	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		// 如果没有设置过,初始化convertView
		if (convertView == null) {
			// 获得设置的view
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.note_list_item, parent, false);

			// 初始化holder
			holder = new ViewHolder();
			holder.hSView = (HorizontalScrollView) convertView
					.findViewById(R.id.hsv);

			holder.action = convertView.findViewById(R.id.ll_action);
			holder.delete = (Button) convertView.findViewById(R.id.delete);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.date = (TextView) convertView.findViewById(R.id.date);

			// 设置内容view的大小为屏幕宽度,这样按钮就正好被挤出屏幕外
			holder.content = convertView.findViewById(R.id.ll_content);
			LayoutParams lp = holder.content.getLayoutParams();
			lp.width = mScreentWidth;

			convertView.setTag(holder);
		} else {
			// 有直接获得ViewHolder
			holder = (ViewHolder) convertView.getTag();
		}
		final Note note  = this.mData.get(position);
		// 把位置放到view中,这样点击事件就可以知道点击的是哪一条item
		
		holder.name.setText(note.getName());
		holder.date.setText(note.getDate());
		holder.delete.setTag(position);
		holder.delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (null!=listener) {
					listener.OnNoteDelete(note);
				}
				
			}
		});
		// 设置监听事件
		convertView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (view != null) {
						ViewHolder viewHolder1 = (ViewHolder) view.getTag();
						viewHolder1.hSView.smoothScrollTo(0, 0);
					}
				case MotionEvent.ACTION_UP:
					// 获得ViewHolder
					ViewHolder viewHolder = (ViewHolder) v.getTag();
					view = v;
					// 获得HorizontalScrollView滑动的水平方向值.
					int scrollX = viewHolder.hSView.getScrollX();

					// 获得操作区域的长度
					int actionW = viewHolder.action.getWidth();

					// 注意使用smoothScrollTo,这样效果看起来比较圆滑,不生硬
					// 如果水平方向的移动值<操作区域的长度的一半,就复原
					if (scrollX < actionW / 2) {
						viewHolder.hSView.smoothScrollTo(0, 0);
					} else// 否则的话显示操作区域
					{
						viewHolder.hSView.smoothScrollTo(actionW, 0);
					}
					return true;
				}
				return false;
			}
		});

		// 这里防止删除一条item后,ListView处于操作状态,直接还原
		if (holder.hSView.getScrollX() != 0) {
			holder.hSView.scrollTo(0, 0);
		}

		return convertView;
	}

	/**
	 * ViewHolder
	 * 
	 * @Title:
	 * @Description:主要是避免了不断的view获取初始化.
	 */
	class ViewHolder {
		public HorizontalScrollView hSView;

		public View content;
		public TextView name;
		public TextView date;

		public View action;
		public Button delete;
	}

	public interface OnNoteDeleteListener {
		public void OnNoteDelete(Note note);
	}

}
