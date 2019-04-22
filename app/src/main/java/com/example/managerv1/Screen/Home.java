package com.example.managerv1.Screen;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.managerv1.Fragment.GraphFragment;
import com.example.managerv1.Fragment.Loading;
import com.example.managerv1.Fragment.OrderFragment;
import com.example.managerv1.Fragment.ProductFragment;
import com.example.managerv1.Fragment.StaffFragment;
import com.example.managerv1.R;

public class Home extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener ,
        NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout Drawer;
    Fragment selectedFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //initalize toolbar and Action bar
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("My Dashbroad");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        //initalize Bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        //initalize Drawer amd left navigation view
        Drawer= findViewById(R.id.Drawer);
        NavigationView NavigationView =findViewById(R.id.nav);
        NavigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new GraphFragment()).commit();
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                Drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            //left Navigation controller
            case R.id.Lognav:
                Drawer.closeDrawers();
                break;
            case R.id.settingnav:
                Drawer.closeDrawers();
                break;
            case R.id.logoutnav:
                Drawer.closeDrawers();
                break;
                //Bottom Navigation controller
            case R.id.Graph:
                selectedFragment=new Loading();
                break;
            case R.id.Product:
                selectedFragment=new Loading();
                break;
            case R.id.Staff:
                selectedFragment=new Loading();
                break;
            case R.id.Order:
                selectedFragment=new Loading();
                break;
        }
        getSupportFragmentManager().beginTransaction(). replace(R.id.fragment_container,selectedFragment).commit();
        return true;
    }
}
