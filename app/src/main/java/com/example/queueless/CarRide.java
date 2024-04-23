package com.example.queueless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CarRide extends AppCompatActivity {

    ImageView back;
    RadioGroup radioGroup1, radioGroup2, radioGroup3;
    Button btn, Location;
    TextView myLocation;
    DBHelper dbHelper;
    String carType = "", carSize = "", dateAndTime;
    int restaurantID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_ride);

        dbHelper = new DBHelper(this);

        back = findViewById(R.id.back);
        Location = findViewById(R.id.location);
        myLocation = findViewById(R.id.myLocation);
        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);
        btn = findViewById(R.id.btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        restaurantID = getIntent().getExtras().getInt("restaurantID");
        dateAndTime = getIntent().getExtras().getString("dateAndTime");

        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarRide.this, map.class);
                startActivityForResult(intent, 1);
            }
        });

        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                carSize = radioButton.getText().toString();
            }
        });

        radioGroup1.setOnCheckedChangeListener(radioGroupListener);
        radioGroup2.setOnCheckedChangeListener(radioGroupListener);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carSize.equals("")) {
                    Toast.makeText(CarRide.this, "Please select the size of car", Toast.LENGTH_SHORT).show();
                } else if (carType.equals("")) {
                    Toast.makeText(CarRide.this, "Please select the type of car", Toast.LENGTH_SHORT).show();
                } else if (myLocation.getText().toString().isEmpty()) {
                    Toast.makeText(CarRide.this, "Please select your location", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.addTransportationService(carType, myLocation.getText().toString(),
                            dateAndTime, carSize, restaurantID);
                    Toast.makeText(CarRide.this, "added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CarRide.this, Home.class);
                    startActivity(intent);
                }
            }
        });
    }
    private final RadioGroup.OnCheckedChangeListener radioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (group == radioGroup1) {
                radioGroup2.setOnCheckedChangeListener(null);
                radioGroup2.clearCheck();
                radioGroup2.setOnCheckedChangeListener(radioGroupListener);
            } else if (group == radioGroup2) {
                radioGroup1.setOnCheckedChangeListener(null);
                radioGroup1.clearCheck();
                radioGroup1.setOnCheckedChangeListener(radioGroupListener);
            }

            RadioButton radioButton = findViewById(checkedId);
            carType=radioButton.getText().toString();
        }
    };

    String Address;
    Double Long, Lat;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Address = data.getStringExtra("Address");
                myLocation.setText(Address);
                Long = data.getDoubleExtra("Long",0);
                Lat = data.getDoubleExtra("Lat",0);
            }
        }
    }

}