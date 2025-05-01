package com.example.s01_authui;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        AppCompatButton pageLoginBtn = findViewById(R.id.page_login_btn);
        pageLoginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ImageButton registerBtn = findViewById(R.id.register_post_btn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameEt = findViewById(R.id.username_register_et);
                EditText emailEt = findViewById(R.id.email_register_et);
                EditText passwordEt = findViewById(R.id.password_register_et);

                String username = usernameEt.getText().toString();
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();

                SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", username);
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();

                Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

            }
        });


    }
}