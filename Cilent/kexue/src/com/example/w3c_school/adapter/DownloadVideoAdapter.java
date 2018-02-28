package com.example.w3c_school.adapter;

import java.util.List;

import com.example.w3c_school.R;
import com.example.w3c_school.entity.Video;
import com.example.w3c_school.utils.VideoThumbnail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

public class DownloadVideoAdapter extends BaseAdapter {

	private List<Video> mData;
	private Context mContext;
	DownloadVideoListener listener;

	// ��Ļ���,���������õ���HorizontalScrollView,���԰�ťѡ��Ӧ������Ļ��
	private int mScreentWidth;
	private View view;

	
	
	public DownloadVideoAdapter(List<Video> mData, Context mContext,
			int mScreentWidth) {
		super();
		this.mData = mData;
		this.mContext = mContext;
		this.mScreentWidth = mScreentWidth;
	}

	public void setListener(DownloadVideoListener listener) {
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
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.downloaded_list_item, null);
			holder = new ViewHolder();
			holder.hSView = (HorizontalScrollView) convertView
					.findViewById(R.id.hsv);
			holder.action = convertView.findViewById(R.id.ll_action);

			holder.videoImg = (ImageView) convertView.findViewById(R.id.img);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.size = (TextView) convertView.findViewById(R.id.size);
			holder.delete = (ImageView) convertView.findViewById(R.id.delete);

			// ��������view�Ĵ�СΪ��Ļ���,������ť�����ñ�������Ļ��
			holder.content = convertView.findViewById(R.id.ll_content);
			LayoutParams lp = holder.content.getLayoutParams();
			lp.width = mScreentWidth;

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Video video = this.mData.get(position);
		holder.name.setText(video.getName());
		holder.size.setText(video.getSize());
		if (null != video.getUrl()) {
			holder.videoImg.setImageBitmap(VideoThumbnail
					.getVideoThumbnail(video.getUrl()));
		} else {
			holder.videoImg.setImageResource(R.drawable.w3welcome);
		}
		holder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != listener) {
					listener.deleteVideo(video);
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

	public static class ViewHolder {
		public HorizontalScrollView hSView;
		public View action;
		public View content;

		public TextView name;
		public TextView size;
		public ImageView videoImg;

		public ImageView delete;
	}

	public interface DownloadVideoListener {
		public void deleteVideo(Video video);
	}
	
	

}
