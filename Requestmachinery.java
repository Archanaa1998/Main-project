package com.example.farmerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Requestmachinery extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    TextView t1,t2,t4,t5;
    EditText t3;
    ImageView image;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestmachinery);
        t1=(TextView) findViewById(R.id.name1);
        t2=(TextView) findViewById(R.id.modelno1);
        t3=(EditText) findViewById(R.id.noofdays1);
        t4=(TextView) findViewById(R.id.total1);
        t5=(TextView) findViewById(R.id.rent1);
        b1=(Button) findViewById(R.id.request);
        image=(ImageView)findViewById(R.id.img);
        b1.setOnClickListener(this);

        t3.addTextChangedListener(this);
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String mid = sh.getString("mid", "");
        String name= sh.getString("name","");
        String modelno= sh.getString("modelno","");
        String rentamount= sh.getString("rentamount","");
        String description= sh.getString("description","");
        String photo= sh.getString("photo","");
        String purpose= sh.getString("purpose","");
        t1.setText(name);
        t2.setText(modelno);
        t5.setText(rentamount);
        SharedPreferences sh1= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip=sh1.getString("ip","");

        String url="http://" + ip + ":5000"+photo;
        Log.d("iiiiiiiiiiiii",url);

        Picasso.with(getApplicationContext()).load(url). into(image);

    }

    @Override
    public void onClick(View view) {
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/userrequestmachinery";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                Toast.makeText(getApplicationContext(), "Request Sent Successfully", Toast.LENGTH_LONG).show();


                                Intent i = new Intent(getApplicationContext(), View_Machinery.class);
                                startActivity(i);
                            }


                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();

                String id = sh.getString("uid", "");
                params.put("noofdays", t3.getText().toString());
                params.put("uid", sh.getString("lid",""));
                params.put("total", t4.getText().toString());
                params.put("mid",sh.getString("mid",""));

                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
float days=Float.parseFloat(t3.getText().toString());
float price=Float.parseFloat(t5.getText().toString());

float total=days*price;
t4.setText(total+"");

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}