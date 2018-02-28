package com.example.w3c_school.downloadview;

/**
 * 
 * 下载的文件
 **/

public class DownloadFile {
	
	public int downloadID;
	public int downloadSize;
	public int totalSize;
	public int downloadState;
	public String downloadTag;
	public int getDownloadID() {
		return downloadID;
	}
	public void setDownloadID(int downloadID) {
		this.downloadID = downloadID;
	}
	public int getDownloadSize() {
		return downloadSize;
	}
	public void setDownloadSize(int downloadSize) {
		this.downloadSize = downloadSize;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public int getDownloadState() {
		return downloadState;
	}
	public void setDownloadState(int downloadState) {
		this.downloadState = downloadState;
	}
	public String getDownloadTag() {
		return downloadTag;
	}
	public void setDownloadTag(String downloadTag) {
		this.downloadTag = downloadTag;
	}
	
	
	
}
