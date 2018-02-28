package com.example.w3c_school.view;

import com.example.w3c_school.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyGridView extends GridView {

	private Bitmap background;

	private Bitmap newBackGround;

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.bookcase_bg);
	}

	protected void dispatchDraw(Canvas canvas) {

		// ȡ�ü��صı���ͼƬ�ߣ���
		int width = background.getWidth();
		int height = background.getHeight();

		// ȡ���ֻ���Ļ�ĸߣ�������Ҫע�⣬��Ҫ�ڹ��췽����д����Ϊ����ȡ����ֵ��0
		int scWidth = this.getWidth();
		int scHeight = this.getHeight();

		// ���������ʣ��³ߴ��ԭʼ�ߴ�,��������Ϊ�߲��ñ䣬���Ծ���ԭ��С�ı���1
		// ����һ��Ҫע�⣬�����ֵ�Ǳ���������ֵ��
		float scaleWidth = ((float) scWidth) / width;
		float scaleHeight = 1;

		// Log.v("info", "width:" + width + "height:" + height + "scWidth:" +
		// scWidth + "scaleWidth:" + scaleWidth + "scaleHeight:" + scaleHeight);

		// ��������ͼƬ�õ�matrix����
		Matrix matrix = new Matrix();

		// ����ͼƬ����
		matrix.postScale(scaleWidth, scaleHeight);

		// �����µ�ͼƬ
		newBackGround = Bitmap.createBitmap(background, 0, 0, width, height,
				matrix, true);

		int count = getChildCount();
		int top = 185;
		int backgroundWidth = newBackGround.getWidth();
		int backgroundHeight = newBackGround.getHeight();

		for (int y = top; y < scHeight; y += 223) {
			// for (int x = 0; x<scWidth; x += backgroundWidth){
			canvas.drawBitmap(newBackGround, 0, y, null);
			// }
		}

		super.dispatchDraw(canvas);
	}

	// ��ֹ���� ������
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
