package br.edu.fateczl.appagendamento.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GenericDao extends SQLiteOpenHelper {

    private static final String DATABASE = "AGENDAMENTO.DB";
    
    private static final int DATABASE_VER = 1;
    
    private static final String CREATE_TABLE_CONS =
            "CREATE TABLE consulta( " +
                    "numero INT NOT NULL PRIMARY KEY, " +
                    "especialidade VARCHAR(10) NOT NULL, " +
                    "data DATE NOT NULL);";
    private static final String CREATE_TABLE_EX =
            "CREATE TABLE exame( " +
                    "numero INT NOT NULL PRIMARY KEY, " +
                    "tipo_exame VARCHAR(30) NOT NULL, " +
                    "data DATE NOT NULL);";


    public GenericDao(Context context){
        super(context, DATABASE, null, DATABASE_VER );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CONS);
        sqLiteDatabase.execSQL(CREATE_TABLE_EX);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int antigaVersao, int novaVersao) {
        if(novaVersao > antigaVersao){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS consulta");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS exame");
            onCreate(sqLiteDatabase);
        }

    }
}
