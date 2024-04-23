package com.example.queueless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.queueless.Account;
import com.example.queueless.R;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the account icon by its id
        ImageView accountIcon = findViewById(R.id.nozomilogo);

        // Set a click listener to the account icon
        accountIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Account activity
                goToAccount();
            }
        });
    }

    private void goToAccount() {
        // Start the Account activity
        Intent intent = new Intent(Home.this, Account.class);
        startActivity(intent);
    }
}
