package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.rest.shop.springrestshop.entity.Notification;
import spring.rest.shop.springrestshop.entity.Review;
import spring.rest.shop.springrestshop.entity.User;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipientUser(User User);
    List<Notification> getAllByRecipientUserAndIsReadFalse(User user);



}
