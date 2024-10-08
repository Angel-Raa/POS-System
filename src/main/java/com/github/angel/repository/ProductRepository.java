/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.github.angel.repository;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.angel.dto.ProductDTO;
import com.github.angel.dto.ProductStockDTO;
import com.github.angel.entity.Product;

/**
 *
 * @author aguero
 */
@Repository
public interface ProductRepository
        extends ListPagingAndSortingRepository<Product, Long>, BaseJpaRepository<Product, Long> {
    @Query("SELECT new com.github.angel.dto.ProductDTO(p.productId, p.name, p.price, p.stock, p.description, p.categoryId) FROM Product p WHERE p.id = :productId")
    Optional<ProductDTO> findByProductIdDto(@Param("productId") Long productId);

    @Query("SELECT new com.github.angel.dto.ProductDTO(p.productId, p.name, p.price, p.stock, p.description, p.categoryId) FROM Product p")
    List<ProductDTO> findAllProductDTOs();

    @Query("SELECT new com.github.angel.dto.ProductDTO(p.productId, p.name, p.price, p.stock, p.description, p.categoryId) FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ProductDTO> findByName(@Param("name") String name);

    @Query("SELECT new com.github.angel.dto.ProductDTO(p.productId, p.name, p.price, p.stock, p.description, p.categoryId) FROM Product p WHERE p.categoryId = :categoryId")
    List<ProductDTO> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT new com.github.angel.dto.ProductDTO(p.productId, p.name, p.price, p.stock, p.description, p.categoryId) FROM Product p WHERE p.categoryId = :categoryId AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ProductDTO> findByProductsByCategory(@Param("categoryId") Long categoryId, @Param("name") String name);

    @Query(value = "SELECT new com.github.angel.dto.ProductDTO(p.productId, p.name, p.price, p.stock, p.description, p.categoryId) FROM Product p", countQuery = "SELECT COUNT(p.productId) FROM Product p")
    Page<ProductDTO> findAllDtosPage(Pageable pageable);

    @Modifying
    @Query("UPDATE Product p SET p.stock = :newStock WHERE p.productId = :productId")
    void updateStock(@Param("productId") Long productId, @Param("newStock") int newStock);

    @Query("SELECT NEW com.github.angel.dto.ProductStockDTO(p.productId, p.stock, p.price) FROM Product p WHERE p.productId =:productId")
    Optional<ProductStockDTO> findProductStocByProductId(@Param("productId") Long productId);

    @Query("SELECT new com.github.angel.dto.ProductDTO(p.productId, p.name) FROM Product p")
    List<ProductDTO> findAllName();
}
