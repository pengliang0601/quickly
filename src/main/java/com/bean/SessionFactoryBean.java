package com.bean;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

public class SessionFactoryBean implements FactoryBean<Session> {

	@Autowired
	private Connection connection;

	@Override
	public Session getObject() throws Exception {
		return connection.openSession();
	}

	@Override
	public Class<?> getObjectType() {
		return Session.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public Connection getConnection() {
		return connection;
	}

}
