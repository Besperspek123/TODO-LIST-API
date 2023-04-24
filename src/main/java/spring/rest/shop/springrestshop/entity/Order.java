package spring.rest.shop.springrestshop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

    @Column(name = "price")
    long price;

    @ManyToMany
    @JoinTable(name = "order_cart_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_product_id"))
    private List<CartProduct> productList = new ArrayList<>();

}
