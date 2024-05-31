package com.example.newceps;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.newceps.db.DbHelper;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.val;

public class AddCepActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private DbHelper dbHelper;

    private EditText cepText;
    private Button btnSearch;
    private Button btnSave;
    private RequestQueue queque;

    private String url = "https://viacep.com.br/ws/{CEP}/json/";

    private TextView cepValue;
    private TextView logadouroValue;
    private TextView complementoValue;
    private TextView bairroValue;
    private TextView localidadeValue;
    private TextView ufValue;
    private TextView ibgeValue;
    private TextView giaValue;
    private TextView dddValue;
    private TextView siafiValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_cep);

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();

        cepText = findViewById(R.id.cepText);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cep = cepText.getText().toString().trim();
                val newUrl = url.replace("{CEP}", cep);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, newUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String cep = response.getString("cep");
                            String logadouro = response.has("logadouro") ? response.getString("logadouro") : null;
                            String complemento = response.has("complemento") ? response.getString("complemento") : null;
                            String bairro = response.has("bairro") ? response.getString("bairro") : null;
                            String localidade = response.has("localidade") ? response.getString("localidade") : null;
                            String uf = response.has("uf") ? response.getString("uf") : null;
                            String ibge = response.has("ibge") ? response.getString("ibge") : null;
                            String gia = response.has("gia") ? response.getString("gia") : null;
                            String ddd = response.has("ddd") ? response.getString("ddd") : null;
                            String siafi = response.has("siafi") ? response.getString("siafi") : null;

                            cepValue.setText(cep);
                            logadouroValue.setText(logadouro);
                            complementoValue.setText(complemento);
                            bairroValue.setText(bairro);
                            localidadeValue.setText(localidade);
                            ufValue.setText(uf);
                            ibgeValue.setText(ibge);
                            giaValue.setText(gia);
                            dddValue.setText(ddd);
                            siafiValue.setText(siafi);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, volleyError -> {

                });
                queque.add(request);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle parameters = new Bundle();

                ContentValues values = new ContentValues();
                values.put(DbHelper.COLUMN_CEP,  cepValue.getText().toString());
                values.put(DbHelper.COLUMN_LOGARDOURO, logadouroValue.getText().toString());
                values.put(DbHelper.COLUMN_COMPLEMENTO, complementoValue.getText().toString());
                values.put(DbHelper.COLUMN_BAIRRO, bairroValue.getText().toString());
                values.put(DbHelper.COLUMN_LOCALIDADE, localidadeValue.getText().toString());
                values.put(DbHelper.COLUMN_UF, ufValue.getText().toString());
                values.put(DbHelper.COLUMN_IBGE, ibgeValue.getText().toString());
                values.put(DbHelper.COLUMN_GIA, giaValue.getText().toString());
                values.put(DbHelper.COLUMN_DDD, dddValue.getText().toString());
                values.put(DbHelper.COLUMN_SIAFI, siafiValue.getText().toString());

                db.insert(DbHelper.TABLE_CEP, null, values);

                Intent i = new Intent(AddCepActivity.this, MainActivity.class);
                i.putExtras(parameters);
                startActivity(i);
            }
        });

        cepValue = findViewById(R.id.cepValue);
        logadouroValue = findViewById(R.id.logadouroValue);
        complementoValue = findViewById(R.id.complementoValue);
        bairroValue = findViewById(R.id.bairroValue);
        localidadeValue = findViewById(R.id.localidadeValue);
        ufValue = findViewById(R.id.ufValue);
        ibgeValue = findViewById(R.id.ibgeValue);
        giaValue = findViewById(R.id.giaValue);
        dddValue = findViewById(R.id.dddValue);
        siafiValue = findViewById(R.id.siafiValue);

        queque = Volley.newRequestQueue(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}