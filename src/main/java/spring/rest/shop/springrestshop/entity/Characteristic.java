package spring.rest.shop.springrestshop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "characteristic")
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "description")
    private String descriptionKey;

    public Characteristic() {

    }

    public Characteristic(String descriptionKey) {
        this.descriptionKey = descriptionKey;

    }
}
