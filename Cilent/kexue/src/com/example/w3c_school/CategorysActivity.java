package com.example.w3c_school;

import java.util.ArrayList;
import java.util.List;

import com.example.w3c_school.adapter.ExpandListAdapter;
import com.example.w3c_school.entity.Category;
import com.example.w3c_school.entity.ContentItem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;

public class CategorysActivity extends BaseActivity {

	private List<ContentItem> groupArray;
	private List<List<ContentItem>> childArray;
	
	private ExpandableListView expandList;
	private ExpandListAdapter adapter;

	private Category c;
	
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categorys);
		groupArray= new ArrayList<ContentItem>();
		childArray= new ArrayList<List<ContentItem>>();
		c=(Category) getIntent().getSerializableExtra("category");
		title = (TextView) findViewById(R.id.title_name);
		title.setText(c.getShceduleName());
		expandList = (ExpandableListView) findViewById(R.id.expandList);
		adapter = new ExpandListAdapter(groupArray, childArray, this);
		expandList.setAdapter(adapter);
		initData();
		adapter.notifyDataSetChanged();
		expandList.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				ContentItem item = (ContentItem) adapter.getChild(groupPosition, childPosition);
				Intent intent = new Intent(CategorysActivity.this,CourseContentActivity.class);
				intent.putExtra("item", item);
				startActivity(intent);
				return true;
			}
		});

	}
	public void back(View view) {
		finish();
	}

	private void initData() {
		groupArray.clear();
		groupArray.add(new ContentItem(1, "html����", 1));
		groupArray.add(new ContentItem(2, "html����", 1));
		groupArray.add(new ContentItem(3, "html��ʽ", 1));
		groupArray.add(new ContentItem(4, "html���", 1));
		
		
		List<ContentItem> cItem1 = new ArrayList<ContentItem>();
		cItem1.add(new ContentItem(5, "html�����������", 1));
		cItem1.add(new ContentItem(6, "html����11", 1));
		
		List<ContentItem> cItem2 = new ArrayList<ContentItem>();
		cItem2.add(new ContentItem(5, "html���ľ�������", 2));
		List<ContentItem> cItem3 = new ArrayList<ContentItem>();
		cItem3.add(new ContentItem(6, "html��ʽ11", 3));
		cItem3.add(new ContentItem(7, "html��ʽ��������", 3));
		List<ContentItem> cItem4 = new ArrayList<ContentItem>();
		cItem4.add(new ContentItem(8, "html��ʽ11", 4));
		cItem4.add(new ContentItem(9, "html��ʽ��������", 4));
		
		childArray.clear();
		childArray.add(cItem1);
		childArray.add(cItem2);
		childArray.add(cItem3);
		childArray.add(cItem4);
	}
}
