package com.example.supportticketsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TicketListActivity extends AppCompatActivity {
    private TicketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);

        findViewById(R.id.btnNewTicket).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(TicketListActivity.this, NewTicketActivity.class));
            }
        });

        ListView list = findViewById(R.id.listTickets);
        EditText etSearch = findViewById(R.id.etSearch);

        List<Ticket> data = TicketStore.load(this);
        adapter = new TicketAdapter(this, data);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ticket t = adapter.getItem(position);
                Intent i = new Intent(TicketListActivity.this, TicketDetailActivity.class);
                i.putExtra("TICKET_ID", t.id);
                startActivity(i);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s != null ? s.toString() : "");
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.updateData(TicketStore.load(this));
    }
}
