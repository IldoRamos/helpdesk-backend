package com.ramos.helpdesk.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.ramos.helpdesk.security.JWTAuthenticationFilter;
import com.ramos.helpdesk.security.JWTAuthorizationFilter;
import com.ramos.helpdesk.security.JWTUtil;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	private static final String[] PUBLIC_MATCH = { "/h2-console/**" };

	@Autowired
	private Environment env;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired(required=true)
	private UserDetailsService userDetailsService;


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		configure(authenticationManagerBuilder);
		return authenticationManagerBuilder.build();
	}
	
	@Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new JWTAuthenticationFilter(authenticationManager, jwtUtil);
    }
	
	@Bean
	public JWTAuthorizationFilter jwtAuthorizationFilter( AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
		return new JWTAuthorizationFilter(authenticationManager, jwtUtil, userDetailsService);
	}

	@SuppressWarnings("removal")
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http, JWTAuthenticationFilter jwtAuthenticationFilter, JWTAuthorizationFilter jwtAuthorizationFilter) throws Exception {

		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers(headers -> headers.frameOptions().disable());
		}

        http.cors(withDefaults());
		http.csrf(csrf -> csrf.disable());
		http.addFilter(jwtAuthenticationFilter);
		http.addFilter(jwtAuthorizationFilter);
		//http.authorizeHttpRequests((authorizeHttpRequests) ->
		//authorizeHttpRequests.requestMatchers(PUBLIC_MATCH).permitAll());
		
		http.authorizeHttpRequests((authorizeHttpRequests) ->
			authorizeHttpRequests.requestMatchers("/**").permitAll().anyRequest().authenticated());
	

		
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.getOrBuild();
	}

	

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
/*
	@Bean
	CorsConfigurationSource configurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();

		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELITE", "OPTIONS"));
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); 
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Permite os cabeçalhos necessários
        configuration.setAllowCredentials(true);
        
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}*/

	@Bean
    protected CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:4200"); 
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELITE", "OPTIONS"));
        corsConfiguration.addAllowedHeader("*"); 
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
