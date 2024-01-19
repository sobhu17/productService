package dev.saurabh.productservice.repository;

import dev.saurabh.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product , UUID> {

    Page<Product> findAllByTitleContaining(String title , Pageable pageable);
}
