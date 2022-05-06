package main.com.simpleonlineshop.filter;

import main.com.simpleonlineshop.dto.UserDto;
import main.com.simpleonlineshop.entity.Role;
import main.com.simpleonlineshop.util.UrlPath;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static main.com.simpleonlineshop.util.UrlPath.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private static final Set<String> PUBLIC_PATHS = Set.of(REGISTRATION, LOGIN);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String uri = ((HttpServletRequest) servletRequest).getRequestURI();

        if (isPublicPath(uri) || isUserLoggedIn(servletRequest)) {

            if (isPathForAdminOnly(uri) && isUserAdmin(servletRequest))
                filterChain.doFilter(servletRequest, servletResponse);
            else if (isPathForAdminOnly(uri) && !isUserAdmin(servletRequest))
                ((HttpServletResponse) servletResponse).sendRedirect(LOGIN);
            else
                filterChain.doFilter(servletRequest, servletResponse);
        } else
            ((HttpServletResponse) servletResponse).sendRedirect(LOGIN);

    }

    private boolean isPathForAdminOnly(String uri) {
        return uri.startsWith(PRODUCTS) || uri.startsWith(USERS);
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {

        UserDto user = (UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return user != null;
    }

    private boolean isPublicPath(String uri) {

        return PUBLIC_PATHS.stream().anyMatch(uri::startsWith);
    }

    private boolean isUserAdmin(ServletRequest servletRequest) {

        UserDto user = (UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");

        if (user == null)
            return false;

        return user.getRole() == Role.ADMIN;
    }
}
