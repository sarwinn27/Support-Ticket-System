package com.example.supportticketsystem;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketStore {
    private static final String PREF = "tickets_pref";
    private static final String KEY = "tickets_json";

    public static List<Ticket> load(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String raw = sp.getString(KEY, "[]");
        List<Ticket> out = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(raw);
            for (int i = 0; i < arr.length(); i++) {
                out.add(Ticket.fromJson(arr.getJSONObject(i)));
            }
        } catch (JSONException ignored) {}
        return out;
    }

    public static void save(Context ctx, List<Ticket> list) {
        JSONArray arr = new JSONArray();
        for (Ticket t : list) {
            try { arr.put(t.toJson()); } catch (JSONException ignored) {}
        }
        ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit().putString(KEY, arr.toString()).apply();
    }

    public static Ticket add(Context ctx, String category, String subject, String description, boolean urgent) {
        List<Ticket> list = load(ctx);
        Ticket t = new Ticket(
                UUID.randomUUID().toString(),
                category == null || category.trim().isEmpty() ? "General" : category.trim(),
                subject.trim(),
                description.trim(),
                urgent,
                false,
                System.currentTimeMillis()
        );
        list.add(0, t);
        save(ctx, list);
        return t;
    }

    public static void update(Context ctx, Ticket updated) {
        List<Ticket> list = load(ctx);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).id.equals(updated.id)) {
                list.set(i, updated);
                break;
            }
        }
        save(ctx, list);
    }

    public static void delete(Context ctx, String id) {
        List<Ticket> list = load(ctx);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).id.equals(id)) {
                list.remove(i);
                break;
            }
        }
        save(ctx, list);
    }

    public static Ticket findById(Context ctx, String id) {
        for (Ticket t : load(ctx)) {
            if (t.id.equals(id)) return t;
        }
        return null;
    }
}
