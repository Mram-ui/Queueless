package com.example.queueless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginButton;
    ImageView arrowImageView;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.button);
        arrowImageView = findViewById(R.id.imageView2);

        
        dbHelper = new DBHelper(this);

        
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        
        arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void attemptLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            
            boolean loginSuccessful = dbHelper.checkUserCredentials(email, password);

            
            if (loginSuccessful) {
                long userid = dbHelper.getUserIdByEmail(email);
                SessionManager.userSignIn(userid);
                goToHomeActivity();
            } else {
                Toast.makeText(this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(Login.this, Home.class);
        startActivity(intent);
        finish(); 
    }
}
