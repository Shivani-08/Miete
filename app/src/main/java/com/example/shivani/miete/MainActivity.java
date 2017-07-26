package com.example.shivani.miete;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {
   @InjectView(R.id.text_closet) TextView subTextView;
    @InjectView(R.id.button_olduser) Button oldUserButton;
            @InjectView(R.id.button_newuser) Button newUserButton;
    @InjectView(R.id.skip)Button skip;
    @InjectView(R.id.text_miete) TextView headTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headTextView = (TextView) findViewById(R.id.text_miete);
        subTextView = (TextView) findViewById(R.id.text_closet);
        oldUserButton = (Button) findViewById(R.id.button_olduser);
        newUserButton = (Button) findViewById(R.id.button_newuser);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/alex.ttf");
        headTextView.setTypeface(custom_font);
        subTextView.setTypeface(custom_font);
        oldUserButton.setTypeface(custom_font);
        newUserButton.setTypeface(custom_font);
        skip=(Button) findViewById(R.id.skip);
        skip.setTypeface(custom_font);
        ButterKnife.inject(this);
      //  YoYo.with(Techniques.Bounce)
        //        .duration(700)
          //      .playOn(findViewById(R.id.button_newuser));


    }

    public void onAlreadyAnUserClick(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onNewUserClick(View view) {
        Intent intent = new Intent(MainActivity.this,Signup.class);
        startActivity(intent);
        finish();
    }

    public void onSkipClick(View view) {
        Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
        startActivity(intent);
        finish();

    }
}
