package com.javaschool.onlineshop.Repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaschool.onlineshop.Models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>{

}
