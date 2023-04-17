package spring.rest.shop.springrestshop.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.swing.*;
import java.util.List;

@Entity
@Table(name = "organization")
@Data
public class Organization {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @Column(name = "activity")
    private boolean activity;


    @OneToMany(mappedBy = "organization" ,cascade = CascadeType.ALL)
    private List<Product> productList;



    private ImageIcon logotype;


}
