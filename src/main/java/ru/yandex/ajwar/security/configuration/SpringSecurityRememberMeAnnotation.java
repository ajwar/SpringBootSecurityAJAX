package ru.yandex.ajwar.security.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class SpringSecurityRememberMeAnnotation extends SpringBootServletInitializer {
	public static Map<String,String> mapProps=new HashMap<>();

	public static void main(String[] args){
/*		String currentPath=SpringSecurityRememberMeAnnotation.class
				.getProtectionDomain()
				.getCodeSource().getLocation()
				.getPath()
				.replace('/', File.separator.charAt(0));
		if (currentPath.indexOf(":")<3 && currentPath.indexOf(":")>0) currentPath=currentPath.substring(1);*/
		SpringApplication.run(SpringSecurityRememberMeAnnotation.class, args);

	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringSecurityRememberMeAnnotation.class);
	}
}