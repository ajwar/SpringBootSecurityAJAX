package ru.yandex.ajwar.security.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.io.File;

@SpringBootApplication
public class SpringSecurityRememberMeAnnotation extends SpringBootServletInitializer {

	public static void main(String[] args) {
		String currentPath=SpringSecurityRememberMeAnnotation.class
				.getProtectionDomain()
				.getCodeSource().getLocation()
				.getPath()
				.replace('/', File.separator.charAt(0));
		if (currentPath.indexOf(":")<3 && currentPath.indexOf(":")>0) currentPath=currentPath.substring(1);
		SpringApplication.run(SpringSecurityRememberMeAnnotation.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringSecurityRememberMeAnnotation.class);
	}
}