package com.pojo;

/**
 * 文件路径
 */
public class FilePath {

	public FilePath(String remotePath, String localPath) {
		this.remotePath = remotePath;
		this.localPath = localPath;
	}

	/** 服务器路径 */
	private String remotePath;

	/** 本地路径*/
	private String localPath;

	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	@Override
	public String toString() {
		return "FilePath{" + "remotePath='" + remotePath + '\'' + ", localPath='" + localPath + '\'' + '}';
	}
}
