package spring.rest.shop.springrestshop.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.cart.CartDTO;
import spring.rest.shop.springrestshop.dto.notification.NotificationDTO;
import spring.rest.shop.springrestshop.dto.notification.NotificationForCurrentUserDTO;
import spring.rest.shop.springrestshop.entity.Notification;
import spring.rest.shop.springrestshop.exception.EmptyFieldException;
import spring.rest.shop.springrestshop.exception.EntityNotFoundException;
import spring.rest.shop.springrestshop.service.CartService;
import spring.rest.shop.springrestshop.service.NotificationService;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ShopService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationRestController {

    private final ProductService productService;
    private final CartService cartService;
    private final ShopService shopService;
    private final NotificationService notificationService;

   @GetMapping("/notifications")
   public List<NotificationForCurrentUserDTO> getActualNotifications(){
       return notificationService.getAllNotificationForCurrentUser()
               .stream().map(NotificationForCurrentUserDTO::new).collect(Collectors.toList());
   }
   @PostMapping("/notifications/{userId}")
    public NotificationDTO sendNotification(@PathVariable long userId, @RequestBody Notification notification) throws EntityNotFoundException, EmptyFieldException {
            notificationService.sendMessage(userId,notification);
            return new NotificationDTO(notification);
   }
}
