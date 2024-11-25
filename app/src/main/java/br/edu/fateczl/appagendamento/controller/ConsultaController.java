package br.edu.fateczl.appagendamento.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.edu.fateczl.appagendamento.model.Consulta;
import br.edu.fateczl.appagendamento.persistence.ConsultaDao;

public class ConsultaController implements IController<Consulta>{

    private final ConsultaDao cDao;

    public ConsultaController(ConsultaDao cDao){

        this.cDao = cDao;
    }
    @Override
    public void agendar(Consulta consulta) throws SQLException {
            if (consulta.getData() == null) {
                throw new IllegalArgumentException("A data da consulta não pode ser nula.");
            }
            Date dataAtual = new Date();
            if (consulta.getData().before(dataAtual)) {
                throw new IllegalArgumentException("A data da consulta não pode ser no passado.");
            }
            if (cDao.open() == null) {
                cDao.open();
            }
            cDao.insert(consulta);
            cDao.close();

    }

    @Override
    public void modificar(Consulta consulta) throws SQLException {
        if(cDao.open() == null){
            cDao.open();
        }
        cDao.update(consulta);
        cDao.close();
    }

    @Override
    public void deletar(Consulta consulta) throws SQLException {
        if(cDao.open() == null){
            cDao.open();
        }
        cDao.delete(consulta);
        cDao.close();
    }

    @Override
    public Consulta buscar(Consulta consulta) throws SQLException {
        if(cDao.open() == null){
            cDao.open();
        }
        return cDao.findOne(consulta);
    }

    @Override
    public List<Consulta> listar() throws SQLException {
        if(cDao.open() == null){
            cDao.open();
        }
        return cDao.findAll();
    }
}
