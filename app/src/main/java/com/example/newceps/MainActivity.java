package com.example.newceps;

import static java.util.Objects.nonNull;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.newceps.db.DbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private DbHelper dbHelper;
    private Button btnSearch;
    private ListView listView;
    ArrayList<String> ceps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        btnSearch = (Button) findViewById(R.id.cepSearch);

        dbHelper = new DbHelper(this);
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DbHelper.TABLE_CEP,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int cepIndex = cursor.getColumnIndex(DbHelper.COLUMN_CEP);
                String cepDB = cursor.getString(cepIndex);
                ceps.add(cepDB);
            }
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ceps);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchCep = new Intent(MainActivity.this, AddCepActivity.class);
                startActivity(searchCep);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}