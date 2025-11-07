package io.github.CarlosBackend.libraryapi.config;

import io.github.CarlosBackend.libraryapi.security.LoginSocialSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilter(
            HttpSecurity http,
            LoginSocialSuccessHandler successHandler
            ) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable) // desabilita o csrf
                .httpBasic(Customizer.withDefaults()) // habilita o basic auth

                .formLogin(configurer -> {configurer.loginPage("/login");
                })
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login").permitAll();
                    authorize.requestMatchers(HttpMethod.POST,"/usuarios").permitAll();
                    authorize.anyRequest().authenticated();
                }) // autoriza qualquer requisicao
                .oauth2Login(
                        oauth2 -> oauth2
                                .loginPage("/login")
                                .successHandler(successHandler)
                )
                .oauth2ResourceServer(oauth2Rs -> oauth2Rs.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }


public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

    // Configure o JwtGrantedAuthoritiesConverter para ajustar prefixos ou áreas de autoridade
    JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
    authoritiesConverter.setAuthorityPrefix(""); // Define o prefixo para authorities
    authoritiesConverter.setAuthoritiesClaimName("roles"); // Nome do claim JWT que contém os authorities

    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

    return jwtAuthenticationConverter;

    }


}