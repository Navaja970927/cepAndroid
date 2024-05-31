package com.example.newceps.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "cep.db";

    public static final String TABLE_CEP = "cep_table";

    // Table columns
    public static final String COLUMN_CEP = "cep";
    public static final String COLUMN_LOGARDOURO = "logardouro";
    public static final String COLUMN_COMPLEMENTO = "complemento";
    public static final String COLUMN_BAIRRO = "bairro";
    public static final String COLUMN_LOCALIDADE = "localidade";
    public static final String COLUMN_UF = "uf";
    public static final String COLUMN_IBGE = "ibge";
    public static final String COLUMN_GIA = "gia";
    public static final String COLUMN_DDD = "ddd";
    public static final String COLUMN_SIAFI = "siafi";

    // Create table SQL query
    private static final String CREATE_TABLE_CEP =
            "CREATE TABLE " + TABLE_CEP + "("
                    + COLUMN_CEP + " TEXT PRIMARY KEY,"
                    + COLUMN_LOGARDOURO + " TEXT,"
                    + COLUMN_COMPLEMENTO + " TEXT,"
                    + COLUMN_BAIRRO + " TEXT,"
                    + COLUMN_LOCALIDADE + " TEXT,"
                    + COLUMN_UF + " TEXT,"
                    + COLUMN_IBGE + " TEXT,"
                    + COLUMN_GIA + " TEXT,"
                    + COLUMN_DDD + " TEXT,"
                    + COLUMN_SIAFI + " TEXT"
                    + ")";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CEP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CEP);
        onCreate(db);
    }
}
