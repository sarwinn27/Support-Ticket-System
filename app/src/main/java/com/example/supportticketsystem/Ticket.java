package com.example.supportticketsystem;

import org.json.JSONException;
import org.json.JSONObject;

public class Ticket {
    public String id;
    public String category;
    public String subject;
    public String description;
    public boolean urgent;
    public boolean resolved;
    public long createdAt;

    public Ticket(String id, String category, String subject, String description, boolean urgent, boolean resolved, long createdAt) {
        this.id = id;
        this.category = category;
        this.subject = subject;
        this.description = description;
        this.urgent = urgent;
        this.resolved = resolved;
        this.createdAt = createdAt;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject o = new JSONObject();
        o.put("id", id);
        o.put("category", category);
        o.put("subject", subject);
        o.put("description", description);
        o.put("urgent", urgent);
        o.put("resolved", resolved);
        o.put("createdAt", createdAt);
        return o;
    }

    public static Ticket fromJson(JSONObject o) throws JSONException {
        return new Ticket(
                o.getString("id"),
                o.optString("category", "General"),
                o.getString("subject"),
                o.optString("description", ""),
                o.optBoolean("urgent", false),
                o.optBoolean("resolved", false),
                o.optLong("createdAt", System.currentTimeMillis())
        );
    }
}
