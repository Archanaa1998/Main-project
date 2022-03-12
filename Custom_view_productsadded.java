package com.example.farmersapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Custom_view_productsadded extends BaseAdapter {
    String[] productid,name,category,quantity,rate,image,loginid;
    private Context context;

    public Custom_view_productsadded(Context appcontext,String[] productid,String[] loginid,String[] name,String[] category,String[] quantity,String[] rate,String[] image)
    {
        this.context=appcontext;
        this.productid=productid;
        this.loginid=loginid;
        this.name=name;
        this.category=category;
        this.quantity=quantity;
        this.rate=rate;
        this.image=image;


    }

    @Override
    public int getCount() {
        return category.length;
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
            gridView=inflator.inflate(R.layout.activity_custom_view_productsadded,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView8);
        ImageView im=(ImageView) gridView.findViewById(R.id.imageView2);
        ;



        tv1.setText(name[i]);


        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":5000"+image[i];


        Picasso.with(context).load(url). into(im);

        return gridView;
    }
}
