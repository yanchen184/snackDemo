package com.yc.snackoverflow.repository;

import com.yc.snackoverflow.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Product entity
 */
@Repository
public interface ProductDao extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    /**
     * Find all products that are active
     */
    List<Product> findByAliveTrue();

    /**
     * Find active products with pagination
     */
    Page<Product> findByAliveTrue(Pageable pageable);

    /**
     * Find active products by name containing a string with pagination
     */
    Page<Product> findByNameContainingAndAliveTrue(String name, Pageable pageable);

    /**
     * Find active products by product class ID with pagination
     */
    Page<Product> findByProductClassIdAndAliveTrue(Long productClassId, Pageable pageable);

    /**
     * Find active products by name containing a string and product class ID with pagination
     */
    Page<Product> findByNameContainingAndProductClassIdAndAliveTrue(String name, Long productClassId, Pageable pageable);

    /**
     * Find active products by names in a list
     */
    List<Product> findByNameInAndAliveTrue(List<String> names);

    /**
     * Check if a product with a specific name exists
     */
    boolean existsByName(String name);

    /**
     * Check if a product with a specific name exists but is not the specified ID
     * (used when updating to check for name conflicts)
     */
    boolean existsByNameAndIdNot(String name, Long id);

    /**
     * Legacy method: List products by name list
     */
    @Query(value = "SELECT * FROM product WHERE (:productNameList IS NULL OR name IN (:productNameList))", nativeQuery = true)
    List<Product> list(List<String> productNameList);
}
