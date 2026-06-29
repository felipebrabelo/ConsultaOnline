package br.ufscar.dc.dsw.agendamento_online.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.ufscar.dc.dsw.agendamento_online.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.agendamento_online.security.UsuarioDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(IUsuarioDAO usuarioDAO) {
        return new UsuarioDetailsService(usuarioDAO);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/error", "/login/**", "/js/**").permitAll()
                        .requestMatchers("/css/**", "/image/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/", "/home", "/profissionais/listar", "/profissionais/*/curriculo").permitAll()
                        .requestMatchers("/clientes/**", "/profissionais/**").hasRole("ADMIN")
                        .requestMatchers("/consultas/agendar/**", "/consultas/salvar", "/consultas/cliente").hasRole("CLIENTE")
                        .requestMatchers("/consultas/profissional").hasRole("PROFISSIONAL")
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("senha")
                        .permitAll())
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/").permitAll())
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/api/**"));

        return http.build();
    }
}
