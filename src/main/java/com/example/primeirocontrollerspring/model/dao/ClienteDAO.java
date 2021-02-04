package com.example.primeirocontrollerspring.model.dao;

import com.example.primeirocontrollerspring.model.Cliente;
import com.example.primeirocontrollerspring.model.ClientePF;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author David
 */
@Repository
public class ClienteDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Cliente> clientes() {
        Query query = em.createQuery("from Cliente");
        return query.getResultList();
    }

    public List<Cliente> clientesPF() {
        Query query = em.createQuery("from ClientePF");
        return query.getResultList();
    }

    public List<Cliente> clientesPJ() {
        Query query = em.createQuery("from ClientePJ");
        return query.getResultList();
    }

    public void save(ClientePF cliente) {
        em.persist(cliente);
    }

    public ClientePF listPFNome(String nome) {
        String hql = "from ClientePF c where c.nome = :nome";
        TypedQuery<ClientePF> query = em.createQuery(hql, ClientePF.class);
        query.setParameter("nome", nome);
        return query.getSingleResult();
    }

}
