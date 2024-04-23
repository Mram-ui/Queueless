package com.example.queueless;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.queueless.R;
import com.example.queueless.SessionManager;

import java.util.HashMap;

public class Account extends AppCompatActivity {

    EditText usernameEditText, emailEditText, phoneEditText, passEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize views
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        passEditText = findViewById(R.id.passs);
        ImageView arrowButton = findViewById(R.id.arrow);
        Button logoutButton = findViewById(R.id.button);

        // Retrieve user data from session
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userData = sessionManager.getUserData();

        // Extract user data from HashMap
        String username = userData.get(SessionManager.KEY_USERNAME);
        String email = userData.get(SessionManager.KEY_EMAIL);
        String phone = userData.get(SessionManager.KEY_PHONE);
        String password = userData.get(SessionManager.KEY_PASSWORD);

        // Display user data in EditText fields
        usernameEditText.setText(username);
        emailEditText.setText(email);
        phoneEditText.setText(phone);
        passEditText.setText(password);

        // Set click listener for arrow button to navigate back to Home activity
        arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // This will act like the back button and navigate to the previous activity
            }
        });

        // Set click listener for logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });
    }

    // Method to show logout confirmation dialog
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout Confirmation");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Clear the session (remove user data)
                SessionManager sessionManager = new SessionManager(Account.this);
                sessionManager.clearSession();

                // Navigate to MainActivity
                startActivity(new Intent(Account.this, MainActivity.class));

                // Finish the current activity
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
