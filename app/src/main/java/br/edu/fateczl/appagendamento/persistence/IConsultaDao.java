package br.edu.fateczl.appagendamento.persistence;

import java.sql.SQLException;

public interface IConsultaDao {

    public ConsultaDao open() throws SQLException;

    public void close();
}
