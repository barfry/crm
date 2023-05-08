package com.coderslab.crm.service;

import com.coderslab.crm.model.Category;
import com.coderslab.crm.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category addNewCategory(Category category){
        return  categoryRepository.save(category);
    }

    public Category getCategoryById(Long categoryId){
        return categoryRepository.getById(categoryId);
    }

    public Category editCategory(Category category){
        return categoryRepository.save(category);
    }

}
