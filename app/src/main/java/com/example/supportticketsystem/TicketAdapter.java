package com.example.supportticketsystem;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends ArrayAdapter<Ticket> {
    private final LayoutInflater inflater;
    private final List<Ticket> all, shown;

    public TicketAdapter(@NonNull Context context, List<Ticket> data) {
        super(context, 0);
        inflater = LayoutInflater.from(context);
        all = new ArrayList<>(data);
        shown = new ArrayList<>(data);
    }

    @Override public int getCount() { return shown.size(); }
    @Override public Ticket getItem(int position) { return shown.get(position); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView != null ? convertView : inflater.inflate(R.layout.row_ticket, parent, false);
        Ticket t = getItem(position);
        TextView tvMeta = v.findViewById(R.id.tvMeta);

        String prefix = t.resolved ? "✅ " : (t.urgent ? "⚠ " : "");

        String date = DateFormat.format("yyyy-MM-dd HH:mm", t.createdAt).toString();
        tvMeta.setText(t.category + " • " + date);
        return v;
    }

    public void updateData(List<Ticket> fresh) {
        all.clear(); all.addAll(fresh);
        shown.clear(); shown.addAll(fresh);
        notifyDataSetChanged();
    }

    public void filter(String q) {
        String s = q == null ? "" : q.trim().toLowerCase();
        shown.clear();
        if (s.isEmpty()) {
            shown.addAll(all);
        } else {
            for (Ticket t : all) {
                if ((t.subject != null && t.subject.toLowerCase().contains(s)) ||
                        (t.description != null && t.description.toLowerCase().contains(s)) ||
                        (t.category != null && t.category.toLowerCase().contains(s))) {
                    shown.add(t);
                }
            }
        }
        notifyDataSetChanged();
    }
}
