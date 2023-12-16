package spring.rest.shop.springrestshop.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String bearerToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);

            try {
                if (jwtTokenProvider.validateToken(bearerToken)) {
                    Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);

                    if (authentication != null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        filterChain.doFilter(servletRequest, servletResponse);
                    } else {
                        sendUnauthorizedResponse((HttpServletResponse) servletResponse, "Invalid token");
                    }
                } else {
                    sendUnauthorizedResponse((HttpServletResponse) servletResponse, "Expired or invalid token");
                }
            } catch (ExpiredJwtException e) {
                sendUnauthorizedResponse((HttpServletResponse) servletResponse, "Token expired");
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
