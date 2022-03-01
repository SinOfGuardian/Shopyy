package com.example.aredoweknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton browser_btn;
    FloatingActionButton scan_btn;
    FloatingActionButton add_btn;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    public static TextView resulttextview;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        resulttextview = findViewById(R.id.dashboard);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Navigation Drawer");
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setTitle("STORE PURING");
        toolbar.setSubtitle("Neneng");

        //Open Web View
        browser_btn = findViewById(R.id.browser_button);
        browser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, Web.class);
                startActivity(intent);
            }
        });
        //Open camera for scan
        scan_btn = findViewById(R.id.scan_button);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, Scanner.class);
                startActivity(intent);
            }
        });
        add_btn = findViewById(R.id.add_button);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, Scanner.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //Check to see which item was being clicked and perform appropriate action
        switch (item.getItemId()) {
            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.nav_home:
                Toast.makeText(this,"Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_other_apps:
                Toast.makeText(this,"Other Apps", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_feedback:
                Toast.makeText(this,"Feedback", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                Toast.makeText(this,"Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_rate:
                Toast.makeText(this,"Rate Us", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
