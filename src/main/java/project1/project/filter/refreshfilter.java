package project1.project.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project1.project.tokens.tokens;

import java.io.IOException;
import java.util.List;


@Service
public class refreshfilter implements Filter {


    @Autowired
    private  tokens tokens;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req=(HttpServletRequest) servletRequest;
        HttpServletResponse resp=(HttpServletResponse) servletResponse;

        String path=req.getRequestURI();
        if(path.contains("/")){
filterChain.doFilter(req,resp);
return;
        }

        Cookie[] cookie=req.getCookies();
        String refresh=null;
if(cookie !=null){
    for(Cookie c:cookie){
if("refresh".equals(c.getName())){
    refresh=c.getValue();
}
    }
}
boolean verifytoken=tokens.verifyrefreshtoken(refresh);
        if(refresh !=null && verifytoken){
            String userid=tokens.decoderefreshtoken(refresh);
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userid, List.of());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            req.setAttribute("userid",userid);
        }
filterChain.doFilter(req,resp);
    }
}
