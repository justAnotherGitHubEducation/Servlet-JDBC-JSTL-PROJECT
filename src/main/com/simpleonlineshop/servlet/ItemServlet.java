package main.com.simpleonlineshop.servlet;

import lombok.SneakyThrows;
import main.com.simpleonlineshop.dto.*;
import main.com.simpleonlineshop.entity.Product;
import main.com.simpleonlineshop.exception.ValidationException;
import main.com.simpleonlineshop.mapper.ItemMapper;
import main.com.simpleonlineshop.mapper.ProductMapper;
import main.com.simpleonlineshop.service.ItemService;
import main.com.simpleonlineshop.service.ProductService;
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
import java.lang.reflect.AnnotatedArrayType;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static main.com.simpleonlineshop.util.UrlPath.*;

public class ItemServlet extends HttpServlet {

    private final ItemService itemService = ItemService.getInstance();
    private final ProductService productService = ProductService.getInstance();
    private final ProductMapper productMapper = ProductMapper.getInstance();
    private final ItemMapper itemMapper = ItemMapper.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();

        try {
            switch (action) {
                case ITEMS_CREATE:
                    showNewForm(req, resp);
                    break;
                case ITEMS_EDIT:
                    editForm(req, resp);
                    break;
                case ITEMS_DELETE:
                    deleteItem(req, resp);
                    break;
                case ITEMS_LIST:
                    listItems(req, resp);
                    break;
                default:
                    listItems(req, resp);
                    break;
            }
        }
        catch (Exception exception) {
            req.setAttribute("errors", List.of( Error.of("RTE",exception.getMessage())));
            req.getRequestDispatcher(JspHelper.getPath("error")).forward(req, resp);
        }

    }

    private void listItems(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer saleId = Integer.valueOf(req.getParameter("saleId"));
        req.setAttribute("items", itemService.findAllBySaleId(saleId).stream().map(item -> itemMapper.mapFrom(item)).collect(toList()));
        req.setAttribute("saleId", saleId);
        req.getRequestDispatcher(JspHelper.getPath("items")).forward(req, resp);
    }


    private void editForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("products", productService.findAll()
                .stream()
                .map(product -> productMapper.mapFrom(product))
                .collect(toList()));

        int id = Integer.parseInt(req.getParameter("itemId"));
        req.setAttribute("item", itemMapper.mapFrom(itemService.findById(id)));
        req.setAttribute("saleId", req.getParameter("saleId"));
        req.getRequestDispatcher(JspHelper.getPath("itemEdit")).forward(req, resp);
    }


    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer saleId = Integer.valueOf(req.getParameter("saleId"));
        req.setAttribute("products", productService.findAll()
                .stream()
                .map(product -> productMapper.mapFrom(product))
                .collect(toList()));
        req.setAttribute("saleId", saleId);
        req.getRequestDispatcher(JspHelper.getPath("itemEdit")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String action = req.getServletPath();

            switch (action) {
                case ITEMS_CREATE:
                    createUpdateItem(req, resp, true);
                    break;
                case ITEMS_EDIT:
                    createUpdateItem(req, resp, false);
                    break;
                default:
                    listItems(req, resp);
                    break;
            }

        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrorList());
            doGet(req, resp);

        }
        catch (Exception exception) {
            req.setAttribute("errors", List.of( Error.of("RTE",exception.getMessage())));
            doGet(req, resp);
        }
    }

    private void createUpdateItem(HttpServletRequest req, HttpServletResponse resp, boolean isCreate) throws ServletException, IOException {

        CreateItemDto createItemDto = CreateItemDto.builder()
                .id(req.getParameter("itemId"))
                .product_id(req.getParameter("productId"))
                .comment(req.getParameter("comment"))
                .quantity(req.getParameter("quantity"))
                .sale_id(req.getParameter("saleId"))
                .build();

        if (isCreate)
            itemService.create(createItemDto);
        else
            itemService.update(createItemDto);

        listItems(req, resp);
    }


    private void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("itemId"));
        itemService.delete(id);
        req.getRequestDispatcher(ITEMS_LIST).forward(req, resp);
    }
}
