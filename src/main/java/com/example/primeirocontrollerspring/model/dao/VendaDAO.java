package com.example.primeirocontrollerspring.model.dao;

import com.example.primeirocontrollerspring.model.Venda;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class VendaDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Venda> venda() {
        Query query = em.createQuery("from Venda");
        return query.getResultList();
    }

    public void save(Venda venda) {
        em.persist(venda);

    }

    public List<Venda> vendasUser(String nome) {
        String hql = "from Venda v where v.clientePF.nome = :nome";
        TypedQuery<Venda> query = em.createQuery(hql, Venda.class);
        query.setParameter("nome", nome);
        return query.getResultList();
    }

}
