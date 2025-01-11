package br.com.magnumscd.www.api_1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FipeCarros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    private String anos;
    private String modelos;
    private String marcas;
    private String observacoes;
    private String veiculos;

    public String getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(String veiculos) {
        this.veiculos = veiculos;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getAnos() {
        return anos;
    }

    public void setAnos(String anos) {
        this.anos = anos;
    }

    public String getModelos() {
        return modelos;
    }

    public void setModelos(String modelos) {
        this.modelos = modelos;
    }

    public String getMarcas() {
        return marcas;
    }

    public void setMarcas(String marcas) {
        this.marcas = marcas;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}


