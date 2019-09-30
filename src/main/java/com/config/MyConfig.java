package com.config;

import ch.ethz.ssh2.Connection;
import com.bean.SessionFactoryBean;
import com.core.LinuxSSH;
import com.core.ReadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.*;
import org.springframework.util.StringValueResolver;

import java.io.IOException;

@Configuration
@ComponentScan("com.*")
@EnableAspectJAutoProxy
@PropertySource(value = "classpath:/properties.properties")
public class MyConfig implements EmbeddedValueResolverAware {

	private StringValueResolver stringValueResolver;

	@Bean
	public Connection connection(
			@Value("${connection.hostname}") String hostname,
			@Value("${connection.username}") String username,
			@Value("${connection.password}") String password) {

		Connection conn = new Connection(hostname);
		try {
			conn.connect();
			conn.authenticateWithPassword(username, password);
			return conn;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Bean
	public ReadFile readFile(
			@Value("${file.path}")String filePath,
			@Value("${remoteRootPath}")String remoteRootPath,
			@Value("${localRootPath}")String localRootPath,
			@Value("${replace.center_directory}")String centerDirectory) throws IOException {

		ReadFile readFile = new ReadFile(filePath);
		if (!remoteRootPath.endsWith("/")) {
			readFile.setRemoteRootPath(remoteRootPath + "/");
		}

		if (!localRootPath.endsWith("/")) {
			readFile.setLocalRootPath(localRootPath);
		}
		readFile.setRemoteRootPath(remoteRootPath);
		readFile.setLocalRootPath(localRootPath);
		readFile.setCenterDirectory(centerDirectory);
		return readFile;
	}

	@Bean
	public LinuxSSH linuxSSH(@Value("${file.backup}") String backup) {

		LinuxSSH linuxSSH = new LinuxSSH();
		linuxSSH.setIsBackupFile("true".equals(backup));

		return linuxSSH;
	}

	@Bean
	public SessionFactoryBean sessionFactoryBean(){
		return new SessionFactoryBean();
	}


	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.stringValueResolver = resolver;
	}


}
