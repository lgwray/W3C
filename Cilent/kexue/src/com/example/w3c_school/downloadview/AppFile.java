package com.example.w3c_school.downloadview;

/**
 * 
 * ʵ����
 **/
public class AppFile {
	
	public int id;
	public String name;
	// ��С
	public int size;
	// �����ش�С
	public int downloadSize;
	// ����״̬:����,�������أ���ͣ���ȴ���������
	public int downloadState;
	public String tag;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getDownloadSize() {
		return downloadSize;
	}
	public void setDownloadSize(int downloadSize) {
		this.downloadSize = downloadSize;
	}
	public int getDownloadState() {
		return downloadState;
	}
	public void setDownloadState(int downloadState) {
		this.downloadState = downloadState;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	}
