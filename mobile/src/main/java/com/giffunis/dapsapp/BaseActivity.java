package com.giffunis.dapsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;


public class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initToolbar();
        initNavDrawer();
    }

    private void initNavDrawer(){
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName(R.string.home);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName(R.string.quizes);

        //create the drawer and remember the `Drawer` result object
        this.drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(this.toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2
                )
                .build();
    }

    private void initToolbar(){
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}