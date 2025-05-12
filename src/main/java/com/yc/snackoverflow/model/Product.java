package com.yc.snackoverflow.model;

import com.yc.snackoverflow.model.baseAbstract.BaseLongIdDO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "PRODUCT")
public class Product extends BaseLongIdDO {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String picture;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Boolean alive;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "product",
            cascade = {CascadeType.PERSIST}
    )
    private Set<ProductCommit> productCommits;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(nullable = false)
    private ProductClass productClass;

}