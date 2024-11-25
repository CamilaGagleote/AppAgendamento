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
import br.edu.fateczl.appagendamento.model.Exame;

public class ExameDao implements IExameDao, ICRUDDao<Exame>{

    private final Context context;

    private GenericDao gDao;

    private SQLiteDatabase database;


    public ExameDao(Context context){
        this.context = context;
    }
    @Override
    public ExameDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Exame exame) throws SQLException {
        ContentValues contentValues = getContentValues(exame);
        database.insert("exame", null, contentValues);
    }

    @Override
    public int update(Exame exame) throws SQLException {
        ContentValues contentValues = getContentValues(exame);
        int ret = database.update("exame", contentValues, "numero = " +
                exame.getNumero(), null);
        return ret;
    }

    @Override
    public void delete(Exame exame) throws SQLException {
        database.delete("exame","numero = " +
               exame.getNumero(), null);
    }

    @SuppressLint("Range")
    @Override
    public Exame findOne(Exame exame) throws SQLException {
        String sql = "SELECT numero, tipo_exame, data FROM consulta WHERE numero = " + exame.getNumero();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        if(!cursor.isAfterLast()){
            exame.setNumero(cursor.getInt(cursor.getColumnIndex("numero")));
            exame.setTipoExame(cursor.getString(cursor.getColumnIndex("tipo_exame")));
            long dataTimestamp = cursor.getLong(cursor.getColumnIndex("data"));
            exame.setData(new Date(dataTimestamp));
        }
        cursor.close();
        return exame;
    }

    @SuppressLint("Range")
    @Override
    public List<Exame> findAll() throws SQLException {
        List<Exame>  exames = new ArrayList<>();
        String sql = "SELECT numero, tipo_exame, data FROM exame";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            Exame exame = new Exame();
            exame.setNumero(cursor.getInt(cursor.getColumnIndex("numero")));
            exame.setTipoExame(cursor.getString(cursor.getColumnIndex("tipo_exame")));
            long dataTimestamp = cursor.getLong(cursor.getColumnIndex("data"));
            exame.setData(new Date(dataTimestamp));

            exames.add(exame);
            cursor.moveToNext();

        }
        cursor.close();
        return exames;
    }

    private static ContentValues getContentValues(Exame exame) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("numero", exame.getNumero());
        contentValues.put("tipo_exame", exame.getTipoExame());
        contentValues.put("data", String.valueOf(exame.getData()));

        return contentValues;
    }


}

