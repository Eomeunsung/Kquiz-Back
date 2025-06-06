package com.back.kdquiz.config.security.jwt;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.config.custom.CustomUserDetailService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailService customUserDetailService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            try {
                jwt = authorizationHeader.substring(7);
                log.info("jwt 토큰 "+jwt);
                email = jwtUtil.extractUser(jwt);

            }catch (Exception e) {
                log.error("JWT 토큰 처리 중 예외 발생1: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\":\"JWT에러\", \"message\":\"" + e.getMessage() + "\"}");
                return;
            }
        }
        //이메일이 있고 인증 받은 객체가 없으면 로그인
        if(email!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            try{
                CustomUserDetails customUserDetails = customUserDetailService.loadUserByUsername(email);
                if(jwtUtil.isValidateToken(jwt, customUserDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (JwtException e) {
                log.error("JWT 토큰 처리 중 예외 발생2: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\":\"JWT에러\", \"message\":\"" + e.getMessage() + "\"}");
                return;
            } catch (Exception e) {
                // 기타 예외 처리
                log.error("JWT 토큰 처리 중 예외 발생3: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //500 에러
                response.getWriter().write("JWT Error");
                return;
            }
        }
        filterChain.doFilter(request, response);

    }
}
