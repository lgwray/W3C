package com.example.w3c_school.downloadview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.w3c_school.utils.ExecutorManager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;

/**
 * 
 *���ع���
 *
 **/

public class DownloadManager {
	
	// ����״̬����������ͣ�������У������أ��Ŷ���
	public static final int DOWNLOAD_STATE_NORMAL = 0x00;
	public static final int DOWNLOAD_STATE_PAUSE = 0x01;
	public static final int DOWNLOAD_STATE_DOWNLOADING = 0x02;
	public static final int DOWNLOAD_STATE_FINISH = 0x03;
	public static final int DOWNLOAD_STATE_WAITING = 0x04;
	
	// SparseArray��android�����Hashmap����,�������Ч��
	private SparseArray<DownloadFile> downloadFiles = new SparseArray<DownloadFile>();
	// ��������������������
	private List<DownloadTask> taskList = new ArrayList<DownloadTask>();
	private Handler mHandler;
	private final static Object syncObj = new Object();
	private static DownloadManager instance;
	private ExecutorService executorService;
	
	private DownloadManager(){
		// ���ֻ��ͬʱ����3����������������Ŷӵȴ�
		executorService = ExecutorManager.getInstance().getExecutors();
	}
	
	public static DownloadManager getInstance(){
		if(null == instance){
			synchronized(syncObj) {
				instance = new DownloadManager();
			}
			return instance;
		}
		return instance;
	}
	
	public void setHandler(Handler handler) {
		this.mHandler =  handler;
	}

	// ��ʼ���أ�����һ�������߳�
	public void startDownload(DownloadFile file) {
		downloadFiles.put(file.downloadID, file);
		DownloadTask task = new DownloadTask(file.downloadID);
		taskList.add(task);
		executorService.submit(task);
	}
	
	public void stopAllDownloadTask() {
		while(taskList.size() != 0){
			DownloadTask task = taskList.remove(0);
			// �����������������Ĵ���
			task.stopTask();
		}
		// ��ֹͣ���ڽ��е�����;ܾ������µ�����
		executorService.shutdownNow();
		
	}

	// ��������
	class DownloadTask implements Runnable {

		private boolean isWorking = false;
		private int downloadId;

		public DownloadTask(int id){
			this.isWorking = true;
			this.downloadId = id;
		}
		
		public void stopTask(){
			this.isWorking = false;
		}
		
		// ����listview�ж�Ӧ��item
		public void update(DownloadFile downloadFile){
			Message msg = mHandler.obtainMessage();
			if(downloadFile.totalSize == downloadFile.downloadSize)
				downloadFile.downloadState = DOWNLOAD_STATE_FINISH;
			msg.obj = downloadFile;
			msg.sendToTarget();
			
		}
		
		public void run() {
			// ���������ļ���״̬
			DownloadFile downloadFile = downloadFiles.get(downloadId);
			downloadFile.downloadState = DOWNLOAD_STATE_DOWNLOADING;
			while(isWorking){
				// ����Ƿ��������
				if(downloadFile.downloadState != DOWNLOAD_STATE_DOWNLOADING){
					downloadFiles.remove(downloadFile.downloadID);
					taskList.remove(this);
					isWorking = false;
					break;
				}
				//Log.e("", "downloadSize="+downloadFile.downloadSize+"; size="+downloadFile.totalSize);
				// ����ֻ��ģ�������أ�ÿһ�����һ��item������״̬
				if(downloadFile.downloadSize <= downloadFile.totalSize){
					this.update(downloadFile);
				}
				
				if(downloadFile.downloadSize < downloadFile.totalSize){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
						downloadFile.downloadState = DOWNLOAD_STATE_PAUSE;
						this.update(downloadFile);
						downloadFiles.remove(downloadId);
						isWorking = false;
						break;
					}
					
					++ downloadFile.downloadSize;
				}
			}
		
		}
	}

}

