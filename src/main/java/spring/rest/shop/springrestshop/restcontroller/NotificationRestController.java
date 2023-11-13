package spring.rest.shop.springrestshop.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Notification",description = "The notification API")

public class NotificationRestController {

    private final ProductService productService;
    private final CartService cartService;
    private final ShopService shopService;
    private final NotificationService notificationService;

   @GetMapping("/notifications")
   @Operation(summary = "Get notification for current User")
   public List<NotificationForCurrentUserDTO> getActualNotifications(){
       return notificationService.getAllNotificationForCurrentUser()
               .stream().map(NotificationForCurrentUserDTO::new).collect(Collectors.toList());
   }

   //TODO need to move to admin controller
   @PostMapping("/notifications/{userId}")
   @Operation(summary = "Send notification to User")
    public NotificationDTO sendNotification(@PathVariable long userId, @RequestBody Notification notification) throws EntityNotFoundException, EmptyFieldException {
            notificationService.sendMessage(userId,notification);
            return new NotificationDTO(notification);
   }
}
