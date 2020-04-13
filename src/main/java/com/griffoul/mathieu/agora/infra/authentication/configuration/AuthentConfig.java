package com.griffoul.mathieu.agora.infra.authentication.configuration;

import com.griffoul.mathieu.agora.infra.authentication.entry.point.RejectedAuthenticationEntryPoint;
import com.griffoul.mathieu.agora.infra.authentication.filter.AuthenticationFilter;
import com.griffoul.mathieu.agora.infra.authentication.service.AuthenticationTokenService;
import com.griffoul.mathieu.agora.infra.authentication.service.AuthenticationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthentConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationTokenService jwtTokenService(){
        return new AuthenticationTokenService();
    }

    @Bean
    public AuthenticationUserDetailsService authenticationUserDetailsService(){
        return new AuthenticationUserDetailsService();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter(authenticationUserDetailsService(), jwtTokenService());
    }
    @Bean
    public RejectedAuthenticationEntryPoint jwtAuthenticationEntryPoint(){
        return new RejectedAuthenticationEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().configurationSource(corsConfigurationSource()).and().csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/authentication/authenticate",
                        "/authentication/signup").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationUserDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }
}
