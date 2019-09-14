package fantasticfour.controllers;

import fantasticfour.controllers.dao.OrderDao;
import fantasticfour.controllers.dao.ProductDao;
import fantasticfour.entity.Product;
import fantasticfour.entity.Role;
import fantasticfour.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
public class ProductsController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/products")
    public String getProducts(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("admin", Role.ADMIN);
        if(user.getRoles().contains(Role.ADMIN)){
            model.addAttribute("userlist","/user");
        }
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
        Iterable<Product> products = productDao.findAll();
        model.addAttribute("products", products);

        return "redirect:/products";
    }

    @GetMapping("/products/{id}")
    public String deleteProduct(@PathVariable("id") int id, Model model, @AuthenticationPrincipal User user) {
        orderDao.findAll().forEach(p -> p.getProducts().keySet().stream().filter(o -> o.getId()==id).forEach(o -> p.getProducts().remove(o)));
        productDao.deleteById(id);
        return getProducts(model, user);
    }

}
