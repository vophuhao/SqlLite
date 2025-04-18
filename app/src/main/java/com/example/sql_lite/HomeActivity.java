package com.example.sql_lite;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import  com.example.sql_lite.databinding.ActivityDatabindingBinding;
import  com.example.sql_lite.databinding.ActivityHomeBinding;
public class HomeActivity extends AppCompatActivity {

    public ObservableField<String> title= new ObservableField<>();
    private ListUserAdapter listUserAdapter;
    private ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView( this, R.layout.activity_home);
        title.set("Ví dụ về DataBinding cho RecycleView");
        binding.setHome(this);
        setData();
        binding.rcView.setLayoutManager(new LinearLayoutManager(this));
        binding.rcView.setAdapter(listUserAdapter);
    }
    private void setData() {
        List<User> userList = new ArrayList<>();
        for (int i=0;i<10;i++){
            User user = new User();
            user.setFirstName("Hữu" + 1);
            user.setLastName("Trung" + 1);
            userList.add(user);
        }
        listUserAdapter = new ListUserAdapter(userList);
    }
}