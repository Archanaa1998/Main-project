package com.example.farmerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farmerapp.R;
import com.squareup.picasso.Picasso;

public class Custom_view_machinery extends BaseAdapter {
    String[] mid,name,modelno,rentamount,description,photo,purpose;
    private Context context;

    public Custom_view_machinery(Context appcontext,String[]mid,String[]name,String[]modelno,String[]rentamount,String[]description,String[]photo,String[]purpose)
    {
        this.context=appcontext;
        this.mid=mid;
        this.name=name;
        this.modelno=modelno;
        this.rentamount=rentamount;
        this.description=description;
        this.photo=photo;
        this.purpose=purpose;


    }

    @Override
    public int getCount() {
        return purpose.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.custom_view_crops,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView16);
        ImageView im=(ImageView) gridView.findViewById(R.id.imageView10);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView2);
        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv1.setText("Name            : "+name[i]);
        tv2.setText("Purpose     : "+purpose[i]);
        tv3.setText(description[i]);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":5000"+photo[i];
        Log.d("iiiiiiiiiiiii",url);

        Picasso.with(context).load(url). into(im);

        return gridView;
    }
}

