package com.core;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.Session;
import com.bean.SessionFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 命令执行器
 */
@Component
public class CommandExecutant {

	private final String CODING = "UTF-8";

	@Autowired
	private SessionFactoryBean sessionFactoryBean;


	public String execute(String cmd) throws Exception {

		Session session = sessionFactoryBean.getObject();
		session.execCommand(cmd);
		return getReturnResult(session);
	}

	/**
	 * 将本地文件放到远程服务器指定目录下，默认的文件模式为 0600，即 rw
	 * 如要更改模式，可调用方法 put(fileName, remotePath, mode),模式须是4位数字且以0开头
	 * @param localPath 本地目录地址
	 * @param remotePath 服务器地址
	 */
	public void putFile(String localPath, String remotePath) throws IOException {
		Connection connection = sessionFactoryBean.getConnection();
		SCPClient sc = new SCPClient(connection);
		sc.put(localPath, remotePath);
	}


	public String mv(String oldPath, String newPath) throws Exception {
		return execute("mv " + oldPath + " " + newPath);
	}


	public SFTPv3Client getSFTPv3Client() throws IOException {
		Connection conn = sessionFactoryBean.getConnection();
		SFTPv3Client sft = new SFTPv3Client(conn);
		return sft;
	}

	public String mkdirs(String path) throws Exception {
		return execute("mkdir -vp " + path);
	}




	private String getReturnResult(Session session) throws IOException {

		InputStream stdout = session.getStdout();
		String returnResult = readData(stdout);

		if (StringUtils.isEmpty(returnResult)) {
			InputStream stderr = session.getStderr();
			returnResult = readData(stderr);
		}
		session.close();
		return returnResult;

	}

	private String readData(InputStream is) throws IOException {

		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, CODING));
		String line = null;

		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		if (sb.length() > 0) {
			return sb.toString().substring(0,sb.length()-1);
		}
		return sb.toString();
	}


}
