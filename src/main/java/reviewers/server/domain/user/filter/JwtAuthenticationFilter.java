package reviewers.server.domain.user.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import reviewers.server.domain.oauth.Filter.GoogleTokenVerifier;
import reviewers.server.domain.user.provider.JwtProvider;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;  // JWT 검증
    private final GoogleTokenVerifier googleTokenVerifier;  // Google 액세스 토큰 검증 로직

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        log.info("requestURI: {}", requestURI);

        // 토큰 검사 제외 경로
        // 테스트를 위해 모두 제외, 추후에 수정 필요
        if (isPublicUrl(requestURI)) {
            filterChain.doFilter(request, response); // 토큰 검사 없이 진행
            return;
        }

        // Authorization 헤더 확인
        String token = request.getHeader("Authorization");

        if (token == null || token.isBlank() || !token.startsWith("Bearer ")) {
            throw new BaseErrorException(ErrorType._TOKEN_MISSING);
        }

        token = token.substring(7);

        try {
            if (jwtProvider.validateToken(token)) {
                setAuthenticationFromJwt(token);
            } else {
                setAuthenticationFromGoogleToken(token);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();  // 예외 발생 시 SecurityContext 제거
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + e.getMessage() + "\"}");
        }
    }

    private void setAuthenticationFromJwt(String token) {
        try {
            String email = jwtProvider.getEmailFromToken(token);
            String role = jwtProvider.getRoleFromToken(token);
            Long userId = jwtProvider.getUserIdFromToken(token);

            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, email, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new BaseErrorException(ErrorType._INVALID_TOKEN);
        }
    }

    private void setAuthenticationFromGoogleToken(String token) {
        try {
            GoogleTokenVerifier.GoogleUserInfo userInfo = googleTokenVerifier.verify(token);

            String email = userInfo.getEmail();
            String userId = userInfo.getProviderId();

            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, email, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new BaseErrorException(ErrorType._INVALID_TOKEN);
        }
    }

    private static boolean isPublicUrl(final String requestURI) {

        return requestURI.startsWith("/swagger-ui/") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/favicon.ico") ||
                requestURI.startsWith("/api/health") ||
                requestURI.startsWith("/error") ||
                requestURI.startsWith("/api/v1/user") ||
                requestURI.startsWith("/login") ||
                requestURI.startsWith("/api/v1/loginSuccess");
    }
}

