package com.project.app.securitys.filterSecurity;

//import com.project.shopapp.components.JwtTokenUtils;
//import com.project.shopapp.models.User;

import com.project.app.securitys.componentSecurity.JwtTokenUtils;
import com.project.app.securitys.componentSecurity.LocalizationUtils;
import com.project.app.securitys.modelSecurity.User;
import com.project.app.securitys.utilSecurity.MessageKeys;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor

public class JwtTokenFilter extends OncePerRequestFilter{
    @Value("${api.prefix}")
    private String apiPrefix;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtil;

    private final LocalizationUtils localizationUtils;

    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, ExpiredJwtException,  IOException {
        try {
            if(isBypassToken(request)) {
                filterChain.doFilter(request, response); //enable bypass
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        MessageKeys.UNAUTHORIZED_TOKEN
                        );
                return;
            }
             String tempToken = authHeader;
            if(authHeader.startsWith("Bearer ")){
                tempToken =  authHeader.substring(7);
            }
                final String token = tempToken;

            final String author = this.jwtTokenUtil.extractUserName(token);
            if (author != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) this.userDetailsService.loadUserByUsername(author);
                if(this.jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }else{
                    response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            MessageKeys.UNAUTHORIZED
                    );
                    return;
                }
            }
                filterChain.doFilter(request, response); //enable bypass
        }catch (Exception e) {
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }

    }
    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                // Healthcheck request, no JWT token required
                Pair.of(String.format("%s/author/register", this.apiPrefix), "POST"),
                Pair.of(String.format("%s/author/login", this.apiPrefix), "POST")
        );
        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();

        for (Pair<String, String> token : bypassTokens) {
            String path = token.getFirst();
            String method = token.getSecond();
            // Check if the request path and method match any pair in the bypassTokens list
            if (requestPath.matches(path.replace("**", ".*"))
                    && requestMethod.equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }
}
