package com.example.sql_lite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sql_lite.db.DatabaseHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    ListView listView;
    ArrayList<NotesModel> arrayNotes;
    NotesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.listView);
        arrayNotes = new ArrayList<>();
        adapter = new NotesAdapter(MainActivity.this,R.layout.view_item, arrayNotes);
        listView.setAdapter(adapter);
        InitDBSQLite();
        createDatabaseSQLite();
        databaseSQLite();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAddNotes){
            AddNote();
        }
        return true;
    }
    private void AddNote(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_note);
        //Anh xa trong dialog
        EditText editText = (EditText) dialog.findViewById(R.id.edtNote);
        Button addBtn = (Button) dialog.findViewById(R.id.btnUpdate);
        Button cancelBtn = (Button) dialog.findViewById(R.id.cancel_btn);

        //Bat su kien cho nut add
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString().trim();
                if(name.equals("")){
                    Toast.makeText(MainActivity.this, "Vui long nhap ten note!", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseHandler.QueryData("Insert into Notes Values(null, '"+name+"')");
                    Toast.makeText(MainActivity.this,"Da them note", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    databaseSQLite();
                }
            }
        });
        //Bat sư kien cho nut huy
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void UpdateNote(String name, int id){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_note);

        //Anh xạ
        EditText editText = dialog.findViewById(R.id.edtNote);
        Button cancleBtn = dialog.findViewById(R.id.cancel_btn);
        Button updateBtn = dialog.findViewById(R.id.btnUpdate);
        editText.setText(name);

        //Bat su kien edit
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(name.trim().equals("")){
                        Toast.makeText(MainActivity.this, "Vui long nhap ten", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String query = "Update Notes Set NameNotes = '"+editText.getText().toString().trim() +"' Where Id = " +id;
                        databaseHandler.QueryData(query);
                        Toast.makeText(MainActivity.this, "Da thay doi thanh cong", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        databaseSQLite();
                    }
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        //Bat sư kien huy
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //Dialog delete
    public void DeleteNote(String name, final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa Notes "+ name + "này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String query = "Delete From Notes Where Id = "+id;
                databaseHandler.QueryData(query);
                Toast.makeText(MainActivity.this, "Da xoa thanh cong", Toast.LENGTH_SHORT).show();
                databaseSQLite();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    //Khoi tao database
    private void InitDBSQLite(){
        databaseHandler = new DatabaseHandler(this,"notes.sqlite",null,1);
        if(databaseHandler != null){
            databaseHandler.QueryData("CREATE TABLE IF NOT EXISTS Notes(Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes VARCHAR(200))");
        }
    }
    //Them gia tri
    private void createDatabaseSQLite(){
        databaseHandler.QueryData("Insert into Notes Values (null,'Vi du 1 SQLite')");
        databaseHandler.QueryData("Insert into Notes Values (null,'Vi du 2 SQLite')");
        databaseHandler.QueryData("Insert into Notes Values (null,'Vi du 3 SQLite')");
    }
    //Lay gia tri
    private void databaseSQLite(){
        arrayNotes.clear();
        Cursor cursor = databaseHandler.GetData("Select * From Notes");
        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            arrayNotes.add(new NotesModel(id,name));
            //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}