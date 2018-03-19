package com.smartparking.security.filters;

import com.smartparking.entity.Client;
import com.smartparking.security.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class StatelessAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenHandler tokenHandler;

    @Value("${app.jwt.tokenname}")
    private String tokenName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(tokenName)).forEach(cookie -> {
                Optional<UserDetails> userDetails = tokenHandler.parseUserFromToken(cookie.getValue());
                if (tokenHandler.isTokenExpired(cookie.getValue())) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    return;
                }
                System.out.println("Filter works");
                response.addCookie(new Cookie(tokenName, tokenHandler.createTokenForUser((Client) userDetails.get())));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.get().getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
        }
        filterChain.doFilter(request, response);
    }
}
