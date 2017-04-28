package ru.yandex.ajwar.security.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.yandex.ajwar.security.service.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
/*public class SecurityConfiguration extends WebMvcConfigurerAdapter {*/

	/*@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;*/
	@Autowired
	private CustomUserDetailsService userDetailsService;
	

	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		//auth.inMemoryAuthentication().withUser("1").password("1").roles("ADMIN");
		//auth.inMemoryAuthentication().withUser("user").password("user").roles("ADMIN");
		//auth.userDetailsService(userDetailsService);
	}
	
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests()
	  	.antMatchers("/", "/home").permitAll()
	  	.antMatchers("/admin*//**").hasRole("ADMIN")*//*.access("hasRole('ADMIN')")*//*
	  	.antMatchers("/dba*//**").access("hasRole('ADMIN') and hasRole('DBA')")
	  	.and()
			  .formLogin()
			  .loginPage("/login")
			  .usernameParameter("ssoId")
			  .passwordParameter("password")
			  .defaultSuccessUrl("/admin")
	  	.and()
			  .rememberMe()
			  .rememberMeParameter("remember-me")
			  .tokenValiditySeconds(86400)
	  	.and()
			  .csrf()
	  	.and()
			  .exceptionHandling().accessDeniedPage("/Access_Denied");
	}*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/home").permitAll()
				.mvcMatchers("/admin**").hasRole("ADMIN")/*.access("hasRole('ADMIN')")*/
				.and()
				.formLogin()
				.loginPage("/login")
				.usernameParameter("ssoId")
				.passwordParameter("password")
				.defaultSuccessUrl("/admin")
				.and()
				.rememberMe()
				.rememberMeParameter("remember-me")
				.tokenValiditySeconds(86400)
				.and()
				.csrf()
				.and()
				.exceptionHandling().accessDeniedPage("/Access_Denied");
	}

}
