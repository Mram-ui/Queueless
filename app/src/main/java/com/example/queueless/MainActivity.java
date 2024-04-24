package com.example.queueless;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        Button loginButton = findViewById(R.id.LoginButton);
        Button signupButton = findViewById(R.id.SignupButton);

        
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });
    }
}
