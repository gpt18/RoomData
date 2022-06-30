package com.gproject.roomdata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText t1, t2, t3;
    Button b1, b2;
    TextView lbl, data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        lbl = findViewById(R.id.lbl);
        data = findViewById(R.id.dataholder);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);

        refresh();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                new bgthread().start();
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "room_db").allowMainThreadQueries().build();
                UserDao userDao = db.userDao();
                Boolean check = userDao.is_exist(Integer.parseInt(t3.getText().toString()));

                if(check==false){
                    userDao.insertrecord(new User(Integer.parseInt(t3.getText().toString()),t1.getText().toString(),t2.getText().toString()));
                    t1.setText("");
                    t2.setText("");
                    t3.setText(String.valueOf(Integer.parseInt(t3.getText().toString()) + 1));
                    lbl.setText("Inserted Successfully");
                    refresh();
                }
                else{
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                    lbl.setText("Record is existing");
                }


            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), fetchData.class));

            }
        });
    }

    private void refresh() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "room_db").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();

        List<User> users = userDao.getallusers();
        String str = "";

        for(User user : users)
            str = str+"\t   "+user.getUid()+"   "+user.getFirstName()+"   "+user.getLastName()+"\n\n";

        data.setText(str);
    }

//    class bgthread extends Thread{
//        public void run(){
//            super.run();
//
//
//        }
//    }



}