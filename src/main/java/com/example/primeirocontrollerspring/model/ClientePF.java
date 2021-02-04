package com.example.primeirocontrollerspring.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "tb_clientepf")
@PrimaryKeyJoinColumn(name = "id_cliente")
public class ClientePF extends Cliente {

    @NotBlank
    private String nome;
    @NotBlank
    private String cpf;

    @OneToMany(mappedBy = "clientePF")
    private List<Venda> vendas = new ArrayList();

    @OneToOne
    private Usuario usuario;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
