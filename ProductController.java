package com.ecommerce.controller;

import java.util.List;

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
import com.ecommerce.MobileBack.dao.ProductDao;
import com.ecommerce.MobileBack.dao.SupplierDao;
import com.ecommerce.MobileBack.model.Category;
import com.ecommerce.MobileBack.model.Product;
import com.ecommerce.MobileBack.model.Supplier;


@Controller
public class ProductController {
	@Autowired
	ProductDao productdao;
	@Autowired
	CategoryDao categorydao;

	@Autowired
	SupplierDao supplierdao;

	@RequestMapping("/adminProduct")
	public ModelAndView showProduct() {
		ModelAndView mv = new ModelAndView("adminProduct");
		List<Product> ls = productdao.list();

		mv.addObject("prdadmin", new Product());
		mv.addObject("catadmin", new Category());
		mv.addObject("supadmin", new Supplier());
		mv.addObject("products", productdao.list());
		mv.addObject("categories", categorydao.list());
		mv.addObject("suppliers", supplierdao.list());
		return mv;
	}

	@RequestMapping(value = "/prdSubmit", method = RequestMethod.POST)
	public String submit(@ModelAttribute("prdadmin") Product product)

	{
		System.out.println("inside submit method");
		System.out.println(product.getProductId());
		System.out.println(product.getProductName());
		Category category = categorydao.getByName(product.getCategory().getName());
		Supplier supplier = supplierdao.getByName(product.getSupplier().getName());
		product.setCategory(category);
		product.setSupplier(supplier);
		product.setCategory_id(category.getCategoryId());
		product.setSupplier_id(supplier.getSupplierId());
		productdao.saveOrUpdate(product);
		return "redirect:/adminProduct";
	}

	@RequestMapping("editProduct/{productId}")
	public String editProduct(@PathVariable("productId") String productId, Model model) {

		System.out.println(productId);

		model.addAttribute("prdadmin", productdao.getPrdname(productId));
		model.addAttribute("products", this.productdao.list());
		model.addAttribute("categories", this.categorydao.list());
		model.addAttribute("suppliers", this.supplierdao.list());
		model.addAttribute("isProductClicked", true);
		return "adminProduct";
	}
	
	@RequestMapping("delete/{productId}")
	public String delete(@PathVariable("productId") String productId,Model model)throws Exception {
		try {
			productdao.delete(productId);
			model.addAttribute("message", "Successfully Added");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
		}

		
		return  "redirect:/adminProduct";
	}

}
