package main.com.simpleonlineshop.servlet;

import main.com.simpleonlineshop.dto.CreateUserDto;
import main.com.simpleonlineshop.entity.Role;
import main.com.simpleonlineshop.entity.User;
import main.com.simpleonlineshop.exception.ValidationException;
import main.com.simpleonlineshop.service.UserService;
import main.com.simpleonlineshop.util.JspHelper;
import main.com.simpleonlineshop.util.UrlPath;
import main.com.simpleonlineshop.validator.Error;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static main.com.simpleonlineshop.util.UrlPath.REGISTRATION;


public class RegistrationServlet extends HttpServlet {

    private static final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CreateUserDto createUserDto = CreateUserDto.builder()
                .login(req.getParameter("name"))
                .password(req.getParameter("password"))
                .bithday(req.getParameter("bithday"))
                .role(req.getParameter("role"))
                .email(req.getParameter("email"))
                .build();

        try {
            userService.create(createUserDto);
            req.getRequestDispatcher(JspHelper.getPath("login")).forward(req, resp);
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrorList());
            doGet(req, resp);
        }
        catch (Exception exception) {
            req.setAttribute("errors", List.of( Error.of("RTE",exception.getMessage())));
            doGet(req, resp);
        }
    }
}
