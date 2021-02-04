/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.primeirocontrollerspring.model.dao;

import com.example.primeirocontrollerspring.model.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author David
 */
@Repository

public class UsuarioDAO {

    @PersistenceContext
    private EntityManager em;

    public Usuario usuario(String login) {
        return em.find(Usuario.class, login);
    }

    public void save(Usuario usuario) {
        em.persist(usuario);

    }

}
