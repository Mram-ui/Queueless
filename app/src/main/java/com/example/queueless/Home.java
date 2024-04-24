package com.example.queueless;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    private DBHelper dbHelper;
    private LinearLayout restaurantLayout;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dbHelper = new DBHelper(this);
        restaurantLayout = findViewById(R.id.restaurantLayout);
        searchEditText = findViewById(R.id.searchEditText);

        
        dbHelper.insertAllRestaurants();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = searchEditText.getText().toString().trim();
                    
                    Cursor filteredCursor = dbHelper.getRestaurants(query);
                    displayRestaurants(filteredCursor); 
                    return true;
                }
                return false;
            }
        });

        
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.person) {
                    goToAccount();
                    return true;
                } else if (itemId == R.id.history) {
                    goToHistory();
                    return true;
                }

                return false;
            }
        });

        
        displayAllRestaurants();
    }

    private void displayAllRestaurants() {
        Cursor cursor = dbHelper.getAllRestaurants();
        displayRestaurants(cursor);
    }

    private void displayRestaurants(Cursor cursor) {
        restaurantLayout.removeAllViews();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    
                    int nameIndex = cursor.getColumnIndex(DBHelper.COL_RESTAURANT_NAME);
                    int cuisineIndex = cursor.getColumnIndex(DBHelper.COL_CUISINE_TYPE);
                    int ratingIndex = cursor.getColumnIndex(DBHelper.COL_RATING);
                    int locationIndex = cursor.getColumnIndex(DBHelper.COL_LOCATION);
                    int openingHoursIndex = cursor.getColumnIndex(DBHelper.COL_OPENING_HOURS);
                    int phoneNumberIndex = cursor.getColumnIndex(DBHelper.COL_RESTAURANT_PHONE_NUMBER);

                    if (nameIndex != -1 && cuisineIndex != -1 && ratingIndex != -1 &&
                            locationIndex != -1 && openingHoursIndex != -1 &&
                            phoneNumberIndex != -1) {
                        String name = cursor.getString(nameIndex);
                        String cuisine = cursor.getString(cuisineIndex);
                        double rating = cursor.getDouble(ratingIndex);
                        String location = cursor.getString(locationIndex);
                        String openingHours = cursor.getString(openingHoursIndex);
                        String phoneNumber = cursor.getString(phoneNumberIndex);

                        
                        View restaurantView = getLayoutInflater().inflate(R.layout.restaurant_item, null);

                        
                        TextView nameTextView = restaurantView.findViewById(R.id.textView6);
                        TextView cuisineTextView = restaurantView.findViewById(R.id.textView7);
                        TextView ratingTextView = restaurantView.findViewById(R.id.textView8);
                        TextView locationTextView = restaurantView.findViewById(R.id.locationTextView);
                        TextView hoursTextView = restaurantView.findViewById(R.id.hoursTextView);
                        TextView phoneNumberTextView = restaurantView.findViewById(R.id.phoneNumberTextView);
                        ImageView logoImageView = restaurantView.findViewById(R.id.logoImageView);

                        nameTextView.setText(name);
                        cuisineTextView.setText(cuisine);
                        ratingTextView.setText(String.valueOf(rating));
                        locationTextView.setText("Location: " + location);
                        hoursTextView.setText("Opening Hours: " + openingHours);
                        phoneNumberTextView.setText("Phone Number: " + phoneNumber);

                        
                        setRestaurantLogo(logoImageView, name);

                        
                        final String restaurantName = name;
                        logoImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToRestaurantInfo(restaurantName);
                            }
                        });

                        restaurantLayout.addView(restaurantView);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

    private void setRestaurantLogo(ImageView logoImageView, String name) {
        switch (name.toLowerCase()) {
            case "myazu":
                logoImageView.setImageResource(R.drawable.myazu);
                break;
            case "sancarlo":
                logoImageView.setImageResource(R.drawable.sancarlo);
                break;
            case "lpm":
                logoImageView.setImageResource(R.drawable.lpm);
                break;
            case "mnky hse":
                logoImageView.setImageResource(R.drawable.mnky);
                break;
            case "aok":
                logoImageView.setImageResource(R.drawable.aok);
                break;
            default:
                
                logoImageView.setImageResource(R.drawable.sancarlo);
                break;
        }
    }

    public void goToAccount() {
        Intent intent = new Intent(this, Account.class);
        startActivity(intent);
    }

    public void goToHistory() {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    public void goToRestaurantInfo(String name) {
        reservation.restaurantID = dbHelper.getRestaurantIdByName(name);

        Intent intent = new Intent(this, restaurantInfo.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}
