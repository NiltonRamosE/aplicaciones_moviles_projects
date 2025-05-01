package com.example.s01_authui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String savedUsername = prefs.getString("username", null);
        String savedEmail = prefs.getString("email", null);
        String savedPassword = prefs.getString("password", null);

        TextView messageTv = findViewById(R.id.message_welcome_tv);

        String welcome = getString(R.string.welcome_message, savedUsername);
        messageTv.setText(welcome);

        Button logoutBtn = findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();

                Toast.makeText(HomeActivity.this, "Cierre de sesión exitoso", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }
}