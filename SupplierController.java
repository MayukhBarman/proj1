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

import com.ecommerce.MobileBack.dao.SupplierDao;
import com.ecommerce.MobileBack.model.Supplier;

@Controller

public class SupplierController {
	@Autowired
	SupplierDao supplierdao;

	@Autowired
	Supplier supplier;
	
	@RequestMapping("/adminSupplier")
	public ModelAndView showSupplier()

	{

		ModelAndView mv = new ModelAndView("adminSupplier");
		mv.addObject("supadmin", supplier);
		mv.addObject("suppliers", supplierdao.list());

		return mv;
	}

	@RequestMapping(value = "/supplierSubmit", method = RequestMethod.POST)
	public String submit(@ModelAttribute("supadmin") Supplier supplier)

	{
		System.out.println(supplier.getSupplierId());

		System.out.println(supplier.getName());
		supplierdao.saveOrUpdate(supplier);

		return "redirect:/adminSupplier";
	}

	@RequestMapping("editSupplier/{supplierId}")
	public String editSupplier(@PathVariable("supplierId") String supplierId, Model model) {

		Supplier s=supplierdao.getByName(supplierId);
		System.out.println(s.getSupplierId());
		System.out.println(s.getName());
		model.addAttribute("supadmin", s);
		model.addAttribute("suppliers", this.supplierdao.list());

		return "adminSupplier";
	}
}
