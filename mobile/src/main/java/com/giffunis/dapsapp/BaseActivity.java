package com.giffunis.dapsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import FirmaDigital.Comprobar;

public class BaseActivity extends AppCompatActivity {
    Toolbar toolbar;
    Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initToolbar();
        initNavDrawer();
        String clavePublicaServidor = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEBH8Z/WHOHm/ZbDDoFJGy2xobkc5vqssP/iIngDj2gcC751zvKkffEVCMCVvyNzcwfeQOOblwQrKTI5eM3ucuuQ==";
        String mensaje = "Hola Mundo";
        String MensajeFirmado = "MEQCIEW90F/BUqgf8DKAnkZVvepbBT8Wv/A8ACfjiU+nhR3iAiAJQ2O2N3ae/jyloLZ3E9y0qH90gsr1FPKcbF/gtDE92g==";

        try {
            System.out.println(Comprobar.comprobarFirma(clavePublicaServidor,mensaje,MensajeFirmado));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }

    private void initNavDrawer(){
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName(R.string.home).withIdentifier(1);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName(R.string.quizes).withIdentifier(2);

        //create the drawer and remember the `Drawer` result object
        this.drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(this.toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            switch ((int) drawerItem.getIdentifier()) {
                                case 2:
                                    intent = new Intent(BaseActivity.this, QuizesActivity.class);
                                    break;
                            }
                            if (intent != null) {
                                BaseActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
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