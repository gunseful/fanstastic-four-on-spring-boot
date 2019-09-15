package fantasticfour.controllers;

import fantasticfour.controllers.dao.OrderDao;
import fantasticfour.controllers.dao.ProductDao;
import fantasticfour.entity.Order;
import fantasticfour.entity.Product;
import fantasticfour.entity.Role;
import fantasticfour.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
public class OrderController {

    private final ProductDao productDao;

    private final OrderDao orderDao;

    public OrderController(ProductDao productDao, OrderDao orderDao) {
        this.productDao = productDao;
        this.orderDao = orderDao;
    }


    @GetMapping("/products-to-basket/{id}")
    public String addToUserBasket(
            @AuthenticationPrincipal User user,
            @PathVariable("id") int id, Model model) {
        if (orderDao.findByUserAndStatus(user, "not_ordered") == null) {
            Order order = new Order();
            order.setProducts(new HashMap<>());
            order.setUser(user);
            order.setStatus("not_ordered");
            order.setDate(java.sql.Date.valueOf(LocalDate.now()));
            orderDao.save(order);
        }
        var order = orderDao.findByUserAndStatus(user, "not_ordered");
        var products = order.getProducts();
        var product = productDao.findById(id).orElse(null);
        if (products.containsKey(product)) {
            products.put(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
        order.setProducts(products);
        orderDao.save(order);
        return getBasket(user, model);
    }

    @GetMapping("/basket/product/plus/{id}")
    public String plusProduct(
            @AuthenticationPrincipal User user,
            @PathVariable("id") int id) {

        var order = orderDao.findByUserAndStatus(user, "not_ordered");
        var products = order.getProducts();
        var product = productDao.findById(id).orElse(null);
            products.put(product, products.get(product) + 1);
        order.setProducts(products);
        orderDao.save(order);
        return "redirect:/basket";

    }

    @GetMapping("/basket/product/minus/{id}")
    public String minusProduct(
            @AuthenticationPrincipal User user,
            @PathVariable("id") int id) {

        var order = orderDao.findByUserAndStatus(user, "not_ordered");
        var products = order.getProducts();
        var product = productDao.findById(id).orElse(null);
        if(products.get(product)>1){
        products.put(product, products.get(product) - 1);}
        else{
            products.remove(product);
        }
        order.setProducts(products);
        orderDao.save(order);
        return "redirect:/basket";
    }

    @GetMapping("/basket")
    public String getBasket(@AuthenticationPrincipal User user,
                            Model model) {
        model.addAttribute("order", orderDao.findByUserAndStatus(user, "not_ordered"));
        return "basket";
    }

    @GetMapping("/basket/{id}")
    public String makeOrder(@PathVariable int id) {
        var order = orderDao.findById(id).orElseThrow(NullPointerException::new);
        order.setStatus("ordered");
        order.setDate(java.sql.Date.valueOf(LocalDate.now()));
        orderDao.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String getOrdersByUser(@AuthenticationPrincipal User user,
                                  Model model) {
        model.addAttribute("paid", "paid");
        model.addAttribute("user", user);
        model.addAttribute("admin", Role.ADMIN);
        model.addAttribute("block", Role.BLOCKED);
        ArrayList<Order> list;
        if (user.getRoles().contains(Role.ADMIN)) {
            list = (ArrayList<Order>) orderDao.findAllByStatus("ordered");
            list.addAll(orderDao.findAllByStatus("paid"));
        } else {
            list = (ArrayList<Order>) orderDao.findAllByUserAndStatus(user, "ordered");
            list.addAll(orderDao.findAllByUserAndStatus(user, "paid"));
        }
        list.forEach(System.out::println);
        list.sort(Comparator.comparingLong(Order::getId));
        model.addAttribute("orders", list);
        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String deleteOrder(@PathVariable int id) {
        var order = orderDao.findById(id).orElseThrow(NullPointerException::new);
        orderDao.delete(order);
        return "redirect:/orders";
    }

    @GetMapping("/orders/paid/{id}")
    public String makePaid(@PathVariable int id) {
        var order = orderDao.findById(id).orElseThrow(NullPointerException::new);
        order.setStatus("paid");
        orderDao.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/basket/product/{id}")
    public String deleteFromBasket(@AuthenticationPrincipal User user,
                                   @PathVariable int id) {

        var order = orderDao.findByUserAndStatus(user, "not_ordered");
        System.out.println(order);
        Map<Product, Integer> products = order.getProducts();
        Product productForDelete = null;
        for (Product product : products.keySet()) {
            if (product.getId() == id) {
                productForDelete = product;
            }
        }
        products.remove(productForDelete);
        order.setProducts(products);
        orderDao.save(order);
        return "redirect:/basket";
    }
}
