package com.ygs.docview;

import com.ygs.docview.config.AppConfig;
import com.ygs.docview.controller.DocRestfulAPI;
import com.ygs.docview.controller.DocumentController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {DocumentController.class, DocRestfulAPI.class,AppConfig.class})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
