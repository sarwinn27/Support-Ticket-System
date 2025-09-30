package com.example.supportticketsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, TicketListActivity.class));
                finish();
            }
        });

        findViewById(R.id.tv2fa).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // Placeholder for 2FA
            }
        });
    }
}
