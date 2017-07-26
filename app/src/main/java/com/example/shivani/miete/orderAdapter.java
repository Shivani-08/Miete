package com.example.shivani.miete;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shivani.miete.utils.PrefsHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by ravi joshi on 1/22/2017.
 */


public class orderAdapter  extends ArrayAdapter<model> {
    private Context context;
    private List<model> dataList;
    model model, model2;
    int pos;
    public  ListView listview;




    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public orderAdapter(Context context, int resource, List<model> objects) {
        super(context, resource, objects);
        this.context = context;
        this.dataList = objects;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.order_form, parent, false);

        MyViewHolder viewHolder = new MyViewHolder();

        model = dataList.get(position);
        Log.e("Name of ", model.getName() + "");
        listview = (ListView) view.findViewById(R.id.List_view);
        viewHolder.name = (TextView) view.findViewById(R.id.name);
        viewHolder.delete = (Button) view.findViewById(R.id.remove);
        viewHolder.edit=(Button) view.findViewById(R.id.editorder);
        viewHolder.name.setText(model.getName());
        viewHolder.image = (ImageView) view.findViewById(R.id.img);
        String imgUrl =  model.getImgpath();
        Picasso.with(context).load(imgUrl).resize(70, 70).into(viewHolder.image);

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isonline()) {
                    Log.e("Requesting the  port", " ");
                    pos = position;
                    requestDelete("https://" + context.getString(R.string.ipAdd) + "/upload/delete");


                } else {
                    Toast.makeText(context, "Network isn't available ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isonline()){
                    pos = position;
                    model2 = dataList.get(pos);
                    Intent intent =new Intent(context,Upload.class);
                    intent.putExtra("Meraobject",model2);
                    intent.putExtra("value",2);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "Network isn't available ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    static class MyViewHolder {

        private TextView name;
        private ImageView image;
        private Button edit, delete;

    }


    public void requestDelete(String uri) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String success = "";
                        String message = "";
                        try {
                            JSONObject obje = new JSONObject(response);
                            success = obje.getString("success");
                            message = obje.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (success.equals("true")) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            afterres();

                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Login again ", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, LoginActivity.class);
                context.startActivity(i);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                model2 = dataList.get(pos);
                Log.e("My id:------- ", model2.getUniqueid() + " " + model2.getName() + pos);
                params.put("uid", model2.getUniqueid());


                //returning parameters
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", (String) PrefsHelper.getPrefsHelper(context).getPref(PrefsHelper.PREF_TOKEN));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //Adding request to the queue
        requestQueue.add(stringRequest);


    }

    public boolean isonline() {
        Log.e("coool start is online", " ");
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }

    }
    public void afterres() {
        Intent i = new Intent(context, NavigationActivity.class);
        context.startActivity(i);

    }

}



