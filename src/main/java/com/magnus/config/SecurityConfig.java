package com.magnus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/**
	 * Configuration for setting up authorization. The mapping of request mappings to authorities is done here
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.httpBasic()
		.and()
		.authorizeRequests()
		.antMatchers("/","/public/**","/login","/login.html","/index.html").permitAll()
		.antMatchers("/admin/**")
		.hasAnyAuthority("ADMIN")
		.antMatchers("/employee/**")
		.hasAnyAuthority("ADMIN","EMPLOYEE")
		.antMatchers("/public/**")
		.permitAll()
		.anyRequest()
		.authenticated().and().logout().logoutUrl("/logout").logoutSuccessUrl("/public/login.html")
		.and()
		.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
}
