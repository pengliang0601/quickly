package com.core;

import com.pojo.FilePath;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class LinuxSSH {

	@Autowired private CommandExecutant commandExecutant;
	@Autowired private ReadFile readFile;
	private Set<String> remoteFolder = new HashSet<String>();

	/**
	 * 是否备份文件
	 */
	private boolean isBackupFile;

	public void run() throws Exception {

		String suffix = "_bak" + DateFormatUtils.format(new Date(), "yyyyMMdd");

		while (readFile.hasNext()) {
			FilePath filePath = readFile.readLine();

			String localPath = filePath.getLocalPath();
			String remotePath = filePath.getRemotePath();

			int index = remotePath.lastIndexOf("/");
			String folder = remotePath.substring(0, index);

			if (!remoteFolder.contains(folder)) {
				remoteFolder.add(folder);
				commandExecutant.mkdirs(folder);
			}

			if (this.isBackupFile) {
				commandExecutant.mv(remotePath, remotePath + suffix);
			}
			commandExecutant.putFile(localPath, folder);

		}


	}


	public void setIsBackupFile(boolean isBackupFile) {
		this.isBackupFile = isBackupFile;
	}
}
