package spring.rest.shop.springrestshop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.rest.shop.springrestshop.entity.Notification;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.service.NotificationService;
import spring.rest.shop.springrestshop.service.ProductService;
import spring.rest.shop.springrestshop.service.ShopService;
import spring.rest.shop.springrestshop.service.UserService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class NotificationController {

    public  final UserService userService;

    public  final ShopService shopService;

    public  final ProductService productService;

    public  final NotificationService notificationService;

    @GetMapping("/notifications")
    public String getAllYourNotifications(Model model){
        List<Notification> notificationList = notificationService.getAllNotificationForCurrentUser();

        model.addAttribute("AllNotification",notificationList);
        return "notification/notification-page";
    }


}
