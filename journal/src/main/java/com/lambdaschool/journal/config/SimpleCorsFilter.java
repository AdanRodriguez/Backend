package com.lambdaschool.journal.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// CORS -- allows remote clients access to our sever
// Cross Origin Resource Sharing -- multiple systems can access each other
// Simple Cors Filter
// set up so anyone can access it -- system is not restricted at network level
// can restrict IP address, methods, etc

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter
{

    public SimpleCorsFilter()
    {
    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain) throws IOException, ServletException
    {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin",
                "*");
        //        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Methods",
                "*");
        //        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, content-type, access_token");
        response.setHeader("Access-Control-Allow-Headers",
                "*");
        response.setHeader("Access-Control-Max-Age",
                "3600");

        if (HttpMethod.OPTIONS.name()
                .equalsIgnoreCase(((HttpServletRequest) req).getMethod()))
        {
            response.setStatus(HttpServletResponse.SC_OK);
        } else
        {
            chain.doFilter(req,
                    res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void destroy()
    {
    }
}