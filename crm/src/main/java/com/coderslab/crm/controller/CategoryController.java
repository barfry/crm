package com.coderslab.crm.controller;

import com.coderslab.crm.model.Category;
import com.coderslab.crm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    String showAllCategoriesPage(Model model){
        model.addAttribute("categories", categoryService.getAllCategories());
        return "user-zone/all-categories";
    }

    @GetMapping("/add-new-category")
    String initAddNewCategoryForm(Model model){
        model.addAttribute("category", new Category());
        return "user-zone/add-new-category";
    }

    @PostMapping("/add-new-category")
    String addNewCategory(@Valid Category category, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("category", category);
            return "user-zone/add-new-category";
        }
        categoryService.addNewCategory(category);

        return "redirect:";
    }

    @GetMapping("/edit-category")
    String initEditCategoryForm(@RequestParam(name = "categoryId") Long categoryId, Model model){
        model.addAttribute("category", categoryService.getCategoryById(categoryId));

        return "user-zone/edit-category";
    }

    @PostMapping("/edit-category")
    String editCategory(@Valid Category category, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("category", category);
            return "user-zone/edit-category";
        }

            categoryService.editCategory(category);

        return "redirect:";
    }
}
