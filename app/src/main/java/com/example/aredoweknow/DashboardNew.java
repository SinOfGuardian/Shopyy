package com.example.aredoweknow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.example.aredoweknow.databases_folder.Database;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aredoweknow.databinding.ActivityDashboardNewBinding;

public class DashboardNew extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AppBarConfiguration mAppBarConfiguration;

    private String user_name = "";
    private String store_name = "";
    final String reset = "";

    private Toolbar tool_bar;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDashboardNewBinding binding = ActivityDashboardNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarDashboardNew.toolbarr);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("username");
        store_name = intent.getStringExtra("store");

        tool_bar = (Toolbar) findViewById(R.id.toolbarr);
        tool_bar.setTitle(store_name);
        tool_bar.setSubtitle(user_name);

        db = new Database(this);
        String[] res = db.getCredentials();
        if (res == null) {
            db.addToCred(user_name, store_name);
        } else {
            db.updateCred(user_name, store_name, reset);
        }

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_list, R.id.nav_feedback, R.id.nav_about).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard_new);
        NavigationUI.setupActionBarWithNavController( this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }//DULO NG onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard_new, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard_new);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            new AlertDialog.Builder(this)
                    .setIcon(getDrawable(R.drawable.logout_24px))
                    .setTitle("Shopyy")
                    .setMessage("Do you want to Logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        db.updateCred("","",user_name);
                        finish();
                        Intent login = new Intent(DashboardNew.this, Login.class);
                        startActivity((Intent) login);              // --> Start Login Activity
                    }).setNegativeButton("No", null).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(getDrawable(R.drawable.shopyy_icon))
                .setTitle("Shopyy")
                .setMessage("Are you sure do you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> finish()
                ).setNegativeButton("No", null).show();
    }

}