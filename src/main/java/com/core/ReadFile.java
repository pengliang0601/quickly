package com.core;

import com.pojo.FilePath;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class ReadFile {


	private Queue<String> queue = new LinkedList<String>();

	/** 服务器根目录*/
	private String remoteRootPath;

	/** 本地根目录 */
	private String localRootPath;

	/** 替换中间目录 */
	private String centerDirectory="/WebRoot/WEB-INF/classes/";

	public ReadFile(String filePath) throws IOException {
		loadLocalFile(filePath);
	}

	public void loadLocalFile(String filePath) throws IOException {
		String str = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
		while ((str = br.readLine()) != null) {
			if (StringUtils.isEmpty(str)) {
				continue;
			}
			if (str.contains("\\src\\") && str.contains(".java")) {
				str = str.replace("\\src\\", centerDirectory).replace(".java", ".class");
			}


			String path = str.trim().replace("\\", "/");
			if (path.endsWith(".class")) {

				File file = new File(path);

				String parentFileName = file.getName().substring(0, file.getName().lastIndexOf("."));
				for (File listFile : file.getParentFile().listFiles((File dir, String name) -> {
					return name.matches(parentFileName + "\\$.*\\.class");
				})) {
					queue.add(listFile.getPath().replace("\\", "/"));
				}
			}

			queue.add(str.trim().replace("\\", "/"));


		}
		br.close();
	}


	public FilePath readLine() {
		String localPath = queue.poll();
		return new FilePath(localPath.replace(localRootPath, remoteRootPath), localPath);
	}

	public boolean hasNext(){
		return queue.size() > 0;
	}

	public int size(){
		return queue.size();
	}

	public String getRemotePath(String localPath){
		return localPath.replace(localRootPath, remoteRootPath);
	}

	public void setRemoteRootPath(String remoteRootPath) {
		this.remoteRootPath = remoteRootPath;
	}

	public void setLocalRootPath(String localRootPath) {
		this.localRootPath = localRootPath;
	}

	public void setCenterDirectory(String centerDirectory) {
		this.centerDirectory = centerDirectory;
	}
}
