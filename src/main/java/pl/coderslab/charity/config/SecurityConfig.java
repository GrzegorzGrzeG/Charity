package pl.coderslab.charity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private CustomUserDetails customUserDetails;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorization ->
                authorization
                        .shouldFilterAllDispatcherTypes(false)
                        .requestMatchers(HttpMethod.GET,"/").permitAll()
                        .requestMatchers(HttpMethod.POST,"/").permitAll()
                        .requestMatchers(HttpMethod.GET,"/my").permitAll()
                        .requestMatchers(HttpMethod.GET,"/register").permitAll()
                        .requestMatchers(HttpMethod.POST,"/register").permitAll()
                        .requestMatchers(HttpMethod.GET,"/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"/form").hasAuthority("USER")
                        .requestMatchers(HttpMethod.POST,"/form").hasAuthority("USER")
                        .anyRequest()
                        .authenticated())
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .usernameParameter("email"));


        return http.build();
    }
}
