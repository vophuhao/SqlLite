package com.example.sql_lite;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.example.sql_lite.databinding.ItemListUserBinding;


public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.MyViewHolder> {
    private List<User> userList;

    public ListUserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout using DataBinding
        ItemListUserBinding itemListUserBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_list_user, parent, false);
        return new MyViewHolder(itemListUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setBinding(userList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ObservableField<String> stt = new ObservableField<>();
        public ObservableField<String> firstName = new ObservableField<>();
        public ObservableField<String> lastName = new ObservableField<>();
        private ItemListUserBinding itemListUserBinding;

        public MyViewHolder(ItemListUserBinding itemView) {
            super(itemView.getRoot());
            this.itemListUserBinding = itemView;
        }

        public void setBinding(User user, int position) {
            /*// Set data directly to the DataBinding object
            itemListUserBinding.setUser(user);  // Assuming you have a `setUser` method in the `itemListUserBinding`
            itemListUserBinding.setPosition(String.valueOf(position)); // If you want to display the position
            itemListUserBinding.executePendingBindings(); // Ensures that the bindings are executed immediately*/

            if(itemListUserBinding.getViewHolder() != null){
                itemListUserBinding.setViewHolder(this);
            }
            stt.set(String.valueOf(position));
            firstName.set(user.getFirstName());
            lastName.set(user.getLastName());
        }
    }
}
