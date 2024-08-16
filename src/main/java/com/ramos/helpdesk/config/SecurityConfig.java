package com.ramos.helpdesk.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ramos.helpdesk.security.JWTAuthenticationFilter;
import com.ramos.helpdesk.security.JWTUtil;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private static final String[] PUBLIC_MATCH = { "/h2-console/**" };

	@Autowired
	private Environment env;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired(required=true)
	private UserDetailsService userDetailsService;
	/*
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder().username("email").password("password").roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
	*/

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
/*
	@Bean
	protected JWTAuthenticationFilter jwtAuthenticationFilter(HttpSecurity http) throws Exception {
		JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager(http),
				jwtUtil);
		jwtAuthenticationFilter.setFilterProcessesUrl("/login");
		return jwtAuthenticationFilter;
	}
*/
	// @DependsOn
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http, JWTAuthenticationFilter jwtAuthenticationFilter) throws Exception {
		// final AuthenticationManager authenticationManager =
		// authenticationManager(http);
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}

		http.cors().and().csrf(csrf -> csrf.disable());
		http.addFilter(jwtAuthenticationFilter);
		//http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		// http.addFilter(new JWTAuthenticationFilter(authenticationManager, jwtUtil));
		http.authorizeHttpRequests().requestMatchers(PUBLIC_MATCH).permitAll().anyRequest().authenticated();
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.getOrBuild();
	}

	/*
	 * @Bean public AuthenticationManager authenticationManager( UserDetailsService
	 * userDetailsService, PasswordEncoder passwordEncoder) {
	 * DaoAuthenticationProvider authenticationProvider = new
	 * DaoAuthenticationProvider();
	 * authenticationProvider.setUserDetailsService(userDetailsService);
	 * authenticationProvider.setPasswordEncoder(passwordEncoder);
	 * 
	 * return new ProviderManager(authenticationProvider); }
	 */

	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	CorsConfigurationSource configurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();

		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELITE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
