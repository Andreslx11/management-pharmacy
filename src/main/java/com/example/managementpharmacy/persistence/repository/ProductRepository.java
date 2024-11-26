package com.example.managementpharmacy.persistence.repository;


import com.example.managementpharmacy.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


/**
 * Repository interface for interacting with the Product entity.
 * Extends JpaRepository to provide basic CRUD operations.
 * Contains custom queries for retrieving products based on different search criteria.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {



    @Query("SELECT p FROM Product p WHERE UPPER(p.state)" +
            " = UPPER(:state) ORDER BY p.id DESC")
    List<Product> findByState(String state);


    @Query( "SELECT p FROM Product p " +
            "WHERE UPPER(p.tradeName) LIKE UPPER(CONCAT('%', :tradeName, '%')) " +
            "ORDER BY p.id DESC")
    List<Product> findByTradeName(@Param("tradeName") String tradeName);


    @Query("SELECT p FROM Product p " +
            "WHERE (:tradeName IS NULL OR UPPER(p.tradeName) LIKE UPPER(CONCAT('%', :tradeName, '%'))) " +
            "AND (:state IS NULL OR UPPER(p.state) = UPPER(:state))")
    List<Product> findAllByFilters(@Param("tradeName") String tradeName, @Param("state") String state);


//

    @Query(value = "SELECT p.id_product, p.trade_name, p.generic_name, p.laboratory, " +
            "p.presentation, p.concentration, p.stock, p.sale_price, " +
            "p.expiration_date, p.category, p.invima_registration, p.description, " +
            "p.contraindications, p.supplier_id, p.creation_date, p.update_date, p.state, p.url_key " +
            "FROM products p " +
            "WHERE (:tradeName IS NULL OR UPPER(p.trade_name) LIKE UPPER(CONCAT('%', :tradeName, '%'))) " +
            "AND (:expirationDateFrom IS NULL OR p.expiration_date >= :expirationDateFrom) " +
            "AND (:expirationDateTo IS NULL OR p.expiration_date <= :expirationDateTo) " +
            "AND (:salePriceFrom IS NULL OR p.sale_price >= :salePriceFrom) " +
            "AND (:salePriceTo IS NULL OR p.sale_price <= :salePriceTo) " +
            "AND (:state IS NULL OR UPPER(p.state) = UPPER(:state))",
            nativeQuery = true)
    Page<Product> paginatedSearch(
            @Param("tradeName") String tradeName,
            @Param("expirationDateFrom") LocalDate expirationDateFrom,
            @Param("expirationDateTo") LocalDate expirationDateTo,
            @Param("salePriceFrom") BigDecimal salePriceFrom,
            @Param("salePriceTo") BigDecimal salePriceTo,
            @Param("state") String state,
            Pageable pageable);

}
