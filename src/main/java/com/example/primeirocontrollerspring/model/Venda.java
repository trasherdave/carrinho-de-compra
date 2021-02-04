package com.example.primeirocontrollerspring.model;

/**
 *
 * @author David
 */
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("session")
@Component
@Entity
@Table(name = "tb_venda")
public class Venda implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private LocalDate data;
    @OneToMany(mappedBy = "venda", cascade = CascadeType.PERSIST)
    public List<ItemVenda> itemVendas = new ArrayList();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_clientePF")
    private ClientePF clientePF;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<ItemVenda> getItemVenda() {
        return itemVendas;
    }

    public void setItemVenda(List<ItemVenda> itemVenda) {
        this.itemVendas = itemVenda;
    }

    public Double getTotal() {
        Double total = 0.0;

        if (!itemVendas.isEmpty()) {
            for (ItemVenda item : itemVendas) {
                total += item.total();
            }
        }
        return total;
    }

    public List<ItemVenda> getItemVendas() {
        return itemVendas;
    }

    public void setItemVendas(List<ItemVenda> itemVendas) {
        this.itemVendas = itemVendas;
    }

    public ClientePF getClientePF() {
        return clientePF;
    }

    public void setClientePF(ClientePF clientePF) {
        this.clientePF = clientePF;
    }
    
    
}
