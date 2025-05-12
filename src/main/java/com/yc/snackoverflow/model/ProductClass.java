package com.yc.snackoverflow.model;

import com.yc.snackoverflow.model.baseAbstract.BaseLongIdDO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PRODUCT_CLASS")
public class ProductClass extends BaseLongIdDO {

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "productClass",
            cascade = {CascadeType.PERSIST}
    )
    private Set<Product> products;

    private String name;

    private Boolean alive;
}