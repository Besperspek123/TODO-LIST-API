package spring.rest.shop.springrestshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.dto.notification.NotificationDTO;
import spring.rest.shop.springrestshop.entity.*;
import spring.rest.shop.springrestshop.exception.CartEmptyException;
import spring.rest.shop.springrestshop.exception.EmptyFieldException;
import spring.rest.shop.springrestshop.exception.EntityNotFoundException;
import spring.rest.shop.springrestshop.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
        List<Notification> notReadNotification = notificationRepository.getAllByRecipientUserAndIsReadFalse(currentUser);
        for (Notification notification: notReadNotification
             ) {
            notification.setIsRead(true);
            notificationRepository.save(notification);
        }
        return notificationRepository.findByRecipientUser(currentUser);
    }

    public void sendMessage(long userId, NotificationDTO notificationDTO) throws EntityNotFoundException, EmptyFieldException {
        Notification notification = new Notification();
        notification.setMessage(notificationDTO.getMessage());
        notification.setTitle(notificationDTO.getTitle());
        if(!SecurityContext.getCurrentUser().getRoles().contains(Role.ROLE_ADMIN)){
            throw new AccessDeniedException("You don`t have role for this command");
        }
        if(userService.getUserById(userId) == null){
            throw new EntityNotFoundException(("User with ID:" + userId + "not found"));
        }
        User recipientUSer = userService.getUserById(userId);
        if(notification.getMessage() == null || notification.getTitle() == null){
            throw new EmptyFieldException("Title and message fields cannot be empty");
        }
        notification.setDate(LocalDate.now());
        notification.setIsRead(false);
        notification.setRecipientUser(recipientUSer);
        notificationRepository.save(notification);

    }

}

