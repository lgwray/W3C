package com.example.w3c_school.utils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.w3c_school.entity.ApkInfo;
import com.example.w3c_school.entity.User;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import android.app.Application;
import android.view.WindowManager;

/**
 * requestQueueȫ�ֱ������������̿��ã������ط����ô���
 *
 */

/**
 * ��AndroidManiFest.xml��<application>�� name ��ע���Application
 *
 */
public class ApplicationDIV extends Application {

	private static ApplicationDIV application;

	private RequestQueue requestQueue;
	private User user;

	private ApkInfo apkInfo;

	private WindowManager.LayoutParams windowParams;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;// ��ApplicationDIV��ֵ��application��̬����
		requestQueue = Volley.newRequestQueue(this);// �����������
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).denyCacheImageMultipleSizesInMemory()// �ܾ�����ͼƬ����ߴ�
				// .threadPriority(Thread.NORM_PRIORITY-2)//����ͼƬ�߳����ȼ�
				.taskExecutor(ExecutorManager.getInstance().getExecutors())// ���������̵߳�ִ�������̳߳أ����Լ�д��
				.memoryCacheSize((int) Runtime.getRuntime().maxMemory() / 8)// �����ڴ滺���С��ϵͳ�����С��1/8ΪͼƬ�����ڴ�
				.discCacheSize(1024 * 1024 * 50)// ���ô��̻����С
				.discCacheFileCount(100)// ���ô��̵�����
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				// ʹ��Md5���ܷ�ʽ���ô��̻����ļ�������������
				.diskCache(
						new UnlimitedDiskCache(FileUitlity.getInstance(this)
								.makeDir("imagCache")))// ���ô��̻����·��
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())// Ĭ��ͼƬ������
				.imageDownloader(
						new BaseImageDownloader(this, 60 * 1000, 60 * 1000))// ���þ����ͼƬ������
				.build();// ����������Ϣ

		ImageLoader.getInstance().init(config);
		windowParams = new WindowManager.LayoutParams();
	}

	/**
	 * ���� ��̬����
	 */
	public static ApplicationDIV getInstance() {
		return application;
	}

	public RequestQueue getRequestQueue() {
		return this.requestQueue;

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public WindowManager.LayoutParams getWindowParams() {
		return this.windowParams;
	}

	public ApkInfo getApkInfo() {
		return apkInfo;
	}

	public void setApkInfo(ApkInfo apkInfo) {
		this.apkInfo = apkInfo;
	}

}
