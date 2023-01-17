package dev.network.socialclub.providers;

import dev.network.socialclub.services.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AppAuthenticationProvider implements AuthenticationProvider {
    private final AppUserDetailsService appUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AppAuthenticationProvider(AppUserDetailsService appUserDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserDetailsService = appUserDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = this.appUserDetailsService.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password,authorities);
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
