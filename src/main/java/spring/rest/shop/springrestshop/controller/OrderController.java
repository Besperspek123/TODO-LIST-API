package spring.rest.shop.springrestshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.entity.Cart;
import spring.rest.shop.springrestshop.entity.Order;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.service.*;

@Controller
public class OrderController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartProductService cartProductService;

    @PostMapping("/createOrder")
    public String createOrder(RedirectAttributes redirectAttributes){
        User currentUser = SecurityContext.getCurrentUser();
        Cart currentCart = currentUser.getCart();
        if(currentCart.getCartProducts().size() != 0){
            orderService.createOrder();
        }

        else redirectAttributes.addAttribute("orderIsEmpty", "true");
        return "redirect:/cart";
    }

    @GetMapping("/orders")
    public String ordersPage(Model model){
        User currentUser = SecurityContext.getCurrentUser();
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("ordersList",currentUser.getOrderList());

        return "order/orders-page";
    }

    @GetMapping("/viewOrder")
    public String viewOrder(@RequestParam(name = "orderId") long orderId,Model model){
        User currentUser = SecurityContext.getCurrentUser();
        Order order = orderService.getOrderDetails(currentUser,orderId);
        model.addAttribute("order",order);
        return "order/details";
    }
}
