package com.example.farmerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity implements View.OnClickListener {
    EditText edname, edpost, edpincode, edhousename, eddistrict, edplace, edphoneno, edemail, edpassword;
    Button bt1;
    ImageView im;
    String path, atype, fname, attach, attatch1;

    byte[] byteArray = null;
CheckBox farmer,whole,normal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        edname = (EditText) findViewById(R.id.etRegName);
        edpost = (EditText) findViewById(R.id.etRegPost);
        edpincode = (EditText) findViewById(R.id.etRegPincode);
        edhousename = (EditText) findViewById(R.id.etRegHouseName);
        eddistrict = (EditText) findViewById(R.id.etRegDistrict);
        edplace = (EditText) findViewById(R.id.etRegPlace);
        edphoneno = (EditText) findViewById(R.id.etRegPhone);
        edemail = (EditText) findViewById(R.id.etRegEmail);
        farmer=(CheckBox) findViewById(R.id.checkBox);
        whole=(CheckBox) findViewById(R.id.checkBox4);
        normal=(CheckBox) findViewById(R.id.checkBox3);
        edpassword = (EditText) findViewById(R.id.etRegPassword);
        im = (ImageView) findViewById(R.id.imageView);
        bt1 = (Button) findViewById(R.id.btnGotoLogin);
        bt1.setOnClickListener(this);
        im.setOnClickListener(this);


    }
String ty="";
    @Override
    public void onClick(View view) {
        int flg=0;
        if (view == im) {
showfilechooser(1);

        } else {
            if (farmer.isChecked()) {
                ty = "farmer";
            }
            if (whole.isChecked()) {
                if (ty.equalsIgnoreCase("")) {
                    ty = "whole";
                } else {

                    ty = ty + ",whole";
                }
            }
            if (normal.isChecked()) {
                if (ty.equalsIgnoreCase("")) {
                    ty = "normal";
                } else {

                    ty = ty + ",normal";
                }
            }
            String name = edname.getText().toString();
            if (name.length() == 0) {
                edname.setError("");
                flg++;
            }
            String post = edpost.getText().toString();
            if (post.length() == 0) {
                edpost.setError("");
                flg++;
            }
            String pin = edpincode.getText().toString();
            if (pin.length() < 6 || pin.length() > 6) {
                edpincode.setError("Length must be 6");
                flg++;
            }
            String housename = edhousename.getText().toString();
            if (housename.length() == 0) {
                edhousename.setError("");
                flg++;
            }
            String district = eddistrict.getText().toString();
            if (district.length() == 0) {
                eddistrict.setError("");
                flg++;
            }
            String place = edplace.getText().toString();
            if (place.length() == 0) {
                edplace.setError("");
                flg++;
            }
            String phoneno = edphoneno.getText().toString();
            if (phoneno.length() < 10 || phoneno.length() > 10) {
                edphoneno.setError("length must be 10");
                flg++;
            }
            String email = edemail.getText().toString();
            if (email.length() == 0) {
                edemail.setError("");
                flg++;
            }
            String password = edpassword.getText().toString();
            if (password.length() == 0) {
                edpassword.setError("");
                flg++;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edemail.setError("Must be of the form name@gmail.com");
                flg++;
            }
            if(attach.equalsIgnoreCase(""))
            { Toast.makeText(getApplicationContext(), "Choose an image", Toast.LENGTH_LONG).show();

                flg++;
            }
            if(ty.equalsIgnoreCase(""))
            {

                Toast.makeText(getApplicationContext(), "Choose a user type", Toast.LENGTH_LONG).show();
normal.setError("");
                flg++;
            }
            if (flg == 0) {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/farmerregistration_post";


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

                                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                        Intent ij = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(ij);
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
                        params.put("emailid", email);
                        params.put("name", name);
                        params.put("housename", housename);
                        params.put("district", district);
                        params.put("post", post);
                        params.put("pincode", pin);
                        params.put("place", place);
                        params.put("phoneno", phoneno);
                        params.put("photo", attach);
                        params.put("password", password);
                        params.put("ty", ty);


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
        }
    }


    void showfilechooser(int string) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //getting all types of files

        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), string);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                ////
                Uri uri = data.getData();

                try {
                    path = FileUtils.getPath(this, uri);

                    File fil = new File(path);
                    float fln = (float) (fil.length() / 1024);
                    atype = path.substring(path.lastIndexOf(".") + 1);


                    fname = path.substring(path.lastIndexOf("/") + 1);
                    // ed15.setText(fname);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                try {

                    File imgFile = new File(path);

                    if (imgFile.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        im.setImageBitmap(myBitmap);

                    }


                    File file = new File(path);
                    byte[] b = new byte[8192];
                    Log.d("bytes read", "bytes read");

                    InputStream inputStream = new FileInputStream(file);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    int bytesRead = 0;

                    while ((bytesRead = inputStream.read(b)) != -1) {
                        bos.write(b, 0, bytesRead);
                    }
                    byteArray = bos.toByteArray();

                    String str = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                    attach = str;


                } catch (Exception e) {
                    Toast.makeText(this, "String :" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }

                ///

            }
        }


    }
}