package fantasticfour.controllers;

import fantasticfour.controllers.dao.OrderDao;
import fantasticfour.controllers.dao.ProductDao;
import fantasticfour.entity.Order;
import fantasticfour.entity.Product;
import fantasticfour.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;


    @GetMapping("/products-to-basket/{id}")
    public String addToUserBasket(
            @AuthenticationPrincipal User user,
            @PathVariable("id") int id, Model model) {
        if(orderDao.findByUserAndStatus(user,"not_ordered")==null){
            Order order = new Order();
            order.setProducts(new ArrayList<>());
            order.setUser(user);
            order.setStatus("not_ordered");
            orderDao.save(order);
        }
        var order = orderDao.findByUserAndStatus(user,"not_ordered");
        var products = order.getProducts();
        products.add(productDao.findById(id).get());
        order.setProducts(products);
        orderDao.save(order);
        return getBasket(user, model);
    }

    @GetMapping("/basket")
    public String getBasket(@AuthenticationPrincipal User user,
                            Model model) {
        model.addAttribute("order", orderDao.findByUserAndStatus(user,"not_ordered"));
        return "basket";
    }

    @GetMapping("/basket/{id}")
    public String makeOrder(@AuthenticationPrincipal User user,
                            Model model, @PathVariable int id) {
        var order = orderDao.findById(id).get();
        order.setStatus("ordered");
        orderDao.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String getOrdersByUser(@AuthenticationPrincipal User user,
                            Model model) {
        ArrayList<Order> list = (ArrayList<Order>) orderDao.findAllByUserAndStatus(user,"ordered");
        list.addAll(orderDao.findAllByUserAndStatus(user,"paid"));
        list.sort(Comparator.comparingLong(Order::getId));
        model.addAttribute("orders", list);
        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String deleteOrder(@AuthenticationPrincipal User user,
                              Model model, @PathVariable int id) {
        var order = orderDao.findById(id);
        orderDao.delete(order.get());
        return "redirect:/orders";
    }

    @GetMapping("/orders/paid/{id}")
    public String makePaid(@AuthenticationPrincipal User user,
                              Model model, @PathVariable int id) {
        var order = orderDao.findById(id).get();
        order.setStatus("paid");
        orderDao.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/basket/product/{id}")
    public String deleteFromBasket(@AuthenticationPrincipal User user,
                              Model model, @PathVariable int id) {

        var order = orderDao.findByUserAndStatus(user,"not_ordered");
        List<Product> products = order.getProducts();
        Product productForDelete = null;
        for(Product product : products){
            if(product.getId()==id){
                productForDelete = product;
            }
        }
        products.remove(productForDelete);
        order.setProducts(products);
        orderDao.save(order);
        return "redirect:/basket";
    }
}
