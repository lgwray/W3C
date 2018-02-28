package com.example.w3c_school.utils;

import com.example.w3c_school.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageLoaderUitil {
	private static DisplayImageOptions options = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.cc_default_news_img)//���ع�������Ҫ��ͼƬ
	.showImageOnFail(R.drawable.cc_default_news_img_fail)//����ʧ����Ҫ��ͼƬ
	.showImageForEmptyUri(R.drawable.w3welcome)//uriΪ��ʱ��ʾ��ͼƬ
	.cacheInMemory(true)//�Ƿ���л���
	.cacheOnDisk(true)//�Ƿ���̻���
	.bitmapConfig(Bitmap.Config.RGB_565)//��ʾ��ʽ
	.resetViewBeforeLoading(true)
	.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//ͼƬ��ƥ��ʱ����С��ʾ
	.displayer(new FadeInBitmapDisplayer(200))//���뵭��Ч����RoundedBitmapDisplayer��Բ��
	.build();
	
	public static void display(String url,ImageView imageView){
		
		ImageLoader.getInstance().displayImage(url, imageView, options);
	}
}
