package com.example.w3c_school.utils;

import com.example.w3c_school.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import android.content.Context;
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
	

	
	
	public static void display(String url,ImageView imageView,Context mContext){
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext
				).denyCacheImageMultipleSizesInMemory()// �ܾ�����ͼƬ����ߴ�
				// .threadPriority(Thread.NORM_PRIORITY-2)//����ͼƬ�߳����ȼ�
				.taskExecutor(ExecutorManager.getInstance().getExecutors())// ���������̵߳�ִ�������̳߳أ����Լ�д��
				.memoryCacheSize((int) Runtime.getRuntime().maxMemory() / 8)// �����ڴ滺���С��ϵͳ�����С��1/8ΪͼƬ�����ڴ�
				.discCacheSize(1024 * 1024 * 50)// ���ô��̻����С
				.discCacheFileCount(100)// ���ô��̵�����
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				// ʹ��Md5���ܷ�ʽ���ô��̻����ļ�������������
				.diskCache(
						new UnlimitedDiskCache(FileUitlity.getInstance(mContext)
								.makeDir("imagCache")))// ���ô��̻����·��
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())// Ĭ��ͼƬ������
				.imageDownloader(
						new BaseImageDownloader(mContext, 60 * 1000, 60 * 1000))// ���þ����ͼƬ������
				.build();// ����������Ϣ
		ImageLoader.getInstance().init(config);
		ImageLoader.getInstance().displayImage(url, imageView, options);
	}
}
