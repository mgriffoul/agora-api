package com.griffoul.mathieu.agora.infra.authentication.configuration;

import com.griffoul.mathieu.agora.infra.authentication.filter.AuthenticationFilter;
import com.griffoul.mathieu.agora.infra.authentication.service.AuthenticationProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthentConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationEntryPoint rejectedAuthenticationEntryPoint;
    private AuthenticationFilter authenticationFilter;
    private UserDetailsService authenticationUserDetailsService;
    private AuthenticationProviderService authenticationProviderService;

    @Autowired
    public AuthentConfig(AuthenticationEntryPoint rejectedAuthenticationEntryPoint, AuthenticationFilter authenticationFilter, UserDetailsService authenticationUserDetailsService, AuthenticationProviderService authenticationProviderService) {
        this.rejectedAuthenticationEntryPoint = rejectedAuthenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
        this.authenticationUserDetailsService = authenticationUserDetailsService;
        this.authenticationProviderService = authenticationProviderService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProviderService);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().configurationSource(corsConfigurationSource()).and().csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/authentication/authenticate",
                        "/authentication/signup").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(rejectedAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
