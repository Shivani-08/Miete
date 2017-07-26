package com.example.shivani.miete;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shivani.miete.utils.PrefsHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class YourProfileFragment extends Fragment {
    Button update;
    user newUser;
    EditText Name,Phone,Address,Password;
    AutoCompleteTextView Email;
    String name,phone,address,password,email;

    public YourProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_your_profile, container, false);
        Name=(EditText) view.findViewById(R.id.name);
        Phone=(EditText) view.findViewById(R.id.phone);
        Address=(EditText) view.findViewById(R.id.address);
        Password=(EditText) view.findViewById(R.id.password);
        Email=(AutoCompleteTextView)view.findViewById(R.id.email);
        update =  (Button) view.findViewById(R.id.update1);
        // Inflate the layout for this fragment
        if(isonline()){
            requestUser("https://" + getString(R.string.ipAdd) + "/api/getuser");
        }else{
            Toast.makeText(getActivity(), "Network isnt available ", Toast.LENGTH_SHORT).show();
        }


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=Name.getText().toString();
                phone=Phone.getText().toString();
                address=Address.getText().toString();
                password=Password.getText().toString();
                email=Email.getText().toString();



                if (name.length() > 0) {
                    if (phone.length() == 10) {
                        if (address.length() > 0) {
                            if (email.length() > 0) {
                                if (password.length() > 0) {
                                    requestData("https://" + getString(R.string.ipAdd) + "/api/edit");
                                } else {
                                    Password.requestFocus();
                                    Password.setError("Please enter your Password");
                                }
                            } else {
                                Email.requestFocus();
                                Email.setError("Please enter your login email");
                            }
                        } else {
                            Address.requestFocus();
                            Address.setError("Please Enter your Address");
                        }
                    } else {
                        Phone.requestFocus();
                        Phone.setError("Please enter 10 digit number");

                    }
                } else {
                    Name.requestFocus();
                    Name.setError("Please enter the name");
                }
            }

        });

        return view;
    }
    public void responseAnekeBaad(){

        Intent i = new Intent(getActivity(), NavigationActivity.class);
        startActivity(i);
        getActivity().finish();
    }




    public void requestData(String uri){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String success="";
                        String message="";
                        try {
                            JSONObject obje= new JSONObject(response);
                            success=obje.getString("success");
                            message=obje.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(success.equals("true")){
                            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                            responseAnekeBaad();
                        }
                        else{
                            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                        }

                    }



                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Login Again", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new Hashtable<String, String>();
                params.put("email", email);
                params.put("password",password);
                params.put("name",name);
                params.put("address",address);
                params.put("phone",phone);

                //returning parameters
                return params;
            }
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

    public void requestUser(String uri)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        newUser = userJsonParser.parsefeed(response);
                        Log.e(" My name ",newUser.getName()+"");
                        Name.setText(newUser.getName());
                        Phone.setText(newUser.getPhone());
                        Address.setText(newUser.getAddress());
                        Email.setText(newUser.getEmail());
                        Password.setText(newUser.getPassword());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Login Again ", Toast.LENGTH_SHORT).show();
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
