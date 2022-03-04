package com.example.aredoweknow;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
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
    private ActivityDashboardNewBinding binding;

    FloatingActionButton browser_btn, scan_btn, add_btn;
    NavigationView logout_btn;

    private String user_name = "";
    private String store_name = "";

    private Toolbar tool_bar;
    private Database db;

    final String reset = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarDashboardNew.toolbar);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("username");
        store_name = intent.getStringExtra("store");

        tool_bar = (Toolbar) findViewById(R.id.toolbar);
        tool_bar.setTitle(store_name);
        tool_bar.setSubtitle(user_name);

        db = new Database(this);
        String res[] = db.getCredentials();
        if (res == null) {
            db.addToCred(user_name, store_name);
        } else {
            db.updateCred(user_name, store_name, reset);
        }

//        binding.appBarDashboardNew.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_list, R.id.nav_feedback, R.id.nav_about).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard_new);
        NavigationUI.setupActionBarWithNavController( this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //--------------------------------Open Web View
        browser_btn = findViewById(R.id.fab1);
        browser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardNew.this, Web.class);
                intent.putExtra("ToSearch", "");
                startActivity(intent);
            }
        });

        //---------------------------------Open camera for scan
        scan_btn = findViewById(R.id.fab2);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardNew.this, Scanner.class);
                intent.putExtra("Search", true);
                startActivity(intent);
            }
        });
        add_btn = findViewById(R.id.fab3);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardNew.this, AddItem.class);
                startActivity(intent);
            }
        });


    }

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
        Toast.makeText(getApplicationContext(), "Hello Javatpoint", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            new AlertDialog.Builder(this)
                    .setIcon(getDrawable(R.drawable.shopyy_icon))
                    .setTitle("Shopyy")
                    .setMessage("Do you want to Logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.updateCred("","",user_name);
                            finish();
                            Intent login = new Intent(DashboardNew.this, Login.class);
                            startActivity((Intent) login);              // --> Start Login Activity
                        }

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
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                }).setNegativeButton("No", null).show();
    }
}