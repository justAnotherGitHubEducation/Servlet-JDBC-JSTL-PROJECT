package main.com.simpleonlineshop.servlet;

import lombok.SneakyThrows;
import main.com.simpleonlineshop.dto.UserDto;
import main.com.simpleonlineshop.entity.Role;
import main.com.simpleonlineshop.mapper.UserMapper;
import main.com.simpleonlineshop.service.UserService;
import main.com.simpleonlineshop.util.JspHelper;
import main.com.simpleonlineshop.util.UrlPath;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static main.com.simpleonlineshop.util.UrlPath.*;


@WebServlet(LOGIN)
public class LoginServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher(JspHelper.getPath("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        userService.login(req.getParameter("login"), req.getParameter("password"))
                .ifPresentOrElse(
                        user -> onLoginSuccess(req, resp, userMapper.mapFrom(user)),
                        () -> onLoginFail(req, resp)
                );
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect(LOGIN + "?error&login = " + req.getParameter("login"));
    }

    @SneakyThrows
    private void onLoginSuccess(HttpServletRequest req, HttpServletResponse resp, UserDto user) {

        req.getSession().setAttribute("isAdmin", user.getRole() == Role.ADMIN);
        req.getSession().setAttribute("user", user);
        resp.sendRedirect(SALES_LIST);
    }
}

