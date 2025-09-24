package io.github.CarlosBackend.libraryapi.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable) // desabilita o csrf

                .httpBasic(Customizer.withDefaults()) // habilita o basic auth
                .authorizeHttpRequests(authorize -> {
                    authorize.anyRequest().authenticated();
                }) // autoriza qualquer requisicao
                .build();
    }
}
