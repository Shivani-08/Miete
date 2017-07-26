package com.example.shivani.miete;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static java.lang.System.load;


/**
 * Created by ravi joshi on 10/23/2016.
 */
public class dataAdapter  extends ArrayAdapter<model> {
    private Context context;
    private List<model> dataList;




    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public dataAdapter(Context context, int resource, List<model>objects){
      super(context,resource, objects);
        this.context=context;
        this.dataList=objects;


    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.grid_layout,parent,false);
        final model model = dataList.get(position);
        TextView tv= (TextView) view.findViewById(R.id.name_text);
        TextView rt= (TextView) view.findViewById(R.id.rate_text);
        tv.setText(model.getName());
        rt.setText(model.getRentamo());
        final ImageView image= (ImageView) view.findViewById(R.id.grid_image);
        String imgUrl= model.getImgpath();

        Picasso.with(context).load(imgUrl).resize(150,150).into(image);
        return view;
    }

}
