package br.edu.fateczl.appagendamento.persistence;

import java.sql.SQLException;

public interface IExameDao {
    public ExameDao open() throws SQLException;

    public void close();

}
