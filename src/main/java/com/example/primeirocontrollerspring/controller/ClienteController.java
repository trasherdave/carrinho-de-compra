package com.example.primeirocontrollerspring.controller;

import com.example.primeirocontrollerspring.model.ClientePF;
import com.example.primeirocontrollerspring.model.Role;
import com.example.primeirocontrollerspring.model.Usuario;
import com.example.primeirocontrollerspring.model.dao.ClienteDAO;
import com.example.primeirocontrollerspring.model.dao.RoleDao;
import com.example.primeirocontrollerspring.model.dao.UsuarioDAO;
import com.example.primeirocontrollerspring.security.WebSecurityConfig;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    ClienteDAO clienteDao;
    @Autowired
    UsuarioDAO usuarioDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    WebSecurityConfig secure;

    @GetMapping("/listClientes")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("clientepf", clienteDao.clientesPF());
        model.addAttribute("clientepj", clienteDao.clientesPJ());
        return new ModelAndView("/cliente/listClientes", model);
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastrar(ClientePF cliente, ModelMap model) {
        model.addAttribute("clientepf", clienteDao.clientesPF());
        return new ModelAndView("/cliente/cadastro", model);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid ClientePF cliente, BindingResult result, ModelMap model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return cadastrar(cliente, model);
        }

        Role r = roleDao.role("ROLE_EDITOR");

        Usuario u = new Usuario();
        u.setLogin(cliente.getNome());
        if (cliente.getUsuario().getPassword() == null || cliente.getUsuario().getPassword() == "") {
            attributes.addFlashAttribute("erro", "Senha é obrigatória!");
            return new ModelAndView("redirect:/cliente/cadastro");
        }
        u.setPassword(secure.passwordEncoder().encode(cliente.getUsuario().getPassword()));
        u.getRoles().add(r);

        usuarioDao.save(u);
        cliente.setUsuario(u);

        clienteDao.save(cliente);
        return new ModelAndView("login/login");
    }
}
