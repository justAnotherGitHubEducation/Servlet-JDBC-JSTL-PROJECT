package main.com.simpleonlineshop.servlet;

import lombok.SneakyThrows;
import main.com.simpleonlineshop.dto.CreateProductDto;
import main.com.simpleonlineshop.dto.CreateUserDto;
import main.com.simpleonlineshop.dto.ProductDto;
import main.com.simpleonlineshop.dto.UserDto;
import main.com.simpleonlineshop.entity.Role;
import main.com.simpleonlineshop.exception.ValidationException;
import main.com.simpleonlineshop.mapper.ProductMapper;
import main.com.simpleonlineshop.service.ProductService;
import main.com.simpleonlineshop.util.JspHelper;
import main.com.simpleonlineshop.util.UrlPath;
import main.com.simpleonlineshop.validator.Error;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static main.com.simpleonlineshop.util.UrlPath.*;

public class ProductsServlet extends HttpServlet {

    private static ProductService productService = ProductService.getInstance();
    private final ProductMapper productMapper = ProductMapper.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        try {
            switch (action) {
                case PRODUCTS_CREATE:
                    showNewForm(req, resp);
                    break;
                case PRODUCTS_EDIT:
                    editForm(req, resp);
                    break;
                case PRODUCTS_DELETE:
                    deleteProduct(req, resp);
                    break;
                case PRODUCTS_LIST:
                    listProducts(req, resp);
                    break;
                default:
                    listProducts(req, resp);
                    break;
            }
        } catch (Exception exception) {
            req.setAttribute("errors", List.of(Error.of("RTE", exception.getMessage())));
            req.getRequestDispatcher(JspHelper.getPath("error")).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();

        try {
            switch (action) {
                case PRODUCTS_CREATE:
                    createUpdateProduct(req, resp, true);
                    break;
                case PRODUCTS_EDIT:
                    createUpdateProduct(req, resp, false);
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
    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) {

        int id = Integer.parseInt(req.getParameter("id"));
        productService.delete(id);
        resp.sendRedirect("/products/list");
    }


    @SneakyThrows
    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) {

        req.getRequestDispatcher(JspHelper.getPath("productEdit")).forward(req, resp);
    }

    @SneakyThrows
    private void editForm(HttpServletRequest req, HttpServletResponse resp) {

        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("product", productMapper.mapFrom(productService.findById(id)));
        req.getRequestDispatcher(JspHelper.getPath("productEdit")).forward(req, resp);
    }


    private void createUpdateProduct(HttpServletRequest req, HttpServletResponse resp, boolean IsCreate) throws ServletException, IOException {

        CreateProductDto createProductDto = CreateProductDto.builder()
                .id(req.getParameter("id"))
                .name(req.getParameter("name"))
                .description(req.getParameter("description"))
                .build();

        try {
            if (IsCreate)
                productService.create(createProductDto);
            else
                productService.update(createProductDto);

            req.setAttribute("products", productService.findAll()
                    .stream()
                    .map(product -> productMapper.mapFrom(product))
                    .collect(toList()));

            req.getRequestDispatcher(JspHelper.getPath("products")).forward(req, resp);
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrorList());
            doGet(req, resp);
        }
    }

    @SneakyThrows
    private void listProducts(HttpServletRequest req, HttpServletResponse resp) {

        req.setAttribute("products", productService.findAll()
                .stream()
                .map(product -> productMapper.mapFrom(product))
                .collect(toList()));
        req.getRequestDispatcher(JspHelper.getPath("products")).forward(req, resp);
    }
}