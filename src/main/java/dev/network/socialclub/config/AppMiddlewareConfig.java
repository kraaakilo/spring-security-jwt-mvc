package dev.network.socialclub.config;

import dev.network.socialclub.services.AppUserDetailsService;
import dev.network.socialclub.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AppMiddlewareConfig extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AppUserDetailsService appUserDetailsService;

    @Autowired
    public AppMiddlewareConfig(JwtService jwtService, AppUserDetailsService appUserDetailsService) {
        this.jwtService = jwtService;
        this.appUserDetailsService = appUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/api")) {
            String authorization = request.getHeader("Authorization");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean auth = (authentication instanceof AnonymousAuthenticationToken) || authentication == null;
            if (authorization != null && auth) {
                String extractUsername = jwtService.extractUsername(authorization);
                UserDetails user = appUserDetailsService.loadUserByUsername(extractUsername);
                if (jwtService.isTokenValid(authorization, user)) {
                    System.out.println(user.getUsername());
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user,null,user.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println(user.getUsername());
                }
            } else {
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}
