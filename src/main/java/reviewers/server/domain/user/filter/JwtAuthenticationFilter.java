package reviewers.server.domain.user.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import reviewers.server.domain.user.provider.JwtProvider;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

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


        if (!jwtProvider.validateToken(token)) {
            throw new BaseErrorException(ErrorType._INVALID_TOKEN);
        }


        // 토큰에서 사용자 정보 추출
        String email = jwtProvider.getEmailFromToken(token);
        String role = jwtProvider.getRoleFromToken(token);
        Long userId = jwtProvider.getUserIdFromToken(token);
        // `role` 추출

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

        try {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, email, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();  // 예외 발생 시 SecurityContext 제거
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(e.getMessage());
        }
    }

    private static boolean isPublicUrl(String requestURI) {
        return
                requestURI.startsWith("/swagger-ui") ||  // Swagger 경로 (하위 경로 모두 포함)
                        requestURI.startsWith("/v3/api-docs") || // OpenAPI 스펙 경로
                        requestURI.startsWith("/favicon.ico") ||
                        requestURI.startsWith("/api/health") ||
                        requestURI.startsWith("/error") ||
                        requestURI.startsWith("/api/v1/user/") ||
                        requestURI.startsWith("/login");
    }
}

