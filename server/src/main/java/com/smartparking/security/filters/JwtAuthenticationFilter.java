package com.smartparking.security.filters;

import com.smartparking.security.tokens.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenUtil tokenUtil;

    @Value("${app.jwt.header_string}")
    private String HEADER_STRING;
    @Value("${app.jwt.token_prefix}")
    private String TOKEN_PREFIX;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Go in filter");
        String header = request.getHeader(HEADER_STRING);
        System.out.println("Find header" + header);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX+" ","");
            System.out.println("Find token "  + authToken);
            try {
                username = tokenUtil.getUsernameFromToken(authToken);
                System.out.println("Username " + username);
            } catch (IllegalArgumentException e) {
                System.out.println("an error occured during getting username from token");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                System.out.println("the token is expired and not valid anymore");
            } catch(SignatureException e){
                System.out.println("Authentication Failed. Username or Password not valid.");
                e.printStackTrace();
            }
        } else {
            System.out.println("couldn't find bearer string, will ignore the header");
        }
        System.out.println("Try to autorize");
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("SecurityContextHolder.getContext().getAuthentication() = " + SecurityContextHolder.getContext().getAuthentication());

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("Username from details " + userDetails.getUsername());
            System.out.println("Password from details " + userDetails.getPassword());
            System.out.println("Authority from details " + userDetails.getAuthorities());

            if (tokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        filterChain.doFilter(request, response);

    }
}
