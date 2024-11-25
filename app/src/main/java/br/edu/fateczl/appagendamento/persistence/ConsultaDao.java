package br.edu.fateczl.appagendamento.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.edu.fateczl.appagendamento.model.Consulta;

public class ConsultaDao implements IConsultaDao, ICRUDDao<Consulta> {

    private final Context context;

    private GenericDao gDao;

    private SQLiteDatabase database;

    public ConsultaDao(Context context){
        this.context = context;
    }

    @Override
    public ConsultaDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }
    

    @Override
    public void insert(Consulta consulta) throws SQLException {
        ContentValues contentValues = getContentValues(consulta);
        database.insert("consulta", null, contentValues);
    }


    @Override
    public int update(Consulta consulta) throws SQLException {
        ContentValues contentValues = getContentValues(consulta);
        int ret = database.update("consulta", contentValues, "numero = " +
                consulta.getNumero(), null);
        return ret;
    }

    @Override
    public void delete(Consulta consulta) throws SQLException {
        database.delete("consulta","numero = " +
                consulta.getNumero(), null);
    }

    @SuppressLint("Range")
    @Override
    public Consulta findOne(Consulta consulta) throws SQLException {
        String sql = "SELECT numero, especialidade, data FROM consulta WHERE numero = " + consulta.getNumero();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        if(!cursor.isAfterLast()){
            consulta.setNumero(cursor.getInt(cursor.getColumnIndex("numero")));
            consulta.setEspecialidade(cursor.getString(cursor.getColumnIndex("especialidade")));
            long dataTimestamp = cursor.getLong(cursor.getColumnIndex("data"));
            consulta.setData(new Date(dataTimestamp));
        }
        cursor.close();
        return consulta;
    }

    @SuppressLint("Range")
    @Override
    public List<Consulta> findAll() throws SQLException {
        List<Consulta>  consultas = new ArrayList<>();
        String sql = "SELECT numero, especialidade, data FROM consulta";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
       while(!cursor.isAfterLast()){
           Consulta consulta = new Consulta();
            consulta.setNumero(cursor.getInt(cursor.getColumnIndex("numero")));
            consulta.setEspecialidade(cursor.getString(cursor.getColumnIndex("especialidade")));
            long dataTimestamp = cursor.getLong(cursor.getColumnIndex("data"));
            consulta.setData(new Date(dataTimestamp));

            consultas.add(consulta);
            cursor.moveToNext();

        }
        cursor.close();
        return consultas;
    }

    private static ContentValues getContentValues(Consulta consulta) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("numero", consulta.getNumero());
        contentValues.put("especialidade", consulta.getEspecialidade());
        contentValues.put("data", String.valueOf(consulta.getData()));

        return contentValues;
    }


}
