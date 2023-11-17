package com.experis.course.springlibrary.service;

import com.experis.course.springlibrary.exceptions.CategoryNameUniqueException;
import com.experis.course.springlibrary.model.Category;
import com.experis.course.springlibrary.repository.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  public List<Category> getAll() {
    return categoryRepository.findByOrderByName();
  }

  public Category save(Category category) throws CategoryNameUniqueException {
    // verifico che questo nome non esista già
    if (categoryRepository.existsByName(category.getName())) {
      throw new CategoryNameUniqueException(category.getName());
    }
    // trasformo il nome in lowercase
    category.setName(category.getName().toLowerCase());
    // salvo su database
    return categoryRepository.save(category);
  }
}
