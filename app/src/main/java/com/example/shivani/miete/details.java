package com.example.shivani.miete;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;



public class details extends AppCompatActivity {

    TextView RentAmo,RentDuo,Category,Gender,Advmoney,Name,Location,Contactno;
    ImageView itemimage;

    model obj;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        obj = (model) getIntent().getSerializableExtra("Meraobject");
        Log.e("object name", obj.getName());
        RentAmo = (TextView) findViewById(R.id.details_rentamt);
        RentDuo = (TextView) findViewById(R.id.details_rentduration);
        Category = (TextView) findViewById(R.id.details_category);
        Gender = (TextView) findViewById(R.id.details_gender);
        Advmoney = (TextView) findViewById(R.id.details_advancemoney);
        Name = (TextView) findViewById(R.id.seller_name);
        Location = (TextView) findViewById(R.id.seller_address);
        Contactno = (TextView) findViewById(R.id.seller_contactno);
        itemimage = (ImageView) findViewById(R.id.details_itemimage);

        RentAmo.setText(obj.getRentamo());
        RentDuo.setText(obj.getRentduo());
        Category.setText(obj.getCategory());
        Gender.setText(obj.getGender());
        Advmoney.setText(obj.getAdvmoney());
        Name.setText(obj.getName());
        Location.setText(obj.getLocation());
        Contactno.setText(obj.getContactno());
        final String imgUrl = obj.getImgpath();

        Picasso.with(details.this).load(imgUrl).into(itemimage);
    }
        public void onContactTextClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            String p = "tel:" + Contactno.getText().toString();
            intent.setData(Uri.parse(p));
            startActivity(intent);

        }



}
