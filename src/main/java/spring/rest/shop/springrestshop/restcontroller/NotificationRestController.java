package spring.rest.shop.springrestshop.restcontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.rest.shop.springrestshop.dto.notification.NotificationForCurrentUserDTO;
import spring.rest.shop.springrestshop.service.NotificationService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Notification",description = "The notification API")

public class NotificationRestController {

    private final NotificationService notificationService;

   @GetMapping("/notifications")
   @Operation(summary = "Get notification for current User")
   public ResponseEntity<List<NotificationForCurrentUserDTO>> getActualNotifications(){
       List<NotificationForCurrentUserDTO> notificationList = notificationService.getAllNotificationForCurrentUser()
               .stream().map(NotificationForCurrentUserDTO::new).collect(Collectors.toList());
       return new ResponseEntity<>(notificationList, HttpStatus.OK);
   }


}
