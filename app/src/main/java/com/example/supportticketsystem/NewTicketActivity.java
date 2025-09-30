package com.example.supportticketsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewTicketActivity extends AppCompatActivity {

    private Spinner spCategory;
    private EditText etSubject, etDescription;
    private CheckBox cbUrgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);

        spCategory    = findViewById(R.id.spCategory);
        etSubject     = findViewById(R.id.etSubject);
        etDescription = findViewById(R.id.etDescription);
        cbUrgent      = findViewById(R.id.cbUrgent);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        if (spCategory.getAdapter() == null) {
            spCategory.setAdapter(new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_dropdown_item,
                    new String[]{"General","Hardware","Software","Network","Account"}));
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String category = spCategory.getSelectedItem() != null
                        ? spCategory.getSelectedItem().toString() : "General";
                String subject = etSubject.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                boolean urgent = cbUrgent.isChecked();

                if (subject.isEmpty() || description.isEmpty()) {
                    Toast.makeText(NewTicketActivity.this, "Subject and description required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                TicketStore.add(NewTicketActivity.this, category, subject, description, urgent);
                Toast.makeText(NewTicketActivity.this, "Ticket created.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
