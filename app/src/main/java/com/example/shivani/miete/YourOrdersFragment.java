package com.example.shivani.miete;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shivani.miete.utils.PrefsHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class YourOrdersFragment extends Fragment {
    ListView listview;
    List<model> dataList;
    ProgressDialog progress;


    public YourOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your_orders, container, false);
        listview = (ListView) view.findViewById(R.id.List_view);

        // Inflate the layout for this fragment
        if (isonline()) {
            Log.e("Requesting the  port", " ");
            progress = new ProgressDialog(getActivity());
            progress.setTitle("Loading...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
            requestData("https://" + getString(R.string.ipAdd) + "/upload/myorders");

        }
        else {
            Toast.makeText(getActivity(), "Network isnt available ", Toast.LENGTH_SHORT).show();
        }



        return view;
    }

    public   boolean isonline(){
        Log.e("coool start is online", " ");
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo!=null && netInfo.isConnectedOrConnecting()){
            return true;
        }
        else
        {
            return false;
        }

    }

    public void requestData(String uri){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.length()>2) {
                            dataList = modelJsonParser.parsefeed(response);
                            progress.dismiss();
                            orderAdapter adapter = new orderAdapter(getActivity(), R.layout.order_form, dataList);
                            listview.setAdapter(adapter);
                        }
                        else{
                            progress.dismiss();
                            Toast.makeText(getActivity(),"No Ad",Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(getActivity(), " Login Again ", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization",(String) PrefsHelper.getPrefsHelper(getActivity()).getPref(PrefsHelper.PREF_TOKEN));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

}
