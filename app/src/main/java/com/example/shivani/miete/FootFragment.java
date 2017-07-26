package com.example.shivani.miete;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FootFragment extends Fragment {
    GridView gridView;
    List<model> dataList;
    ProgressDialog progress;
    public FootFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_foot, container, false);
        gridView = (GridView) view.findViewById(R.id.grid_view);
        // Inflate the layout for this fragment
        if (isonline()) {
            Log.e("Requesting the  port", " ");
            progress = new ProgressDialog(getActivity());
            progress.setTitle("Loading...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
            requestData("https://" + getString(R.string.ipAdd) + "/books/footfragment");
        } else {
            Toast.makeText(getActivity(), "Network isnt available ", Toast.LENGTH_SHORT).show();
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                model obj=dataList.get(i);
                Intent intent =new Intent(getActivity(),details.class);
                intent.putExtra("Meraobject",obj);
                startActivity(intent);
            }
        });

        return view;
    }
    protected  void requestData(String uri){
        StringRequest request = new StringRequest(uri,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.length()>2) {
                            Log.e("response ki length ", String.valueOf(response.length()));
                            dataList = modelJsonParser.parsefeed(response);
                            gridView = (GridView) getView().findViewById(R.id.grid_view);
                            progress.dismiss();
                            dataAdapter adapter = new dataAdapter(getActivity(), R.layout.grid_layout, dataList);
                            gridView.setAdapter(adapter);
                        }else{
                            progress.dismiss();
                            Toast.makeText(getActivity(),"No items available",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError ex) {
                        Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
        RequestQueue queue;
        queue = Volley.newRequestQueue(getActivity());
        queue.add(request);

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

}
