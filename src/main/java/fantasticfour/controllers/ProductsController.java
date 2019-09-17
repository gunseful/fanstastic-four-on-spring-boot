package fantasticfour.controllers;

import fantasticfour.controllers.service.ProductService;
import fantasticfour.entity.Product;
import fantasticfour.entity.Role;
import fantasticfour.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getProducts(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("admin", Role.ADMIN);
        if(user.getRoles().contains(Role.ADMIN)){
            model.addAttribute("userlist","/user");
        }
        Iterable<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @PostMapping("/products")
    public String addNewProduct(
            @RequestParam String name,
            @RequestParam double price,
            Model model) {
        var product = new Product(name, price);
        productService.save(product);
        model.addAttribute("products", productService.findAll());

        return "redirect:/products";
    }

    @GetMapping("/products/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

}
