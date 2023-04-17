package spring.rest.shop.springrestshop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "keyword")
@Data
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "keyword")
    private String keyword;

    public Keyword() {
    }

    public Keyword(String keyword) {
        this.keyword = keyword;
    }
}
