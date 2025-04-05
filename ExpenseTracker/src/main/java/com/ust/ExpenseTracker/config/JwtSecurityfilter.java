package com.ust.ExpenseTracker.config;

import com.ust.ExpenseTracker.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtSecurityfilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtSecurityfilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader= request.getHeader("Authorization");
        if(authHeader==null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        String jwt=authHeader.substring(7);
        String userName=jwtService.extractUsername(jwt);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(userName!=null&& authentication==null)
        {
            UserDetails userDetails= userDetailsService.loadUserByUsername(userName);
            if(jwtService.isTokenValid(jwt,userDetails)){
                //Session id creation and pass the information to the next filter and updated in security context.
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            return;
        }
        else{
            filterChain.doFilter(request,response);
            return;
        }
    }
}
