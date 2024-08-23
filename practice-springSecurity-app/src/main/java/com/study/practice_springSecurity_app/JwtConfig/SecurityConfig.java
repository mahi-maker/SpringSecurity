package com.study.practice_springSecurity_app.JwtConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.study.practice_springSecurity_app.JwtFilter.JwtFilter;
import com.study.practice_springSecurity_app.JwtService.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private UserService userService;
	
	 @Autowired
	    private JwtFilter jwtFilter;
	 
	 @Bean
	 AuthenticationProvider authenticationProvider() {
		 DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		 provider.setUserDetailsService(userService);
		 provider.setPasswordEncoder(new BCryptPasswordEncoder());
		 return provider;
	 }
	 
	 @Bean
	     AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
	 @Bean
	     SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http.csrf(AbstractHttpConfigurer::disable)
	                .authorizeHttpRequests(auth -> auth
	                        .requestMatchers("/login", "/signup").permitAll()
	                        .anyRequest().authenticated())
	                .sessionManagement(session -> session
	                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	                .cors(AbstractHttpConfigurer::disable);
	        
	        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	        
	        return http.build();
	    }
}
