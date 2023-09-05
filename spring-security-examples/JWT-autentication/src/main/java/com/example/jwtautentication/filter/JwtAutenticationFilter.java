package com.example.jwtautentication.filter;

import com.example.jwtautentication.service.JwtService;
import com.example.jwtautentication.service.UserService;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAutenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization"); //Bearer nvcvernv.vjwenvew.eovnew
    final String username;
    final String token;

    if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    token = authHeader.substring(7);
    username = jwtService.getUsernameFromToken(token);

    if (Strings.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails = userService.loadUserByUsername(username);

      if (jwtService.isValidToken(token, userDetails)) {

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        securityContext.setAuthentication(usernamePasswordAuthenticationToken);

        SecurityContextHolder.setContext(securityContext);

      }

    }

    filterChain.doFilter(request, response);

  }
}
