package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.entity.*;
import spring.rest.shop.springrestshop.exception.CartEmptyException;
import spring.rest.shop.springrestshop.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final UserService userService;
    private final ProductService productService;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public List<Notification> getAllNotificationForCurrentUser(){
        User currentUser = SecurityContext.getCurrentUser();
        return notificationRepository.findByRecipientUser(currentUser);
    }

}

