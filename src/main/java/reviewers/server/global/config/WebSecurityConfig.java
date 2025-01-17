package reviewers.server.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import reviewers.server.domain.oauth.service.OAuth2UserCustomService;
import reviewers.server.domain.user.entity.Role;
import reviewers.server.domain.user.filter.JwtAuthenticationFilter;
import reviewers.server.domain.user.provider.JwtProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtProvider jwtProvider;
    private final OAuth2UserCustomService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/api/v1/user/**"
                                ,"/swagger-ui/**"
                                ,"/v3/api-docs/**"
                                ,"/favicon.ico"
                                ,"/error").permitAll()
                        .requestMatchers("/api/v1/comments/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api/v1/contents/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .requestMatchers("/api/v1/contents/**").hasRole(Role.ADMIN.name())
                        .requestMatchers("/api/v1/review/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .anyRequest().authenticated()
                )
                        .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(userDetailsService))
                        .defaultSuccessUrl("/api/v1/loginSuccess", true) // 로그인 성공 시 리다이렉트 URL
                        .permitAll()
                ).exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(customAccessDeniedHandler())
                )

                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    private AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Access Denied\", \"message\": \"" +
                    accessDeniedException.getMessage() + "\", \"path\": \"" + request.getRequestURI() + "\"}");
        };
    }
}