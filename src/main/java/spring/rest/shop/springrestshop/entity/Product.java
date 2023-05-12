package spring.rest.shop.springrestshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Column(name = "price")
    private long price;
    @Column(name = "amount_in_store")
    private long amountInStore;
    @Column(name = "sale")
    private int Sale;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<Review> reviewsList = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL)
    private List<Characteristic> characteristicList;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Keyword> keywordsList;

    private String keywordsString;
    private String characteristicsString;


}
