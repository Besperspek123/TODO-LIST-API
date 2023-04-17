package spring.rest.shop.springrestshop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name ="product_review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long reviewId;

    @ManyToOne()
    @JoinColumn (name = "product_id")
    private Product productId;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User author;

    @Column(name = "comment")
    private String comment;
    @Column(name = "rating")
    private int rating;
    @Column(name = "date")
    private LocalDate dateCreated;
}
