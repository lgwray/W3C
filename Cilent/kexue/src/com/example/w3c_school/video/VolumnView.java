package com.example.w3c_school.video;

import com.example.w3c_school.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * ��С���������ڻ��ν�����
 */
public class VolumnView extends View {

	// �뾶
	float r1 = 0;
	float r2 = 0;
	float r3 = 0;
	// ��Բ���
	float w1 = 15;
	// ��Բ���
	float w2 = 30;
	Paint paint;

	// ����
	float progress = 0;

	Bitmap bitmap;
	
	RectF oval;

	public VolumnView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public VolumnView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public VolumnView(Context context) {
		super(context);
		init(context);
	}

	void init(Context context) {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Style.STROKE);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ling);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		float cx = getMeasuredWidth() / 2;
		float cy = getMeasuredHeight() / 2;
		r1 = cx - w1 / 2;
		r2 = cx - w1 / 2 - w2 / 2;
		r3 = cx - w1 / 2 - w2;

		// ������Բ
		paint.setStrokeWidth(w1);
		paint.setColor(Color.parseColor("#454547"));
		canvas.drawCircle(cx, cy, r1, paint);

		// �����м�Բ��
		paint.setColor(Color.parseColor("#747476"));
		paint.setStrokeWidth(w2);
		canvas.drawCircle(cx, cy, r2, paint);

		// ������Բ
		paint.setColor(Color.parseColor("#464648"));
		paint.setStyle(Style.FILL);
		canvas.drawCircle(cx, cy, r3, paint);

		// �����м��ͼƬ
		canvas.drawBitmap(bitmap, cx - bitmap.getWidth() / 2,
				cx - bitmap.getHeight() / 2, paint);

		// �����ı�
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(0);
		paint.setTextSize(40);
		float textWidth = paint.measureText("����"); // ���������ȣ�������Ҫ��������Ŀ��������Բ���м�

		canvas.drawText("����", cx - textWidth / 2, cx + bitmap.getHeight() / 2
				+ 40, paint);

		// ���ƽ���
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(w2);
		paint.setColor(Color.WHITE);
		if (oval == null) {
			oval = new RectF(cx - r2, cy - r2, cx + r2, cy + r2); // ���ڶ����Բ������״�ʹ�С�Ľ���
		}
		canvas.drawArc(oval, 270, 360 * progress / 100, false, paint);

		super.onDraw(canvas);
	}

	/**
	 * ���ý���
	 * 
	 * @param progress
	 *            ��Χ(0-100)
	 */
	public void setProgress(float progress) {
		this.progress = progress;
		if (this.progress >= 100)
			this.progress = 100;
		if (this.progress <= 0)
			this.progress = 0;
		postInvalidate();
	}
}
