package com.example.w3c_school.adapter;

import java.util.List;

import com.example.w3c_school.R;
import com.example.w3c_school.entity.Book;
import com.example.w3c_school.entity.Note;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class NoteAdapter extends BaseAdapter{

	private List<Note> mData;
	private Context mContext;
	private OnNoteDeleteListener listener;
	

	public void setListener(OnNoteDeleteListener listener) {
		this.listener = listener;
	}

	public NoteAdapter(List<Note> mData, Context mContext) {
		super();
		this.mData = mData;
		this.mContext = mContext;
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
		ViewHolder holder = null;
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.note_list_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.delete = (Button) convertView.findViewById(R.id.delete); 
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		final Note note = this.mData.get(position);
		holder.date.setText(note.getDate());
		holder.name.setText(note.getName());
		holder.delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (null != listener) {
					listener.OnNoteDelete(note);
				}
				
			}
		});
		return convertView;
	}
	
	public static class ViewHolder{
		public TextView name;
		public TextView date;
		public Button delete;
	}

	public interface OnNoteDeleteListener {
		public void OnNoteDelete(Note note);
	} 
}
