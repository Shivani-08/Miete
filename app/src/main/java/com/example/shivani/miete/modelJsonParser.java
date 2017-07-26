package com.example.shivani.miete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi joshi on 10/23/2016.
 */
public class modelJsonParser {
    public  static List<model> parsefeed(String content){
        try {
            JSONArray ar = new JSONArray(content);
            List<model> dataList = new ArrayList<>();

            for (int i = ar.length()-1; i >= 0; i--) {
                JSONObject obj = ar.getJSONObject(i);
                model newmodel = new model();

                newmodel.setId(obj.getString("_id"));
                newmodel.setName(obj.getString("name"));
                newmodel.setImgpath(obj.getString("imgpath"));
                newmodel.setCategory(obj.getString("category"));
                newmodel.setContactno(obj.getString("contactno"));
                newmodel.setGender(obj.getString("gender"));
                newmodel.setRentamo(obj.getString("rentAmo"));
                newmodel.setRentduo(obj.getString("rentDuo"));
                newmodel.setUniqueid(obj.getString("uniqueId"));
                newmodel.setAdvmoney(obj.getString("advmoney"));
                newmodel.setLocation(obj.getString("location"));
                dataList.add(newmodel);

            }

            return dataList;
        }catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
