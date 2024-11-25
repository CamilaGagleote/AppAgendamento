package br.edu.fateczl.appagendamento.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.edu.fateczl.appagendamento.model.Exame;
import br.edu.fateczl.appagendamento.persistence.ConsultaDao;
import br.edu.fateczl.appagendamento.persistence.ExameDao;

public class ExameController implements IController<Exame>{

    private final ExameDao eDao;

    public ExameController(ExameDao eDao){
        this.eDao = eDao;
    }
    @Override
    public void agendar(Exame exame) throws SQLException {
        if (exame.getData() == null) {
            throw new IllegalArgumentException("A data da consulta não pode ser nula.");
        }
        Date dataAtual = new Date();
        if (exame.getData().before(dataAtual)) {
            throw new IllegalArgumentException("A data da consulta não pode ser no passado.");
        }
        if (eDao.open() == null) {
            eDao.open();
        }
        eDao.insert(exame);
        eDao.close();
    }

    @Override
    public void modificar(Exame exame) throws SQLException {
        if(eDao.open() == null){
            eDao.open();
        }
        eDao.update(exame);
        eDao.close();
    }

    @Override
    public void deletar(Exame exame) throws SQLException {
        if(eDao.open() == null){
            eDao.open();
        }
        eDao.delete(exame);
        eDao.close();
    }

    @Override
    public Exame buscar(Exame exame) throws SQLException {
        if(eDao.open() == null){
            eDao.open();
        }
        return eDao.findOne(exame);
    }

    @Override
    public List<Exame> listar() throws SQLException {
        if(eDao.open() == null){
            eDao.open();
        }
       return eDao.findAll();
    }
}
