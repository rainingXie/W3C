package com.example.w3c_school.adapter;

import java.util.List;

import com.example.w3c_school.entity.ContentItem;

import android.app.Activity;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandListAdapter extends BaseExpandableListAdapter {

	private List<ContentItem> groupArray;
	private List<List<ContentItem>> childArray;

	private Activity activity;

	
	
	public ExpandListAdapter(List<ContentItem> groupArray,
			List<List<ContentItem>> childArray, Activity activity) {
		super();
		this.groupArray = groupArray;
		this.childArray = childArray;
		this.activity = activity;
	}

	@Override
	public int getGroupCount() {
		return this.groupArray.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.childArray.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.groupArray.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.childArray.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ContentItem ci = groupArray.get(groupPosition);
		return  getGenericView(ci.getTitle(),true);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ContentItem cItem =childArray.get(groupPosition).get(childPosition);  
		return getGenericView(cItem.getTitle(),false);
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;// 当返回true时，子元素可被点击
	}

	public TextView getGenericView(String string,boolean isBold) {
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT );
		TextView text = new TextView(activity);
		text.setLayoutParams(layoutParams);
		text.setGravity(Gravity.CENTER_VERTICAL);
		text.setText(string);
		if (isBold) {//字体加粗
			TextPaint tp = text.getPaint(); 
			tp.setFakeBoldText(true); 
			text.setTextSize(14);
			text.setPadding(80, 10, 10, 10);
		}else {
			text.setTextSize(12);
			text.setPadding(100, 10, 10, 10);
		}
		return text;
	}

}
