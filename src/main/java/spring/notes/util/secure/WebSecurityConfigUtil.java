package spring.notes.util.secure;

import org.springframework.http.HttpMethod;
import spring.notes.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfigUtil {

    final UserService userService;
    private final AuthEntryPointUtil unauthorizedHandler;

    public WebSecurityConfigUtil(UserService userService, AuthEntryPointUtil unauthorizedHandler) {
        this.userService = userService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthTokenFilterUtil authenticationJwtTokenFilter(JwtUtil jwtUtils, UserService userDetailsService) {
        return new AuthTokenFilterUtil(jwtUtils, userDetailsService);
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtil jwtUtils, UserService userDetailsService) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF
                .cors(cors -> cors.disable()) // Disable CORS
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(unauthorizedHandler)
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/notes/**").authenticated()
                        .requestMatchers("/api/test/all").permitAll()
                        .anyRequest().authenticated()
                );
        http.addFilterBefore(new AuthTokenFilterUtil(jwtUtils, userDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
