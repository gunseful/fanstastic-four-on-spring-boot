package fantasticfour.controllers;

import fantasticfour.controllers.dao.ProductDao;
import fantasticfour.controllers.dao.UserDao;
import fantasticfour.entity.Product;
import fantasticfour.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductsController {

    @Autowired
    private ProductDao productDao;

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/products")
    public String users(Model model) {
        Iterable<Product> products = productDao.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @PostMapping("/products")
    public String addUser(@RequestParam String name, @RequestParam double price, Model model){
        var product = new Product(name, price);
        productDao.save(product);

        Iterable<Product> products = productDao.findAll();
        model.addAttribute("products", products);

        return "products";
    }
}
