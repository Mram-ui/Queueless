package com.example.queueless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<infoOfReservation> data = new ArrayList<>();
    ImageView back;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DBHelper(this);

        data = db.getAllReservations(String.valueOf(SessionManager.getUserid()));

        /*data.add(new infoOfReservation("Finished", "Memos",
                "Ramsis Hilton 45 Helwan", 4,"2024-4-28 18:30", 4324));
*/

        myAdapter = new MyAdapter(this, data);

        recyclerView.setAdapter(myAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}