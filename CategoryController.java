package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ecommerce.MobileBack.dao.CategoryDao;
import com.ecommerce.MobileBack.model.Category;


@Controller
public class CategoryController {

	
	@Autowired
	CategoryDao categoryDao;

	@Autowired
	Category category;
	
	public CategoryController()
	{
		
	}
	@RequestMapping("/adminCategory")
	public ModelAndView showCategory()
	{
		ModelAndView mv = new ModelAndView("adminCategory");
		mv.addObject("catadmin", new Category());
		mv.addObject("categories", categoryDao.list());
		return mv;
	}

	@RequestMapping(value = "/categorySubmit", method = RequestMethod.POST)
	public String submit(@ModelAttribute("catadmin") Category category) {

		System.out.println("id:" + category.getCategoryId());

		System.out.println("name:" + category.getName());
		categoryDao.saveorupdate(category);

		return "redirect:/adminCategory";

	}

	@RequestMapping("editCategory/{categoryId}")
	public String editCategory(@PathVariable("categoryId") String categoryId, Model model) {
		
		
		model.addAttribute("catadmin", categoryDao.getByName(categoryId));
		model.addAttribute("categories", this.categoryDao.list());
		// mv.addObject("catadmin",categorydao.getCatname(categoryId));
		// mv.addObject("categories",categorydao.list());
		return "adminCategory";
	}

}

