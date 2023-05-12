package spring.rest.shop.springrestshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name ="review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long reviewId;

    @ManyToOne
    @JoinColumn (name = "product_id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "comment")
    private String comment;
    @Column(name = "rating")
    private int rating;
    @Column(name = "date")
    private LocalDate dateCreated;
}
