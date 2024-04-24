package com.example.queueless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

public class edit extends AppCompatActivity {

    private EditText editDate;
    private EditText editTime;
    private int reservationId; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        
        reservationId = getIntent().getIntExtra("RESERVATION_ID", -1);

        
        editDate = findViewById(R.id.editDate);
        editTime = findViewById(R.id.editTime);
        Button btnConfirm = findViewById(R.id.button2);
        ImageView arrowImageView = findViewById(R.id.arrow);

        
        populateReservationDetails(reservationId);

        
        btnConfirm.setOnClickListener(v -> {
            
            String editedDate = editDate.getText().toString();
            String editedTime = editTime.getText().toString();

            
            updateReservation(reservationId, editedDate, editedTime);

            
            Intent intent = new Intent(edit.this, History.class);
            startActivity(intent);
            finish(); 
        });

        
        arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(edit.this, History.class);
                startActivity(intent);
                finish(); 
            }
        });
    }

    @SuppressLint("Range")
    private void populateReservationDetails(int reservationId) {
        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getReservationById(reservationId);

        if (cursor != null && cursor.moveToFirst()) {
            String dateTimeString = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_RESERVATION_DATE_TIME));
            String[] dateTimeParts = dateTimeString.split(" ");
            String date = dateTimeParts[1] + " " + dateTimeParts[2] + " " + dateTimeParts[3];
            String time = dateTimeParts[4] + " " + dateTimeParts[5];

            
            editDate.setText(date);
            editTime.setText(time);

            
            CalendarView calendarView = findViewById(R.id.calendarView2);
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    
                    final String newDate = String.format(Locale.getDefault(), "%02d-%02d-%d", dayOfMonth, month + 1, year);
                    
                    editDate.setText(newDate);
                }
            });

            cursor.close();
        }
    }




    private void updateReservation(int reservationId, String editedDate, String editedTime) {
        String message = "Reservation updated with Date: " + editedDate + ", Time: " + editedTime;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
