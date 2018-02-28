package com.example.w3c_school.adapter;

import java.util.List;

import com.example.w3c_school.R;
import com.example.w3c_school.entity.ContentItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ContentItemAdapter extends BaseAdapter {
	private List<ContentItem> mData;
	private Context mContext;
	BookChooseListener listener;

	public ContentItemAdapter(List<ContentItem> mData, Context mContext) {
		super();
		this.mData = mData;
		this.mContext = mContext;
	}

	public void setListener(BookChooseListener listener) {
		this.listener = listener;
	}

	@Override
	public int getCount() {
		return this.mData.size();
	}

	@Override
	public Object getItem(int position) {
		return this.mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView item;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.save_list_item, null);
			item = (Button) convertView.findViewById(R.id.save_item);

			convertView.setTag(item);
		} else {
			item = (Button) convertView.getTag();
		}

		final ContentItem iContentItem = this.mData.get(position);
		item.setText(iContentItem.getTitle());

		return convertView;
	}

	public interface BookChooseListener {
		public void deleteBook(ContentItem cItem);
	}
}
