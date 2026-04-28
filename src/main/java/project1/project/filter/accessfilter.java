package project1.project.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import project1.project.tokens.tokens;

import java.io.IOException;
import java.util.List;

public class accessfilter implements Filter {
    @Autowired
    private tokens tokens;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest) servletRequest;
        HttpServletResponse resp=(HttpServletResponse) servletResponse;

        String path=req.getRequestURI();
        if(path.contains("/")){
            filterChain.doFilter(req,resp);
            return;
        }
String header=req.getHeader("Authorization");
        if(header !=null && header.startsWith("Bearer ")){
String substring=header.substring(7);
if(tokens.verifyaccesstoken(substring)){
String userid=tokens.decodeaccesstoken(substring);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userid, null, List.of());
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    req.setAttribute("userid", userid);
}
        }
        filterChain.doFilter(req,resp);
    }
}
