package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Orders;
import com.example.demo.model.Product;
import com.example.demo.service.category.ICategoryService;
import com.example.demo.service.orders.IOrdersService;
import com.example.demo.service.product.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    private final IOrdersService ordersService;
    private final IProductService productService;
    private final ICategoryService categoryService;
    public OrdersController (IOrdersService ordersService, IProductService productService, ICategoryService categoryService){
        this.ordersService = ordersService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showList(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderDate").descending());
        Page<Orders> orders = ordersService.findAll(pageable);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Orders order : orders) {
            String formattedDate = order.getOrderDate().format(formatter);
            order.setFormattedDate(formattedDate);
        }
        model.addAttribute("orders", orders);
        return "list";
    }

    @PostMapping("/filter")
    public String filterOrders(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("order_date").descending());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate start = parseDate(startDate);
        LocalDate end = parseDate(endDate);

        System.out.println("startDate: " + start + " endDate: " + end);

        Page<Orders> orders;
        if (start != null && end != null) {
            LocalDateTime startDateTime = start.atStartOfDay();
            LocalDateTime endDateTime = end.plusDays(1).atStartOfDay();
            orders = ordersService.findByOrderDateBetween(startDateTime, endDateTime, pageable);
        } else {
            orders = ordersService.findAll(pageable);
        }


        for (Orders order : orders) {
            String formattedDate = order.getOrderDate().format(formatter);
            order.setFormattedDate(formattedDate);
        }

        model.addAttribute("orders", orders);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "list";
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;

        LocalDate date = null;
        DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            if (dateStr.contains("-")) {
                date = LocalDate.parse(dateStr, isoFormatter);
            } else if (dateStr.contains("/")) {
                date = LocalDate.parse(dateStr, customFormatter);
            }
        } catch (DateTimeParseException e) {
            System.out.println("errorMessage" + e.getMessage());
        }
        return date;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        Optional<Orders> orderOptional = ordersService.findById(id);
        List<Product> products = productService.findAll();
        if (orderOptional.isPresent()) {
            model.addAttribute("products", products);
            model.addAttribute("order", orderOptional.get());
            return "edit";
        }
        else {
            model.addAttribute("errorMessage", "Order not found");
            return "error";
        }
    }

    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable("id") Long id, Orders order){
        Orders existingOrder = ordersService.findOrderById(id);
        if (existingOrder != null) {
            existingOrder.setOrderDate(order.getOrderDate());
            existingOrder.setQuantity(order.getQuantity());
            existingOrder.setProduct(order.getProduct());
            ordersService.updateOrder(existingOrder);
        }
        return "redirect:/orders";
    }
}
