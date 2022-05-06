package main.com.simpleonlineshop.filter;

import main.com.simpleonlineshop.dto.UserDto;
import main.com.simpleonlineshop.util.UrlPath;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnsafeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        UserDto user = (UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");

        if (user != null)
            doFilter(servletRequest, servletResponse, filterChain);
        else
            ((HttpServletResponse) servletResponse).sendRedirect(UrlPath.REGISTRATION);

    }
}
