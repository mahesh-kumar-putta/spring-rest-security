package com.luv2code.springboot.cruddemo.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {

//	@Bean
//	public InMemoryUserDetailsManager getUserDetails() {
//		UserDetails mahesh = User.builder().username("mahesh")
//											.password("{noop}test123")
//											.roles("EMPLOYEE").build();
//		UserDetails Sahesh = User.builder().username("sahesh")
//				.password("{noop}test123")
//				.roles("EMPLOYEE","MANAGER").build();
//		UserDetails Rahesh = User.builder().username("rahesh")
//				.password("{noop}test123")
//				.roles("EMPLOYEE","MANAGER","ADMIN").build();
//		
//		return new InMemoryUserDetailsManager(mahesh,Sahesh,Rahesh);
//	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			
			http.authorizeRequests(config -> config
							.requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
							.requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
							.requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
							.requestMatchers(HttpMethod.PUT, "/api/employees").hasRole("MANAGER")
							.requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")		
					);
			http.httpBasic(Customizer.withDefaults());
			http.csrf(csrf -> csrf.disable());
			
			return http.build();
	}
	
	@Bean
	public UserDetailsManager getDetails(DataSource datasource) {
		
		return new JdbcUserDetailsManager(datasource);
	}
	
	
}
