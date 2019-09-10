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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductsController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        Iterable<Product> products = productDao.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @PostMapping("/products")
    public String addNewProduct(
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam double price,
            Model model) {
        var product = new Product(name, price);
        productDao.save(product);
        System.out.println(user);

        Iterable<Product> products = productDao.findAll();
        model.addAttribute("products", products);

        return "products";
    }

    @GetMapping("/products/{id}")
    public String deleteProduct(@PathVariable("id") int id, Model model) {
        productDao.deleteById(id);
        getProducts(model);
        return getProducts(model);
    }

}
