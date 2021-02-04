package com.example.primeirocontrollerspring.model.dao;

import com.example.primeirocontrollerspring.model.Produto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Produto> produto() {
        Query query = em.createQuery("from Produto");
        return query.getResultList();
    }

    public Produto produto(Long id) {
        return em.find(Produto.class, id);
    }

    public void saveProduto(Produto produto) {
        em.merge(produto);
    }

}
