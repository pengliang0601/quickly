package com;

import com.config.MyConfig;
import com.core.LinuxSSH;
import com.core.ReadFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

	public static void main(String[] args) throws Exception {

		// 注释
/*		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig.class);
		LinuxSSH bean = applicationContext.getBean(LinuxSSH.class);
		bean.run();*/

		ReadFile readFile = new ReadFile("G:/IDEA/myProject/quickly/src/main/resources/fileList.txt");


	}


}
