package project1.project.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class accessfilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req=(HttpServletRequest) servletRequest;
        HttpServletResponse resp=(HttpServletResponse) servletResponse;

        String path=req.getRequestURI();
        if(path.contains("/") || path.contains())

    }
}
