package com.example.farmerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ipset extends AppCompatActivity implements View.OnClickListener {
EditText edip;
Button btip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipset);
        edip=(EditText)findViewById(R.id.editTextTextPersonName) ;
        btip=(Button) findViewById(R.id.button);
        btip.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String ipval=edip.getText().toString();

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("ip",ipval);
        ed.commit();

        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);

    }
}