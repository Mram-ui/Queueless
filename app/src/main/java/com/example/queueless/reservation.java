package com.example.queueless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class reservation extends AppCompatActivity {

    public static int restaurantID = -1;

    CalendarView calendarView;
    TextView textOfDate;
    EditText numPerson;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn;
    String Time = "";

    ImageView back;
    RadioGroup radioGroup;
    boolean wantCar ;
    DBHelper dbHelper;

    long userID = SessionManager.getUserid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        dbHelper = new DBHelper(this);

        back = findViewById(R.id.back);
        calendarView = findViewById(R.id.calendarView);
        textOfDate = findViewById(R.id.textOfDate);
        numPerson = findViewById(R.id.numPerson);
        btn1= findViewById(R.id.button1);
        btn2= findViewById(R.id.button2);
        btn3= findViewById(R.id.button3);
        btn4= findViewById(R.id.button4);
        btn5= findViewById(R.id.button5);
        btn6= findViewById(R.id.button6);
        radioGroup = findViewById(R.id.radioGroup);
        btn = findViewById(R.id.btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(reservation.this, restaurantInfo.class);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                textOfDate.setText(getFormattedDate(year, month, dayOfMonth));
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(btn1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(btn2);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(btn3);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(btn4);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(btn5);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(btn6);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    wantCar = true;
                } else if (radioButton.getText().toString().equalsIgnoreCase("no")) {
                    wantCar = false;
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numPerson.getText().toString().isEmpty()) {
                    Toast.makeText(reservation.this, "Please enter the number of persons", Toast.LENGTH_SHORT).show();
                } else if (Time.equals("")) {
                    Toast.makeText(reservation.this, "Please enter the Time", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.addReservation(userID, restaurantID,
                            textOfDate.getText().toString() + " " + Time,
                            Integer.parseInt(numPerson.getText().toString()), "Reserved");
                    Toast.makeText(reservation.this, "added successfully", Toast.LENGTH_SHORT).show();
                    if (wantCar) {
                        Intent intent = new Intent(reservation.this, CarRide.class);
                        intent.putExtra("dateAndTime", textOfDate.getText().toString() + " " + Time);
                        intent.putExtra("restaurantID", restaurantID);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(reservation.this, Home.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private String getFormattedDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    private void getTime(Button selectedTime) {
        
        resetButtonBackgroundColors();

        
        selectedTime.setBackgroundColor(Color.BLACK);

        
        Time = selectedTime.getText().toString();
    }

    private void resetButtonBackgroundColors() {
        btn1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btn2.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btn3.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btn4.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btn5.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btn6.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }


    private void changeButtonTint(Button button, int tintColor) {
        Drawable drawable = button.getBackground();
        drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_ATOP);
        button.setBackground(drawable);
    }
}
