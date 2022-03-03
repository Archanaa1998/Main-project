package com.example.farmerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmerapp.databinding.ActivityHomeBinding;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
ImageView im;
TextView tvname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)

                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//        navigationView.setItemIconTintList(null);
        im = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        tvname = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        tvname.setText(sh.getString("name",""));
        String url = "http://" + sh.getString("ip","") + ":5000"+sh.getString("photo","");
       // Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();


        Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()).into(im);
    }
//.setOpenableLayout(drawer)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        if(id==R.id.nav_crop)
        {
            startActivity(new Intent(getApplicationContext(),View_Crops.class));
        }
        else if(id==R.id.nav_edit)
        {
            startActivity(new Intent(getApplicationContext(),View_Crops.class));
        }
        else if(id==R.id.nav_fp)
        {
            startActivity(new Intent(getApplicationContext(),View_Crops.class));
        }
        else if(id==R.id.nav_machinery)
        {
            startActivity(new Intent(getApplicationContext(),View_Machinery.class));
        }
        else if(id==R.id.nav_cropprediction)
        {
            startActivity(new Intent(getApplicationContext(),Weather.class));
        }
        else if(id==R.id.nav_productsadded)
        {
            startActivity(new Intent(getApplicationContext(),View_Crops.class));
        }
        else if(id==R.id.nav_viewproducts)
        {
            startActivity(new Intent(getApplicationContext(),View_Crops.class));
        }

        else if(id==R.id.nav_disease)
        {
            startActivity(new Intent(getApplicationContext(),View_Crops.class));
        }

        else if(id==R.id.nav_notification)
        {
            startActivity(new Intent(getApplicationContext(),View_Crops.class));
        }
        else if(id==R.id.nav_post)
        {
            startActivity(new Intent(getApplicationContext(),View_Crops.class));
        }
        else if(id==R.id.nav_orders)
        {
            startActivity(new Intent(getApplicationContext(),View_Crops.class));
        }
        else if(id==R.id.nav_ordersplaced)
        {
            startActivity(new Intent(getApplicationContext(),View_Crops.class));
        }
        else if(id==R.id.nav_feedback)
        {
            startActivity(new Intent(getApplicationContext(),View_Crops.class));
        }
        else if(id==R.id.nav_contactbook)
        {
            startActivity(new Intent(getApplicationContext(),View_Crops.class));
        }
        else if(id==R.id.nav_complaint)
        {
            startActivity(new Intent(getApplicationContext(),View_Crops.class));
        }
        else if(id==R.id.nav_logout)
        {
            startActivity(new Intent(getApplicationContext(),View_Crops.class));
        }

        return true;
    }
}