package com.example.queueless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    EditText userNameEditText, emailEditText, phoneEditText, passwordEditText;
    Button signUpButton;
    TextView loginTextView;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        
        userNameEditText = findViewById(R.id.editTextText1);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        phoneEditText = findViewById(R.id.editTextPhone);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        signUpButton = findViewById(R.id.button);
        loginTextView = findViewById(R.id.textView8);

        
        dbHelper = new DBHelper(this);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

        
        findViewById(R.id.arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void signUp() {
        String userName = userNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            
            if (checkIfEmailExists(email)) {
                Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
            } else {
                
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DBHelper.COL_USERNAME, userName);
                values.put(DBHelper.COL_EMAIL, email);
                values.put(DBHelper.COL_PHONE_NUMBER, phone);
                values.put(DBHelper.COL_PASSWORD, password);
                long newRowId = db.insert(DBHelper.USER_TABLE, null, values);

                if (newRowId != -1) {
                    
                    SessionManager.userSignIn(newRowId);
                    Intent intent = new Intent(SignUp.this, Home.class);
                    startActivity(intent);
                    finish(); 
                } else {
                    Toast.makeText(this, "Sign up failed, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean checkIfEmailExists(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.COL_EMAIL};
        String selection = DBHelper.COL_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(DBHelper.USER_TABLE, projection, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    private void goToLogin() {
        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
    }

}
