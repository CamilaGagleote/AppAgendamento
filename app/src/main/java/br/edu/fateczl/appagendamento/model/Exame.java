package br.edu.fateczl.appagendamento.model;

import java.util.Date;

public class Exame {

    private int numero;

    private String tipo_exame;

    private Date data;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTipoExame() {
        return tipo_exame;
    }

    public void setTipoExame(String tipo_exame) {
        this.tipo_exame = tipo_exame;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String toString(){
        return numero + " - " + tipo_exame + " " + getData();
    }

}
