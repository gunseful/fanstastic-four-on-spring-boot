package fantasticfour.controllers;

import fantasticfour.controllers.service.OrderService;
import fantasticfour.entity.Role;
import fantasticfour.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
public class OrderController {

    private static Logger logger = LogManager.getLogger();

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/basket/add/{id}")
    public String addToUserBasket(
            @AuthenticationPrincipal User user,
            @PathVariable("id") int id) {
        orderService.addToUserBasket(user, id);
        return "redirect:/basket";
    }

    @GetMapping("/basket/product/plus/{id}")
    public String plusProduct(
            @AuthenticationPrincipal User user,
            @PathVariable("id") int id) {

        orderService.plusProduct(user, id);
        return "redirect:/basket";

    }

    @GetMapping("/basket/product/minus/{id}")
    public String minusProduct(
            @AuthenticationPrincipal User user,
            @PathVariable("id") int id) {
        orderService.minusProduct(user, id);
        return "redirect:/basket";
    }

    @GetMapping("/basket")
    public String getBasket(@AuthenticationPrincipal User user,
                            Model model) {
        model.addAttribute("order", orderService.findByUserAndStatus(user, "not_ordered"));
        return "basket";
    }

    @GetMapping("/basket/{id}")
    public String makeOrder(@PathVariable int id) {
        orderService.makeOrder(id);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String getOrdersByUser(@AuthenticationPrincipal User user,
                                  Model model) {
        model.addAttribute("paid", "paid");
        model.addAttribute("user", user);
        model.addAttribute("admin", Role.ADMIN);
        model.addAttribute("block", Role.BLOCKED);
        model.addAttribute("orders", orderService.getOrdersByUser(user));
        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        logger.info("Order id={} has been deleted", id);

        return "redirect:/orders";
    }

    @GetMapping("/orders/paid/{id}")
    public String makePaid(@PathVariable int id) {
        orderService.makePaid(id);
        logger.info("Order id={} has changed status to PAID", id);
        return "redirect:/orders";
    }

    @GetMapping("/basket/product/{id}")
    public String deleteFromBasket(@AuthenticationPrincipal User user,
                                   @PathVariable int id) {

        orderService.deleteFromBasket(user, id);
        return "redirect:/basket";
    }
}
