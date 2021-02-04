/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.primeirocontrollerspring.security;

import com.example.primeirocontrollerspring.model.Usuario;
import com.example.primeirocontrollerspring.model.dao.UsuarioDAO;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author David
 */
@Transactional
@Repository
public class UsuariosDetailsConfig implements UserDetailsService {

    @Autowired
    UsuarioDAO dao;

    @Override
    public UserDetails loadUserByUsername(String login) {

        Usuario usuario = dao.usuario(login);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        return new User(usuario.getLogin(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
    }

}
