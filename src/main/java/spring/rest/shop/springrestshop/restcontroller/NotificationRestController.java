package spring.rest.shop.springrestshop.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.cart.CartDTO;
import spring.rest.shop.springrestshop.entity.Notification;
import spring.rest.shop.springrestshop.exception.EntityNotFoundException;
import spring.rest.shop.springrestshop.service.CartService;
import spring.rest.shop.springrestshop.service.NotificationService;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ShopService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationRestController {

    private final ProductService productService;
    private final CartService cartService;
    private final ShopService shopService;
    private final NotificationService notificationService;

   @GetMapping("/notifications")
   public List<Notification> getActualNotifications(){
       return notificationService.getAllNotificationForCurrentUser();
   }
}
