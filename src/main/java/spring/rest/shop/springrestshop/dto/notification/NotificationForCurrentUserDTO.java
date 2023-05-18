package spring.rest.shop.springrestshop.dto.notification;

import lombok.Data;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.entity.Notification;

import java.time.LocalDate;

@Data
public class NotificationForCurrentUserDTO {
    long id;
    String title;
    String message;
    LocalDate date;

    public NotificationForCurrentUserDTO(Notification notification) {
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.message = notification.getMessage();
        this.date = notification.getDate();
    }
}
