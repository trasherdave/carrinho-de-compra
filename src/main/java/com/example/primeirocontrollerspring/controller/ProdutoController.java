/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.primeirocontrollerspring.controller;

import com.example.primeirocontrollerspring.model.Produto;
import com.example.primeirocontrollerspring.model.dao.ProdutoDAO;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author David
 */
@Scope("request")
@Transactional
@Controller
@RequestMapping("produto")
public class ProdutoController {

    @Autowired
    ProdutoDAO produtoDao;

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Produto produto, ModelMap model) {
        model.addAttribute("produtos", produtoDao.produto());
        return new ModelAndView("/produto/cadastro", model);

    }

    @GetMapping("/addProduto")
    public ModelAndView addProduto(@Valid Produto produto, BindingResult result, ModelMap model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastro(produto, model);
        }
        attributes.addFlashAttribute("save", "Produto cadastrado com sucesso!");
        produtoDao.saveProduto(produto);
        return new ModelAndView("redirect:/produto/cadastro");
    }

}
