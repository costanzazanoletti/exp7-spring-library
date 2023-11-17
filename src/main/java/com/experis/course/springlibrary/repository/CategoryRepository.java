package com.experis.course.springlibrary.repository;

import com.experis.course.springlibrary.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

  List<Category> findByOrderByName();


  boolean existsByName(String name);

}
