package com.experis.course.springlibrary.service;

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
}
