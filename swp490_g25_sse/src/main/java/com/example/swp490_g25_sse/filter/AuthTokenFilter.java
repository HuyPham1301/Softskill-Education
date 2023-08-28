/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.filter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import javax.servlet.*;
import com.example.swp490_g25_sse.service.UserServiceImpl;
import com.example.swp490_g25_sse.util.JwtUtils;

public class AuthTokenFilter implements Filter {

@Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserServiceImpl userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (!(auth instanceof AnonymousAuthenticationToken)) { /* The user is logged in :) */
//            
//            if () { /* The user doesn't have jwt :) */
//                String jwt = jwtUtils.generateJwtToken(auth);
//
//                CustomUserDetailsService userDetails = (CustomUserDetailsService) auth.getPrincipal();
//                List<String> roles = userDetails.getAuthorities().stream()
//                        .map(item -> item.getAuthority())
//                        .collect(Collectors.toList());
//
//                System.out.println();
//                System.out.println(jwt);
//
//            } else { /* user already have jwt */
//                try {
//                    String jwt = parseJwt(request);
//                    if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//                        String username = jwtUtils.getUserNameFromJwtToken(jwt);
//                        CustomUserDetailsService userDetails = userDetailsService.loadUserByUsername(username);
//                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                                userDetails, null, userDetails.getAuthorities());
//                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
//                    }
//                } catch (Exception e) {
//                    logger.error("Cannot set user authentication: {}", e);
//                }
//            }
//        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}
