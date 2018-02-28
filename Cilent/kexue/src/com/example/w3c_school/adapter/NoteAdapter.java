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
	// ��Ļ���,���������õ���HorizontalScrollView,���԰�ťѡ��Ӧ������Ļ��
	private int mScreentWidth;
	private View view;
	private OnNoteDeleteListener listener;
	
	/**
	 *  ���췽���໬
	 * @param mData
	 * @param mContext
	 * @param mScreentWidth
	 */
	public NoteAdapter(List<Note> mData, Context mContext, int mScreentWidth) {
		super();
		// ��ʼ��
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
		// ���û�����ù�,��ʼ��convertView
		if (convertView == null) {
			// ������õ�view
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.note_list_item, parent, false);

			// ��ʼ��holder
			holder = new ViewHolder();
			holder.hSView = (HorizontalScrollView) convertView
					.findViewById(R.id.hsv);

			holder.action = convertView.findViewById(R.id.ll_action);
			holder.delete = (Button) convertView.findViewById(R.id.delete);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.date = (TextView) convertView.findViewById(R.id.date);

			// ��������view�Ĵ�СΪ��Ļ���,������ť�����ñ�������Ļ��
			holder.content = convertView.findViewById(R.id.ll_content);
			LayoutParams lp = holder.content.getLayoutParams();
			lp.width = mScreentWidth;

			convertView.setTag(holder);
		} else {
			// ��ֱ�ӻ��ViewHolder
			holder = (ViewHolder) convertView.getTag();
		}
		final Note note  = this.mData.get(position);
		// ��λ�÷ŵ�view��,��������¼��Ϳ���֪�����������һ��item
		
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
		// ���ü����¼�
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
					// ���ViewHolder
					ViewHolder viewHolder = (ViewHolder) v.getTag();
					view = v;
					// ���HorizontalScrollView������ˮƽ����ֵ.
					int scrollX = viewHolder.hSView.getScrollX();

					// ��ò�������ĳ���
					int actionW = viewHolder.action.getWidth();

					// ע��ʹ��smoothScrollTo,����Ч���������Ƚ�Բ��,����Ӳ
					// ���ˮƽ������ƶ�ֵ<��������ĳ��ȵ�һ��,�͸�ԭ
					if (scrollX < actionW / 2) {
						viewHolder.hSView.smoothScrollTo(0, 0);
					} else// ����Ļ���ʾ��������
					{
						viewHolder.hSView.smoothScrollTo(actionW, 0);
					}
					return true;
				}
				return false;
			}
		});

		// �����ֹɾ��һ��item��,ListView���ڲ���״̬,ֱ�ӻ�ԭ
		if (holder.hSView.getScrollX() != 0) {
			holder.hSView.scrollTo(0, 0);
		}

		return convertView;
	}

	/**
	 * ViewHolder
	 * 
	 * @Title:
	 * @Description:��Ҫ�Ǳ����˲��ϵ�view��ȡ��ʼ��.
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
