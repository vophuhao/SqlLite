package com.example.sql_lite;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sql_lite.db.DatabaseHandler;

import java.util.List;

public class NotesAdapter  extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<NotesModel> noteList;
    DatabaseHandler databaseHandler;

    public NotesAdapter(MainActivity context, int layout, List<NotesModel> noteList) {
        this.context = context;
        this.layout = layout;
        this.noteList = noteList;
    }
    private class ViewHolder{
        TextView textViewNote;
        ImageView imgViewEdit;
        ImageView imgViewDelete;
    }
    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int i) {
        return noteList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(layout ,null);
            viewHolder.textViewNote = (TextView) convertView.findViewById(R.id.tv_nameNotes);
            viewHolder.imgViewEdit = (ImageView) convertView.findViewById(R.id.imgEdit_note);
            viewHolder.imgViewDelete =(ImageView) convertView.findViewById(R.id.imgDelete_note);

            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Lay gia tri
        NotesModel notes = noteList.get(position);
        viewHolder.textViewNote.setText(notes.getNameNote());
        //Bat su kien nut cap nhat
        viewHolder.imgViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Cập nhật" +notes.getNameNote(), Toast.LENGTH_SHORT).show();
                //Goi Dialog trong MainActivity.java
                context.DialogCapNhatNote(notes.getNameNote(), notes.getIdNote());
            }
        });

        viewHolder.imgViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogDelete(notes.getNameNote(), notes.getIdNote());
            }
        });

        return convertView;
    }

}