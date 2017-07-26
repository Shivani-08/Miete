package com.example.shivani.miete;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;



public class Upload extends AppCompatActivity {

    ProgressDialog progress;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    Button ch,upload;
    ImageView imgView;
    Bitmap orgimage;
    Bitmap scaleimage;
    EditText Name,Location,RentAmo,RentDuo,AdvMoney,ContactNo;
    String name,location,rentamo,rentduo,advmoney,contactno,category,gender;
    model obj;
    Spinner Category,Gender,Duration;
    int check=0;
    int val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        ch=(Button) findViewById(R.id.button1);
        upload= (Button) findViewById(R.id.button2);

        Name=(EditText) findViewById(R.id.edit1);
        Location= (EditText) findViewById(R.id.edit2);
        RentAmo=(EditText) findViewById(R.id.edit3);
        RentDuo=(EditText) findViewById(R.id.edit4);
        AdvMoney=(EditText) findViewById(R.id.edit5);
        ContactNo=(EditText) findViewById(R.id.edit6);
        Category=(Spinner) findViewById(R.id.spinner1);
        Gender=(Spinner)findViewById(R.id.spinner2);
        Duration=(Spinner)findViewById(R.id.spinner3);


        val= getIntent().getIntExtra("value",0);

        if(val==2)
        {
            obj = (model)getIntent().getSerializableExtra("Meraobject");
            imgView=(ImageView) findViewById(R.id.rentImage);
            Name.setText(obj.getName());
            Location.setText(obj.getLocation());
            RentAmo.setText(obj.getRentamo());
            AdvMoney.setText(obj.getAdvmoney());
            ContactNo.setText(obj.getContactno());
            final String imgUrl= obj.getImgpath();
            Picasso.with(Upload.this).load(imgUrl).into(imgView);
        }


        Log.e("name"+name+"  "+location," "+rentamo+" "+rentduo+" "+advmoney+" "+contactno);
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImagefromGallery(v);
                check=1;
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name =  Name.getText().toString().trim();
                location=Location.getText().toString();
                rentamo=RentAmo.getText().toString();
                rentduo=RentDuo.getText().toString();
                advmoney=AdvMoney.getText().toString();
                contactno=ContactNo.getText().toString();
                category=Category.getSelectedItem().toString();
                gender=Gender.getSelectedItem().toString();
                rentduo=rentduo+" "+Duration.getSelectedItem().toString();
                Log.e("name"+name+"  "+location," "+rentamo+" "+rentduo+" "+advmoney+" "+contactno);
                if(isonline()) {
                    if(check==1)
                    {
                        if(name.length()>0)
                        {
                            if(location.length()>0)
                            {
                                if(rentamo.length()>0){
                                    if(rentduo.length()>0){
                                        if(contactno.length()==10){
                                            if(advmoney.length()>0)
                                            {
                                                if(val==2)
                                                {
                                                    progress = new ProgressDialog(Upload.this);
                                                    progress.setTitle("Updating...");
                                                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                                                    progress.show();
                                                    update("https://" + getString(R.string.ipAdd) + "/upload/edit");
                                                }
                                                else{
                                                    progress = new ProgressDialog(Upload.this);
                                                    progress.setTitle("Uploading...");
                                                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                                                    progress.show();
                                                    uploadImage("https://" + getString(R.string.ipAdd) + "/upload");
                                                }
                                            }else
                                            {
                                                AdvMoney.requestFocus();
                                                AdvMoney.setError("Please enter advance money");
                                            }
                                        }else
                                        {
                                            ContactNo.requestFocus();
                                            ContactNo.setError("Please enter 10 digit number");
                                        }
                                    }else
                                    {
                                        RentDuo.requestFocus();
                                        RentDuo.setError("Please enter rent duration");
                                    }
                                }else
                                {
                                    RentAmo.requestFocus();
                                    RentAmo.setError("Please enter rent amount");
                                }
                            }
                            else{
                                Location.requestFocus();
                                Location.setError("Please fill your location");
                            }
                        }else
                        {
                            Name.requestFocus();
                            Name.setError("Please enter the name");
                        }
                    }else{
                        Toast.makeText(Upload.this, "Please Select the image", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(Upload.this, "Network isnt avialable", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(Upload.this, NavigationActivity.class);
        startActivity(intent);
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
        Intent i = new Intent(Upload.this, NavigationActivity.class);
        startActivity(i);

        finish();
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void uploadImage(String uri){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Upload.this, response, Toast.LENGTH_LONG).show();
                        responseAnekeBaad();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(Upload.this, "Login again ", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Upload.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String image = getStringImage(orgimage);

                Map<String,String> params = new Hashtable<String, String>();
                params.put("photo", image);
                params.put("name", name);
                params.put("Rentamo",rentamo);
                params.put("Rentduo",rentduo);
                params.put("Advmoney",advmoney);
                params.put("Contactno",contactno);
                params.put("Category",category);
                params.put("Gender",gender);
                params.put("Location",location);
                //returning parameters
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization",(String) PrefsHelper.getPrefsHelper(Upload.this).getPref(PrefsHelper.PREF_TOKEN));
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.e("Request added for upl"," ");
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

    public void update(String uri)
    {
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
                            Toast.makeText(Upload.this,message,Toast.LENGTH_LONG).show();
                            responseAnekeBaad();
                        }
                        else{
                            Toast.makeText(Upload.this,message,Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(Upload.this,"Login again" , Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Upload.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String image = getStringImage(orgimage);

                Map<String,String> params = new Hashtable<String, String>();
                params.put("photo", image);
                params.put("name", name);
                params.put("Rentamo",rentamo);
                params.put("Rentduo",rentduo);
                params.put("Advmoney",advmoney);
                params.put("Contactno",contactno);
                params.put("Category",category);
                params.put("Gender",gender);
                params.put("Location",location);
                params.put("uid",obj.getUniqueid());
                //returning parameters
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization",(String) PrefsHelper.getPrefsHelper(Upload.this).getPref(PrefsHelper.PREF_TOKEN));
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.e("Request added for upd"," ");
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }


    public void loadImagefromGallery(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,RESULT_LOAD_IMG);
    }




    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(String pathToFile,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathToFile, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathToFile, options);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                imgView = (ImageView) findViewById(R.id.rentImage);
                // Set the Image in ImageView after decoding the String

                orgimage = BitmapFactory.decodeFile(imgDecodableString);
                Log.e("generating bitmap", " "+orgimage);
                scaleimage=decodeSampledBitmapFromResource(imgDecodableString,180,180);
                imgView.setImageBitmap(scaleimage);




            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }


}

