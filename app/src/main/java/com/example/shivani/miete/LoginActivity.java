package com.example.shivani.miete;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Hashtable;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView loginMiete;
    AutoCompleteTextView email;
    EditText password;
    Button Login;
    login log;
    String emal,pasrd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginMiete = (TextView) findViewById(R.id.login_miete);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/alex.ttf");
        loginMiete.setTypeface(custom_font);

        email=(AutoCompleteTextView) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        Login=(Button) findViewById(R.id.email_Login_button);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isonline()) {
                    emal = email.getText().toString();
                    pasrd = password.getText().toString();
                    if (emal.length() > 0) {
                        if (pasrd.length() > 0) {
                            login("https://" + getString(R.string.ipAdd) + "/api/login");
                        } else {
                            password.requestFocus();
                            password.setError("Please enter your login email");
                        }
                    } else {
                        email.requestFocus();
                        email.setError("Please enter your login email");
                    }
                }else
                {
                    Toast.makeText(LoginActivity.this, "Network isnt avialable", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public   boolean isonline(){
        Log.e("coool start is online", " ");
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo!=null && netInfo.isConnectedOrConnecting()){
            return true;
        }
        else
        {
            return false;
        }

    }

    public void responseAnekeBaad(){
        Intent i = new Intent(LoginActivity.this, NavigationActivity.class);
        startActivity(i);
        finish();
    }
    public void login(String uri){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(" hamara response ",response);
                        log = loginJsonParser.parsefeed(response);
                        Log.e(" hamara success ",log.getSuccess());
                        if(log.getSuccess().equals("true")){
                            Log.e(" hamara token ",log.getToken());
                            PrefsHelper.getPrefsHelper(LoginActivity.this).savePref(PrefsHelper.PREF_TOKEN,log.getToken());
                            Log.e("Old token",(String) PrefsHelper.getPrefsHelper(LoginActivity.this).getPref(PrefsHelper.PREF_TOKEN,"Cool"));
                            responseAnekeBaad();
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,"Error in login ", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new Hashtable<String, String>();
                params.put("email", emal);
                params.put("password",pasrd);

                //returning parameters
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }
}
