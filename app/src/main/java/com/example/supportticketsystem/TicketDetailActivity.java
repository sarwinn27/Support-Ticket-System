package com.example.supportticketsystem;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TicketDetailActivity extends AppCompatActivity {
    private Ticket ticket;
    private EditText etSubject, etDescription;
    private CheckBox cbUrgent, cbResolved;
    private TextView tvCategory, tvMeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        String id = getIntent().getStringExtra("TICKET_ID");
        ticket = TicketStore.findById(this, id);
        if (ticket == null) { finish(); return; }

        tvCategory = findViewById(R.id.tvCategory);
        tvMeta     = findViewById(R.id.tvMeta);
        etSubject  = findViewById(R.id.etSubject);
        etDescription = findViewById(R.id.etDescription);
        cbUrgent   = findViewById(R.id.cbUrgent);
        cbResolved = findViewById(R.id.cbResolved);
        Button btnSave   = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);

        tvCategory.setText(ticket.category);
        String date = DateFormat.format("yyyy-MM-dd HH:mm", ticket.createdAt).toString();
        tvMeta.setText("Created: " + date + (ticket.resolved ? " • Resolved" : ""));

        etSubject.setText(ticket.subject);
        etDescription.setText(ticket.description);
        cbUrgent.setChecked(ticket.urgent);
        cbResolved.setChecked(ticket.resolved);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ticket.subject = etSubject.getText().toString().trim();
                ticket.description = etDescription.getText().toString().trim();
                ticket.urgent = cbUrgent.isChecked();
                ticket.resolved = cbResolved.isChecked();
                TicketStore.update(TicketDetailActivity.this, ticket);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                TicketStore.delete(TicketDetailActivity.this, ticket.id);
                finish();
            }
        });
    }
}
