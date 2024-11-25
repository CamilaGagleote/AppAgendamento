package br.edu.fateczl.appagendamento.model;

import java.util.Date;

public class Consulta {

    private int numero;

    private String especialidade;

    private Date data;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String toString(){
        return numero + " - " + especialidade + " " + getData();
    }

}
