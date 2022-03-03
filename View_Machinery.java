package com.example.farmerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;


public class View_Machinery extends AppCompatActivity implements TextWatcher, AdapterView.OnItemClickListener {
    ListView lv;
    EditText ed1;
    String[] mid, name, modelno, rentamount, description, photo, purpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_machinery);
        lv = (ListView) findViewById(R.id.lv);
        ed1 = (EditText) findViewById(R.id.editTextTextPersonName4);
        ed1.addTextChangedListener(this);
        lv.setOnItemClickListener(this);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/userviewmachinery_post";
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

                                JSONArray js = jsonObj.getJSONArray("users");
                                mid = new String[js.length()];
                                name = new String[js.length()];
                                modelno = new String[js.length()];
                                rentamount = new String[js.length()];
                                description = new String[js.length()];
                                photo = new String[js.length()];
                                purpose = new String[js.length()];

                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    mid[i] = u.getString("mid");
                                    name[i] = u.getString("name");
                                    description[i] = u.getString("description");
                                    modelno[i] = u.getString("modelno");
                                    rentamount[i] = u.getString("rentamount");
                                    photo[i] = u.getString("photo");
                                    purpose[i] = u.getString("purpose");


                                }
                                lv.setAdapter(new Custom_view_machinery(getApplicationContext(), mid, name, modelno, rentamount, description, photo, purpose));
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
                //   params.put("uid",id);
//                params.put("mac",maclis);

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

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/userviewmachinerysearch_post";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // response
                        try {JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("users");
                                mid = new String[js.length()];
                                name = new String[js.length()];
                                description = new String[js.length()];
                                rentamount = new String[js.length()];
                                modelno = new String[js.length()];
                                photo = new String[js.length()];

                                purpose = new String[js.length()];

//                                for(int i=0;i<js.length();i++)
//                                {
//                                    JSONObject u=js.getJSONObject(i);
//                                    cid[i]=u.getString("cropid");
//                                    cname[i]=u.getString("name");
//                                    description[i]=u.getString("description");
//                                    category[i]=u.getString("category");
//                                    photo[i]=u.getString("photo");
//
//
//
//                                }


                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    mid[i] = u.getString("mid");
                                    name[i] = u.getString("name");
                                    description[i] = u.getString("description");
                                    modelno[i] = u.getString("modelno");
                                    rentamount[i] = u.getString("rentamount");
                                    photo[i] = u.getString("photo");
                                    purpose[i] = u.getString("purpose");


                                }
                                lv.setAdapter(new Custom_view_machinery(getApplicationContext(), mid, name, modelno, rentamount, description, photo, purpose));


                            }



                            // }
                            else {


                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        }    catch (Exception e) {
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

                String id=sh.getString("uid","");
                //   params.put("uid",id);
                params.put("name",ed1.getText().toString());

                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS=100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }


    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("mid",mid[i]);
        ed.putString("mname",name[i]);
        ed.putString("modelno",modelno[i]);
        ed.putString("rentamount",rentamount[i]);
        ed.putString("description",description[i]);
        ed.putString("photo",photo[i]);
        ed.putString("purpose",purpose[i]);
        ed.commit();

        Intent ii=new Intent(getApplicationContext(),Requestmachinery.class);
        startActivity(ii);

    }
}
