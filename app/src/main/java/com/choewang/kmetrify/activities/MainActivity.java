package com.choewang.kmetrify.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.choewang.kmetrify.R;
import com.choewang.kmetrify.activities.AddDayActivity;
import com.choewang.kmetrify.persistence.KmetrifyDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private KmetrifyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor daysListCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(view.getContext(), AddDayActivity.class);
               startActivity(intent);
            }
        });

        dbHelper = new KmetrifyDatabaseHelper(this);
        try{
            db = dbHelper.getReadableDatabase();
        }catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "database is unavailable!",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
//        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey("Date")){
            TextView intro = (TextView) findViewById(R.id.textViewIntroduction);
            TextView hello = (TextView) findViewById(R.id.textViewWelcome);
            ListView daysList = (ListView) findViewById(R.id.daysListView);
//            intro.setText(getIntent().getExtras().get("Date").toString());

        daysListCursor = db.rawQuery("SELECT _id, date FROM DAY", null);
        CursorAdapter daysAdapter = new SimpleCursorAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1, daysListCursor, new String[]{"date"}, new int[]{android.R.id.text1});
        daysList.setAdapter(daysAdapter);
        if(!daysAdapter.isEmpty()){
            intro.setVisibility(View.INVISIBLE);
            hello.setVisibility(View.INVISIBLE);
        }
            db.close();
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
