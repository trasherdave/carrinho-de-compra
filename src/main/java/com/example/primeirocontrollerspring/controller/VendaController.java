package com.example.primeirocontrollerspring.controller;

import com.example.primeirocontrollerspring.model.ClientePF;
import com.example.primeirocontrollerspring.model.dao.VendaDAO;
import com.example.primeirocontrollerspring.model.Produto;
import com.example.primeirocontrollerspring.model.Venda;
import com.example.primeirocontrollerspring.model.ItemVenda;
import com.example.primeirocontrollerspring.model.dao.ClienteDAO;
import com.example.primeirocontrollerspring.model.dao.ProdutoDAO;
import java.security.Principal;
import java.time.LocalDate;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author David
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Scope("request")
@Transactional
@Controller
@RequestMapping("venda")
public class VendaController {

    @Autowired
    VendaDAO dao;

    @Autowired
    Venda venda;
    @Autowired
    ProdutoDAO produtoDao;
    @Autowired
    ClienteDAO clienteDAO;

    @ResponseBody
    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("venda", dao.venda());
        return new ModelAndView("/venda/list", model);
    }

    @ResponseBody
    @GetMapping("/listUser")
    public ModelAndView listUser(ModelMap model, Principal principal) {
        model.addAttribute("venda", dao.vendasUser(principal.getName()));
        return new ModelAndView("/venda/list", model);
    }

    @GetMapping("/index")
    public ModelAndView Inicio() {
        return new ModelAndView("/venda/index");
    }

    @GetMapping("/form")
    public ModelAndView form(ItemVenda itemVenda, ModelMap model) {
        model.addAttribute("venda", this.venda);
        model.addAttribute("produtos", produtoDao.produto());

        return new ModelAndView("/venda/form", model);
    }

    @GetMapping("/removeItem/{id}")
    public ModelAndView remove(@PathVariable("id") int id) {
        venda.getItemVenda().remove(id);
        return new ModelAndView("redirect:/venda/form");
    }

    @PostMapping("/addItem")
    public ModelAndView addItem(@Valid ItemVenda itemVenda, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return form(itemVenda, model);
        }

        Produto p = produtoDao.produto(itemVenda.getProduto().getId());
        itemVenda.setProduto(p);
        itemVenda.setVenda(this.venda);
        this.venda.getItemVenda().add(itemVenda);

        return new ModelAndView("redirect:/venda/form", model);
    }

    @GetMapping("/save")
    public ModelAndView save(HttpServletRequest request, Principal principal, RedirectAttributes attributes) {

        ClientePF clientePF = clienteDAO.listPFNome(principal.getName());

        if (venda.itemVendas.isEmpty()) {
            attributes.addFlashAttribute("erro", "Você não possui itens no seu carrinho!");
            return new ModelAndView("redirect:/venda/form");
        }
        this.venda.setClientePF(clientePF);
        this.venda.setId(0);
        this.venda.setData(LocalDate.now());
        dao.save(this.venda);
        this.venda.getItemVenda().clear();
        attributes.addFlashAttribute("save", "Compra efetuada com sucesso!");
        return new ModelAndView("/venda/listUser");

    }

}
