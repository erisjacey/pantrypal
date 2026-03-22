package com.github.erisjacey.pantrypal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) {
    http.authorizeHttpRequests(
            requests -> requests.requestMatchers("/actuator/health").permitAll().anyRequest()
                .authenticated()).httpBasic(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }

  @Bean
  InMemoryUserDetailsManager userDetailsManager(@Value("${APP_USER_1_NAME}") String username1,
      @Value("${APP_USER_1_PASSWORD}") String password1,
      @Value("${APP_USER_2_NAME}") String username2,
      @Value("${APP_USER_2_PASSWORD}") String password2) {

    UserDetails userDetails1 = User.withUsername(username1).password("{noop}" + password1).build();
    UserDetails userDetails2 = User.withUsername(username2).password("{noop}" + password2).build();

    return new InMemoryUserDetailsManager(userDetails1, userDetails2);
  }
}
