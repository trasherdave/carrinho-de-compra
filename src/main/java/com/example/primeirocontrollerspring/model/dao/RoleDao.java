/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.primeirocontrollerspring.model.dao;

import com.example.primeirocontrollerspring.model.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author David
 */
@Repository
public class RoleDao {

    @PersistenceContext
    private EntityManager em;

    public Role role(String nome) {
        return em.find(Role.class, nome);
    }

    public void save(Role role) {
        em.persist(role);

    }
}
