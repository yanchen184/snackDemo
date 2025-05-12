package com.yc.snackoverflow.repository;

import com.yc.snackoverflow.data.ProductClassDto;
import com.yc.snackoverflow.model.Product;
import com.yc.snackoverflow.model.ProductClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductClassDao extends JpaRepository<ProductClass, Long> {

    @Query(value = "SELECT * FROM Product_class WHERE (:nameList IS NULL OR NAME IN (:nameList)) ", nativeQuery = true)
    List<ProductClass> list(List<String> nameList);
}
