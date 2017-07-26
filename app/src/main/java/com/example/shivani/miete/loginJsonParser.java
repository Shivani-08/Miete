package com.example.shivani.miete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi joshi on 12/18/2016.
 */

public class loginJsonParser {

    public  static login parsefeed(String content){
        login log =new login();
        try {

            JSONObject obje= new JSONObject(content);
            log.setSuccess(obje.getString("success"));
            log.setToken(obje.getString("token"));
            return log;

            }
         catch (JSONException e1) {
            e1.printStackTrace();
        }


        return log;
    }
}
