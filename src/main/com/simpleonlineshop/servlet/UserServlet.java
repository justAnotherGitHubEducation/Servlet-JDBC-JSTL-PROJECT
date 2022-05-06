package main.com.simpleonlineshop.servlet;


import lombok.SneakyThrows;
import main.com.simpleonlineshop.dto.CreateSaleDto;
import main.com.simpleonlineshop.dto.CreateUserDto;
import main.com.simpleonlineshop.dto.SaleDto;
import main.com.simpleonlineshop.dto.UserDto;
import main.com.simpleonlineshop.entity.Role;
import main.com.simpleonlineshop.exception.ValidationException;
import main.com.simpleonlineshop.mapper.UserMapper;
import main.com.simpleonlineshop.service.UserService;
import main.com.simpleonlineshop.util.JspHelper;
import main.com.simpleonlineshop.validator.Error;
import org.postgresql.util.PSQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static main.com.simpleonlineshop.util.UrlPath.*;

public class UserServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){

        String action = req.getServletPath();

        try {
            switch (action) {
                case USERS_CREATE:
                    showNewForm(req, resp);
                    break;
                case USERS_EDIT:
                    editForm(req, resp);
                    break;
                case USERS_DELETE:
                    deleteUser(req, resp);
                    break;
                case USERS_LIST:
                    listUsers(req, resp);
                    break;
                default:
                    listUsers(req, resp);
                    break;
            }
        } catch (Exception exception) {
            req.setAttribute("errors", List.of(Error.of("RTE", exception.getMessage())));
            req.getRequestDispatcher(JspHelper.getPath("error")).forward(req, resp);
        }

    }

    @SneakyThrows
    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) {

        int id = Integer.parseInt(req.getParameter("id"));
        userService.delete(id);
        resp.sendRedirect(USERS_LIST);
    }

    @SneakyThrows
    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) {

        req.setAttribute("roles", Role.values());
        req.getRequestDispatcher(JspHelper.getPath("userEdit")).forward(req, resp);
    }

    @SneakyThrows
    private void editForm(HttpServletRequest req, HttpServletResponse resp) {

        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("roles", Role.values());
        req.setAttribute("userNew", userMapper.mapFrom(userService.findById(id)));
        req.getRequestDispatcher(JspHelper.getPath("userEdit")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();

        try {
            switch (action) {
                case USERS_CREATE:
                    createUpdateUser(req, resp, true);
                    break;
                case USERS_EDIT:
                    createUpdateUser(req, resp, false);
                    break;
            }
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrorList());
            doGet(req, resp);
        } catch (Exception exception) {
            req.setAttribute("errors", List.of(Error.of("RTE", exception.getMessage())));
            doGet(req, resp);
        }
    }

    private void createUpdateUser(HttpServletRequest req, HttpServletResponse resp, boolean IsCreate) throws ServletException, IOException {

        CreateUserDto createUserDto = CreateUserDto.builder()
                .id(req.getParameter("id"))
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .bithday(req.getParameter("bithday"))
                .role(req.getParameter("role"))
                .email(req.getParameter("email"))
                .build();

        if (IsCreate)
            userService.create(createUserDto);
        else
            userService.update(createUserDto);

        req.setAttribute("users",
                userService.findAll().
                        stream().
                        map(user -> userMapper.mapFrom(user))
                        .collect(Collectors.toList()));
        req.getRequestDispatcher(JspHelper.getPath("users")).forward(req, resp);

    }

    @SneakyThrows
    private void listUsers(HttpServletRequest req, HttpServletResponse resp) {

        req.setAttribute("users",
                userService.findAll().
                        stream().
                        map(user -> userMapper.mapFrom(user))
                        .collect(Collectors.toList()));
        req.getRequestDispatcher(JspHelper.getPath("users")).forward(req, resp);
    }

}
