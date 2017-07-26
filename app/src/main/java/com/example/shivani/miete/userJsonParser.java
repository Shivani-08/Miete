package com.example.shivani.miete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ravi joshi on 1/22/2017.
 */

public class userJsonParser {
    public static user parsefeed(String content) {
        user newUser = new user();
        try {
            JSONArray ar = new JSONArray(content);
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);

                newUser.setName(obj.getString("name"));
                newUser.setAddress(obj.getString("address"));
                newUser.setEmail(obj.getString("email"));
                newUser.setPassword(obj.getString("password"));
                newUser.setPhone(obj.getString("phone"));
                return newUser;
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }


        return newUser;
    }
}

