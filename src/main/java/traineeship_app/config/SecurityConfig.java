package traineeship_app.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import traineeship_app.domainmodel.Role;
import traineeship_app.services.UserServiceImpl;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	
    @Autowired
    private CustomSecuritySuccessHandler customSecuritySuccessHandler;
    
	@Bean 
	public UserDetailsService userDetailsService() { 
		 return new UserServiceImpl(); 
	}
	 

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			
		
				http.csrf(csrf -> csrf.disable());
				
                http.authorizeHttpRequests(
                		(authz) -> authz
                		.requestMatchers("/","/library.jpg","/auth/login", "/auth/signup", "/auth/save").permitAll()
                        .requestMatchers("/student/**").hasAnyAuthority(Role.USER_ST.name())
                        .requestMatchers("/company/**").hasAnyAuthority(Role.USER_CO.name())
                        .requestMatchers("/professor/**").hasAnyAuthority(Role.USER_PR.name())
                        .requestMatchers("/committee/**").hasAnyAuthority(Role.USER_COM.name()).anyRequest().authenticated()                     
                		);
                
                http.formLogin(fL -> fL.loginPage("/auth/login")
                		.failureUrl("/auth/login?error=true")
                        .successHandler(customSecuritySuccessHandler)
                        .usernameParameter("username")
                        .passwordParameter("password"));

                
                http.logout(logOut -> logOut.logoutUrl("/auth/logout")
                		.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                		.logoutSuccessUrl("/")
                		);

                http.authenticationProvider(authenticationProvider());

                return http.build();
    }
}