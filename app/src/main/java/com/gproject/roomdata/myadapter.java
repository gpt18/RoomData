package com.gproject.roomdata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

//step2
public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {

    //step3
    List<User> users;

    //step4
    public myadapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.rv_id.setText(String.valueOf(users.get(position).getUid()));
        holder.rv_first.setText(users.get(position).getFirstName());
        holder.rv_last.setText(users.get(position).getLastName());

        holder.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase db = Room.databaseBuilder(holder.rv_id.getContext(),
                        AppDatabase.class, "room_db").allowMainThreadQueries().build();
                UserDao userDao = db.userDao();

                //delete from roomdb
                userDao.deleteById(users.get(position).getUid());

                //delete form arraylist
                users.remove(position);

                //update arraylist
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        //step5
        return users.size();
    }
//step1
    class myviewholder extends RecyclerView.ViewHolder{

        TextView rv_id, rv_first, rv_last;
        ImageView clear;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            rv_id = itemView.findViewById(R.id.tv1);
            rv_first = itemView.findViewById(R.id.tv2);
            rv_last = itemView.findViewById(R.id.tv3);
            clear = itemView.findViewById(R.id.clear);

        }
    }

}
