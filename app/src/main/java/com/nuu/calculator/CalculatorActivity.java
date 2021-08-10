package com.nuu.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.nuu.calculator.databinding.ActivityCalculatorBinding;

public class CalculatorActivity extends AppCompatActivity {
    private CalculatorViewModel calculatorViewModel = new CalculatorViewModel();

    private ActivityCalculatorBinding mainBinding;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setMainBinding();
        initListener();
        initData();
        View view = mainBinding.getRoot();
        setContentView(view);
        context = this;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void setMainBinding(){
        mainBinding = ActivityCalculatorBinding.inflate(getLayoutInflater());
        drawer = mainBinding.drawerLayout;
        mainBinding.detailView.setViewModel(calculatorViewModel);
        toolbar = mainBinding.detailView.toolbar;
        setSupportActionBar(toolbar);
    }

    private void initListener(){
        mainBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawer.closeDrawer(GravityCompat.START);
                int id = item.getItemId();
                switch (id){
                    case R.id.nav_home:
                        new AlertDialog.Builder(context)
                                .setTitle(getString(R.string.logout_msg))
                                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPreferences preferences = context.getSharedPreferences("CalculatorMain", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.clear();
                                        editor.commit();
                                        goLoginActivity();
                                    }
                                }).setNegativeButton(R.string.cancel,null).create()
                                .show();
                        return true;
                }
                return false;
            }
        });
    }

    private void initData(){
        String name = getIntent().getStringExtra("name");
        View header = mainBinding.navView.getHeaderView(0);
        TextView tvName = (TextView) header.findViewById(R.id.tv_Name);
        tvName.setText("hi~ "+name);
    }

    private void goLoginActivity(){
        Intent intent  = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}