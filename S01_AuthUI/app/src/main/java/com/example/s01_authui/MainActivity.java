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

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatButton pageRegisterBtn = findViewById(R.id.page_register_btn);
        pageRegisterBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        ImageButton loginBtn = findViewById(R.id.login_post_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEt = findViewById(R.id.email_login_et);
                EditText passwordEt = findViewById(R.id.password_login_et);

                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();

                SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                String savedEmail = prefs.getString("email", null);
                String savedPassword = prefs.getString("password", null);

                if (email.equals(savedEmail) && password.equals(savedPassword)) {
                    Toast.makeText(MainActivity.this, "Login exitoso, bienvenido " + prefs.getString("username", null), Toast.LENGTH_SHORT).show();
                    // Aquí puedes lanzar otra actividad, por ejemplo:
                    // startActivity(new Intent(this, HomeActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}