package dev.saurabh.productservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseModel {
    @Column(unique = true)
    private String name;

    @OneToMany(fetch = jakarta.persistence.FetchType.EAGER, mappedBy = "category")
    private List<Product> products;
}
