package main.com.simpleonlineshop.servlet;


import lombok.SneakyThrows;
import main.com.simpleonlineshop.dto.CreateSaleDto;
import main.com.simpleonlineshop.dto.SaleDto;
import main.com.simpleonlineshop.dto.UserDto;
import main.com.simpleonlineshop.entity.Role;
import main.com.simpleonlineshop.entity.Sale;
import main.com.simpleonlineshop.entity.User;
import main.com.simpleonlineshop.exception.ValidationException;
import main.com.simpleonlineshop.mapper.SaleMapper;
import main.com.simpleonlineshop.mapper.UserMapper;
import main.com.simpleonlineshop.service.ItemService;
import main.com.simpleonlineshop.service.SaleService;
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
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static main.com.simpleonlineshop.util.UrlPath.*;

public class SaleServlet extends HttpServlet {

    private static final SaleService saleService = SaleService.getInstance();
    private final UserService userService = UserService.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final SaleMapper saleMapper = SaleMapper.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();

        try {
            switch (action) {
                case SALES_CREATE:
                    showNewForm(req, resp);
                    break;
                case SALES_EDIT:
                    editForm(req, resp);
                    break;
                case SALES_DELETE:
                    deleteSale(req, resp);
                    break;
                case SALES_LIST:
                    listSales(req, resp);
                    break;
                default:
                    listSales(req, resp);
                    break;
            }
        } catch (Exception exception) {
            req.setAttribute("errors", List.of(Error.of("RTE", exception.getMessage())));
            req.getRequestDispatcher(JspHelper.getPath("error")).forward(req, resp);
        }
    }

    @SneakyThrows
    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) {

        req.setAttribute("users",
                userService.findAll().
                        stream().
                        map(user -> userMapper.mapFrom(user))
                        .collect(Collectors.toList()));
        Optional<UserDto> userDto = Optional.ofNullable((UserDto) req.getSession().getAttribute("user"));
        userDto.ifPresent(it -> req.setAttribute("selectedUserId", it.getId()));

        req.getRequestDispatcher(JspHelper.getPath("saleEdit")).forward(req, resp);
    }

    @SneakyThrows
    private void editForm(HttpServletRequest req, HttpServletResponse resp) {

        req.setAttribute("users",
                userService.findAll().
                        stream().
                        map(user -> userMapper.mapFrom(user))
                        .collect(Collectors.toList()));
        int id = Integer.parseInt(req.getParameter("id"));

        req.setAttribute("sale", saleMapper.mapFrom(saleService.findById(id)));
        req.getRequestDispatcher(JspHelper.getPath("saleEdit")).forward(req, resp);
    }

    @SneakyThrows
    private void listSales(HttpServletRequest req, HttpServletResponse resp) {

        req.setAttribute("sales", filteringListForRole(req));
        req.getRequestDispatcher(JspHelper.getPath("sales")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();

        try {
            switch (action) {
                case SALES_EDIT:
                    createUpdateSale(req, resp, false);
                    break;
                case SALES_CREATE:
                    createUpdateSale(req, resp, true);
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

    @SneakyThrows
    private void deleteSale(HttpServletRequest req, HttpServletResponse resp) {

        int id = Integer.parseInt(req.getParameter("id"));
        saleService.delete(id);
        resp.sendRedirect("/sales/list");
    }

    private void createUpdateSale(HttpServletRequest req, HttpServletResponse resp, boolean IsCreate) throws ServletException, IOException {

        CreateSaleDto createSaleDto = CreateSaleDto.builder()
                .id(req.getParameter("id"))
                .date(req.getParameter("date"))
                .description(req.getParameter("description"))
                .user_id(req.getParameter("userId"))
                .build();

        if (IsCreate)
            saleService.create(createSaleDto);
        else
            saleService.update(createSaleDto);

        req.setAttribute("sales", filteringListForRole(req));
        req.getRequestDispatcher(JspHelper.getPath("sales")).forward(req, resp);
    }

    private boolean IsAdmin(HttpServletRequest req) {
        return ((UserDto) req.getSession().getAttribute("user")).getRole() == Role.ADMIN;
    }

    private Integer getCurrentUserId(HttpServletRequest req) {
        return ((UserDto) req.getSession().getAttribute("user")).getId();
    }

    private List<SaleDto> filteringListForRole(HttpServletRequest req) {

        boolean IsAdmin = IsAdmin(req);
        Integer CurrentUserId = getCurrentUserId(req);

        return saleService.findAll()
                .stream()
                .filter(sale -> sale.getUser().getId() == CurrentUserId || IsAdmin)
                .map(sale -> saleMapper.mapFrom(sale))
                .collect(Collectors.toList());
    }
}
