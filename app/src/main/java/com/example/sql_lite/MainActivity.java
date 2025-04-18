package com.example.sql_lite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


import com.example.sql_lite.db.DatabaseHandler;

import com.example.sql_lite.DataBindingActivity;
import com.example.sql_lite.HomeActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    ListView listView;
    ArrayList<NotesModel> arrayList;
    Button btn_databing,sharePre;
    NotesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        InitDatabaseSQLite();
        //createDatabaseSqlite();
        listView = (ListView) findViewById(R.id.listview1);
        arrayList = new ArrayList<>();
        adapter = new NotesAdapter(this, R.layout.list_item, arrayList);
        listView.setAdapter(adapter);
        databaseSQLite();
        sharePre =findViewById(R.id.sharePre);
        btn_databing = findViewById(R.id.btn_dataBinding);
        btn_databing.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        });
        sharePre.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, com.example.sql_lite.sharedpreferences.MainActivity.class);
            startActivity(intent);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAddNotes)
        {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }
    private void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_note);
        //Anhs xa trong dialog
        EditText editText = dialog.findViewById(R.id.editText);
        Button btnAdd = dialog.findViewById(R.id.btn_add);
        Button btnHuy = dialog.findViewById(R.id.btn_exit);
        //Bat su kien nut add
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString().trim();
                if(name.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên Notes", Toast.LENGTH_SHORT).show();

                }else{
                    databaseHandler.QuerryData("INSERT INTO Notes VALUES(null, '"+name+"')");
                    Toast.makeText(MainActivity.this, "Đã thêm Notes", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    databaseSQLite();
                }
            }
        });

        //Bat su kien nut huy
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void createDatabaseSqlite(){
        databaseHandler.QuerryData("INSERT INTO Notes VALUES(null, 'Ví dụ SQLite 1')");
        databaseHandler.QuerryData("INSERT INTO Notes VALUES(null, 'Ví dụ SQLite 2')");
        databaseHandler.QuerryData("INSERT INTO Notes VALUES(null, 'Ví dụ SQLite 3')");
        databaseHandler.QuerryData("INSERT INTO Notes VALUES(null, 'Ví dụ SQLite 4')");
        databaseHandler.QuerryData("INSERT INTO Notes VALUES(null, 'Ví dụ SQLite 5')");

    }
    private void InitDatabaseSQLite(){
        //khoi tao database
        databaseHandler = new DatabaseHandler(this, "notes.sqlite", null, 1);
        //Tao bang note
        databaseHandler.QuerryData("CREATE TABLE IF NOT EXISTS Notes(Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes VARCHAR(200))");
    }
    private void databaseSQLite(){
        arrayList.clear();
        Cursor cursor = databaseHandler.GetData("Select * from Notes");
        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            arrayList.add(new NotesModel(id, name));
            //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
    public void DialogCapNhatNote(String name, int id){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_note);
        EditText editText = dialog.findViewById(R.id.edtCapNhat);
        Button btnCapNhat = dialog.findViewById(R.id.btn_edit);
        Button btnHuy = dialog.findViewById(R.id.btn_exit);
        editText.setText(name);
        //Bat su kien nut cap nhat
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString().trim();
                databaseHandler.QuerryData("UPDATE Notes SET NameNotes = '"+name+"' WHERE Id = '"+id+"'");
                Toast.makeText(MainActivity.this, "Đã cập nhật notes thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                databaseSQLite();   //Ham load lai data

            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void DialogDelete(String name, final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa Notes" +name+ "này không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                databaseHandler.QuerryData("DELETE FROM Notes WHERE Id = '"+id+"'");
                Toast.makeText(MainActivity.this, "Đã xóa Notes" +name+ "Thành công", Toast.LENGTH_SHORT).show();
                databaseSQLite();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

            }
        });
        builder.show();

    }
}