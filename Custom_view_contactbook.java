package com.example.farmersapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Custom_view_contactbook extends BaseAdapter {
    String[] contactbookid,name,phoneno,emailid,department;
    private Context context;

    public Custom_view_contactbook(Context appcontext,String[]contactbookid,String[] name,String[] phoneno,String[] emailid,String[] department)
    {
        this.context=appcontext;
        this.contactbookid=contactbookid;
        this.name=name;
        this.phoneno=phoneno;
        this.emailid=emailid;
        this.department=department;


    }
    @Override
    public int getCount() {
        return name.length;
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
            gridView=inflator.inflate(R.layout.custom_view_contactbook,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView1);

        TextView tv2=(TextView)gridView.findViewById(R.id.textView2);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView3);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView4);
        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv1.setText("Department            :       "+department[i]);
        tv2.setText("Name                       :       "+name[i]);
        tv3.setText("Email id                   :       "+emailid[i]);
        tv4.setText("Phone Number      :       "+phoneno[i]);

//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//        String ip=sh.getString("ip","");

//        String url="http://" + ip + ":5000"+photo[i];


//        Picasso.with(context).load(url). into(im);

        return gridView;

    }
}
