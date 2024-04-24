package com.example.queueless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class restaurantInfo extends AppCompatActivity {

    private String name_rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        if (getIntent().getStringExtra("name") != null) {
            name_rest = getIntent().getStringExtra("name");
        }

        DBHelper dbHelper = new DBHelper(restaurantInfo.this);

        Cursor cursor = dbHelper.getRestaurant(name_rest);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(DBHelper.COL_RESTAURANT_NAME);
                int cuisineIndex = cursor.getColumnIndex(DBHelper.COL_CUISINE_TYPE);
                int ratingIndex = cursor.getColumnIndex(DBHelper.COL_RATING);
                int locationIndex = cursor.getColumnIndex(DBHelper.COL_LOCATION);
                int openingHoursIndex = cursor.getColumnIndex(DBHelper.COL_OPENING_HOURS);
                int phoneNumberIndex = cursor.getColumnIndex(DBHelper.COL_RESTAURANT_PHONE_NUMBER);

                do {
                    if (nameIndex != -1 && cuisineIndex != -1 && ratingIndex != -1 &&
                            locationIndex != -1 && openingHoursIndex != -1 &&
                            phoneNumberIndex != -1) {
                        String name = cursor.getString(nameIndex);
                        String cuisine = cursor.getString(cuisineIndex);
                        double rating = cursor.getDouble(ratingIndex);
                        String location = cursor.getString(locationIndex);
                        String openingHours = cursor.getString(openingHoursIndex);
                        String phoneNumber = cursor.getString(phoneNumberIndex);

                        ImageView logo = findViewById(R.id.imageView16);
                        TextView nameTextView = findViewById(R.id.textView);
                        TextView ratingTextView = findViewById(R.id.textView6);
                        TextView cuisineTextView = findViewById(R.id.textView7);
                        TextView locationTextView = findViewById(R.id.textView3);
                        TextView hoursTextView = findViewById(R.id.textView9);
                        TextView phoneNumberTextView = findViewById(R.id.textView10);

                        nameTextView.setText(name);
                        ratingTextView.setText(String.valueOf(rating));
                        cuisineTextView.setText(cuisine);
                        locationTextView.setText(location);
                        hoursTextView.setText(openingHours);
                        phoneNumberTextView.setText(phoneNumber);

                        
                        if (name.replace(" " , "").equalsIgnoreCase("myazu"))
                        {
                            logo.setImageDrawable(getDrawable(R.drawable.myazu));
                        }else if (name.replace(" " , "").equalsIgnoreCase("sancarlo")){
                            logo.setImageDrawable(getDrawable(R.drawable.sancarlo));
                        }else if (name.replace(" " , "").equalsIgnoreCase("lpm")){
                            logo.setImageDrawable(getDrawable(R.drawable.lpm));
                        }else if (name.replace(" " , "").equalsIgnoreCase("MNKYHSE")){
                            logo.setImageDrawable(getDrawable(R.drawable.mnky));
                        }else if (name.replace(" " , "").equalsIgnoreCase("aok")){
                            logo.setImageDrawable(getDrawable(R.drawable.aok));
                        }
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        Button reservationButton = findViewById(R.id.button2);

        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(restaurantInfo.this, reservation.class);
                startActivity(intent);
            }
        });
        ImageView arrowImageView = findViewById(R.id.imageView14);
        arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(restaurantInfo.this, Home.class);
                startActivity(intent);
                finish(); 
            }
        });
    }


}