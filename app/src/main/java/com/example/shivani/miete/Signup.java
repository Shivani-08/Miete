package com.example.shivani.miete;

import android.app.ProgressDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class Signup extends AppCompatActivity {
    TextView head;
    EditText Name,Phone,Address,Password;
    AutoCompleteTextView Email;
    Button sn;
    String name,phone,address,password,email;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        head=(TextView) findViewById(R.id.head);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/alex.ttf");
        head.setTypeface(custom_font);

        Name=(EditText)findViewById(R.id.name);
        Phone=(EditText)findViewById(R.id.phone);
        Address=(EditText)findViewById(R.id.address);
        Password=(EditText)findViewById(R.id.password);
        Email=(AutoCompleteTextView)findViewById(R.id.email);
        sn=(Button)findViewById(R.id.email_sign_in_button);
        sn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=Name.getText().toString();
                phone=Phone.getText().toString();
                address=Address.getText().toString();
                password=Password.getText().toString();
                email=Email.getText().toString();

                Name.setText(name);
                Phone.setText(phone);
                Address.setText(address);
                Email.setText(email);

                if(name.length()>0){
                  if(phone.length()==10){
                     if(address.length()>0){
                         if(email.length()>0){
                             if(password.length()>0){
                                 progress = new ProgressDialog(Signup.this);
                                 progress.setTitle("Signing in..");
                                 progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                                 progress.show();
                                 Signup("https://" + getString(R.string.ipAdd) + "/api/register");
                             }else{
                                 Password.requestFocus();
                                 Password.setError("Please enter your Password");
                             }
                         }else{
                             Email.requestFocus();
                             Email.setError("Please enter your login email");
                         }
                     }else{
                             Address.requestFocus();
                             Address.setError("Please Enter your Address");
                     }
                  }else{
                      Phone.requestFocus();
                      Phone.setError("Please enter 10 digit number");

                  }
                }else{
                    Name.requestFocus();
                    Name.setError("Please enter the name");
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
        progress.dismiss();
        Toast.makeText(Signup.this," Login to continue ",Toast.LENGTH_LONG).show();
        Intent i = new Intent(Signup.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void Signup(String uri){
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
                            responseAnekeBaad();
                        }
                        else{
                            Toast.makeText(Signup.this,message,Toast.LENGTH_LONG).show();
                        }

                    }



                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(Signup.this, "Ërror in sign in ", Toast.LENGTH_SHORT).show();
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
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);


    }
}
