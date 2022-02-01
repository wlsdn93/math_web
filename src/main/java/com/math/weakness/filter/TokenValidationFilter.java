package com.math.weakness.filter;


import com.math.weakness.oauth.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TokenValidationFilter implements Filter {

    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException {
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            jwtService.parseJwt(request.getParameter("accessToken"));
        } catch (ExpiredJwtException e) {
            log.info("ExpiredJwtException is caught by TokenValidationFilter");
            res.sendError(401, "This token has been expired");
        } catch (SignatureException e) {
            res.sendError(401, "This token has been forged");
        } catch (JwtException e) {
            res.sendError(401, "An error occurred for some reason");
        }
    }
}
