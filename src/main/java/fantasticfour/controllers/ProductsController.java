package fantasticfour.controllers;

import fantasticfour.controllers.service.ProductService;
import fantasticfour.entity.Product;
import fantasticfour.entity.Role;
import fantasticfour.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
public class ProductsController {

    public static Logger logger = LogManager.getLogger();

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @Value("${upload.path}")
    private String uploadPath;

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
            @RequestParam("file") MultipartFile file,
            @RequestParam String name,
            @RequestParam double price,
            Model model) throws IOException {
        var product = new Product(name, price);

        if(file!=null && !file.getOriginalFilename().isEmpty()){
            File uploadFolder = new File(uploadPath);
            if(uploadFolder.exists()){
                uploadFolder.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath+"/"+resultFileName));

            product.setFilename(resultFileName);
        }
        productService.save(product);
        logger.info("Added new product name={}", product.getName());
        model.addAttribute("products", productService.findAll());

        return "redirect:/products";
    }

    @GetMapping("/products/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        logger.info("Deleted product id={}", id);
        return "redirect:/products";
    }

}
