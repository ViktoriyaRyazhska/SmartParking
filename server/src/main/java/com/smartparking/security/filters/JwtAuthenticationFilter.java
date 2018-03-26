package com.smartparking.security.filters;

import com.smartparking.security.tokens.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
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
        LOGGER.info("Go in filter");
        String header = request.getHeader(HEADER_STRING);
        LOGGER.info("Find header" + header);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX+" ","");
            LOGGER.info("Find token "  + authToken);
            try {
                username = tokenUtil.getUsernameFromToken(authToken);
                LOGGER.info("Find user with username " + username);
            } catch (IllegalArgumentException e) {
                LOGGER.warn("An error occured during getting username from token");
            } catch (ExpiredJwtException e) {
                LOGGER.warn("The token is expired and not valid anymore");
            } catch(SignatureException e){
                LOGGER.warn("Authentication Failed. Username or Password not valid.");
            }
        } else {
            LOGGER.warn("Couldn't find bearer string, will ignore the header");
        }
        LOGGER.info("Try to autorize");
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            LOGGER.info("Username from details " + userDetails.getUsername());
            LOGGER.info("Authority from details " + userDetails.getAuthorities());

            if (tokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                LOGGER.info("Authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        filterChain.doFilter(request, response);

    }
}
