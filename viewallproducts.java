package com.example.farmersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class viewallproducts extends AppCompatActivity implements View.OnClickListener , TextWatcher{
    TextView ed1,ed2,ed3,ed4,ed5;
    EditText ed6;
    ImageView img;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewallproducts);
        ed1 = (TextView) findViewById(R.id.name1);

        ed2 = (TextView) findViewById(R.id.category1);
        ed3 = (TextView) findViewById(R.id.quantity1);
        ed4 = (TextView) findViewById(R.id.rate1);
        btn = (Button) findViewById(R.id.request);
        img = (ImageView) findViewById(R.id.img);
        ed5=(TextView)findViewById(R.id.total1);
        ed6=(EditText)findViewById(R.id.qty1) ;
        btn.setOnClickListener(this);
        ed6.addTextChangedListener(this);

//
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String productid = sh.getString("productid", "");
        String category = sh.getString("category", "");
        String name = sh.getString("name", "");
        String quantity = sh.getString("quantity", "");
        String rate = sh.getString("rate", "");
        String image = sh.getString("image", "");
//
//
//
//
//
        ed1.setText(name);
        ed2.setText(category);
        ed3.setText(quantity);
        ed4.setText(rate);
        SharedPreferences sh1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip = sh1.getString("ip", "");
//
        String url = "http://" + ip + ":5000" + image;
        Log.d("iiiiiiiiiiiii", url);

        Picasso.with(getApplicationContext()).load(url).into(img);
//    }




    }

    @Override
    public void onClick(View view) {
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/userrequestproduct";

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
                params.put("productid", sh.getString("pid",""));
                params.put("uid", sh.getString("lid",""));

                params.put("amount", ed5.getText().toString());
                params.put("quantity", ed6.getText().toString());


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
        float total_qty=Float.parseFloat(ed3.getText().toString());
        float req_qty=Float.parseFloat(ed6.getText().toString());
        if(req_qty>total_qty){
            ed6.setError("req qty >total");
            ed5.setText("");
        }
        else {
            float price = Float.parseFloat(ed4.getText().toString());

            float total = req_qty * price;
            ed5.setText(total + "");
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}