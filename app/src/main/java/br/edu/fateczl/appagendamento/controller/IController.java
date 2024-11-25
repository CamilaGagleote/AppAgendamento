package br.edu.fateczl.appagendamento.controller;

import java.sql.SQLException;
import java.util.List;

public interface IController <T>{

    public void agendar(T t ) throws SQLException;
    public void modificar(T t) throws SQLException;
    public void deletar(T t) throws SQLException;
    public T buscar(T t) throws SQLException;
    public List<T> listar () throws SQLException;
}
