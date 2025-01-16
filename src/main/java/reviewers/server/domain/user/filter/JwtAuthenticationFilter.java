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

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // 토큰 검사 제외 경로
        if (requestURI.startsWith("/api/v1/") || requestURI.startsWith("/login")) {
            filterChain.doFilter(request, response); // 토큰 검사 없이 진행
            return;
        }

        // Authorization 헤더 확인
        String token = request.getHeader("Authorization");

        if (token == null || token.isBlank() || !token.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Token is missing or malformed");
            return;
        }

        token = token.substring(7);

        try {
            if (!jwtProvider.validateToken(token)) {
                throw new RuntimeException("Invalid Token");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(e.getMessage());
            return;
        }

        // 토큰에서 사용자 정보 추출
        String email = jwtProvider.getEmailFromToken(token);
        String role = jwtProvider.getRoleFromToken(token);  // `role` 추출

        try {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority(role)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();  // 예외 발생 시 SecurityContext 제거
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(e.getMessage());
        }
    }
}

