package dev.saurabh.productservice.repository;

import dev.saurabh.productservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category , UUID> {

    Category findCategoryByName(String name);
}
