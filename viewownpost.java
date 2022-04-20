package com.example.farmersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class viewownpost  extends AppCompatActivity implements View.OnClickListener {
    ListView lv;
    ImageButton image, iview;
    String[] postid, loginid, photo, description, dateandtime, uname, ph;

    ImageView im;
    ImageButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewownpost);
        lv = (ListView) findViewById(R.id.lv);
        image = (ImageButton) findViewById(R.id.imageButton);
        iview = (ImageButton) findViewById(R.id.viewpost);
        im = (ImageView) findViewById(R.id.image);


        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(getApplicationContext(), discussionforum.class);
                startActivity(ii);
            }
        });
//        image.setOnClickListener(this);
//        iview.setOnClickListener(this);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/viewowndiscussionforum_post";
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
                                postid = new String[js.length()];
                                loginid = new String[js.length()];
                                ph = new String[js.length()];
                                uname = new String[js.length()];
                                photo = new String[js.length()];
                                description = new String[js.length()];
                                dateandtime = new String[js.length()];
//                                String pic=jsonObj.getString("photo");
//                                String url="http://"+ hu + ":5000"+pic;
//                                Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()).into(im);
//


                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    postid[i] = u.getString("postid");
                                    loginid[i] = u.getString("loginid");
                                    ph[i] = u.getString("ph");
                                    uname[i] = u.getString("uname");
                                    photo[i] = u.getString("photo");
                                    description[i] = u.getString("description");
                                    dateandtime[i] = u.getString("dateandtime");


                                }
                                lv.setAdapter(new custom_discussionforum(getApplicationContext(), postid, loginid, photo, description, dateandtime, uname, ph));
                            }


                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "NO POSTS ADDED", Toast.LENGTH_LONG).show();
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
//                params.put("productid", sh.getString("pid",""));
                params.put("uid", sh.getString("lid", ""));
//                params.put("category", tv1.getText().toString());
//                params.put("name", tv2.getText().toString());
//
//                params.put("rate", tv4.getText().toString());
//                params.put("quantity", tv3.getText().toString());
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
    public void onClick(View view) {

    }
}