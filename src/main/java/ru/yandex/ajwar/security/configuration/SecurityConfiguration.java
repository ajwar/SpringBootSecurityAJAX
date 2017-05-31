package ru.yandex.ajwar.security.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	

	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder(4));
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/home").permitAll()
				.mvcMatchers("/redirect**","/admin**").hasRole("ADMIN")/*.access("hasRole('ADMIN')")*/
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
				/*.csrf()
				.and()*/
				.exceptionHandling().accessDeniedPage("/Access_Denied");
	}

}
