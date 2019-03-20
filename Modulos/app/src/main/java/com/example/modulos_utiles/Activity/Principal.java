package com.example.modulos_utiles.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modulos_utiles.APIs.API_ClvTecnico;
import com.example.modulos_utiles.Fragments.Configuracion.ConfiguracionFragment;
import com.example.modulos_utiles.Fragments.Ordenes.OrdenesFragment;
import com.example.modulos_utiles.Fragments.PrincipalFragment;
import com.example.modulos_utiles.Fragments.Reportes.ReportesFragment;
import com.example.modulos_utiles.R;
import com.example.modulos_utiles.Utils.Util;

public class Principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView textViewNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setToolbar();

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        View nuevoView = navigationView.getHeaderView(0);
        textViewNombre = (TextView) nuevoView.findViewById(R.id.tv_NombreTecnico);
        textViewNombre.setText(Util.getNombreTecnicoPreference(preferences));

        if (savedInstanceState == null) {
            setFragmentByDefault();
        }

    }

    private void setFragmentByDefault() {
        changeFragment(new PrincipalFragment(), navigationView.getMenu().getItem(0));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        boolean fragmentTransaction = false;
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.inicio:
                fragment = new PrincipalFragment();
                fragmentTransaction = true;
                break;
            case R.id.ordenes:
                fragment = new OrdenesFragment();
                fragmentTransaction = true;
                break;
            case R.id.reportes:
                fragment = new ReportesFragment();
                fragmentTransaction = true;
                break;
            case R.id.configuraciones:
                fragment = new ConfiguracionFragment();
                fragmentTransaction = true;
                break;
        }
        if (fragmentTransaction) {
            changeFragment(fragment, menuItem);
            drawerLayout.closeDrawers();
        }
        return true;
    }

    private void changeFragment(Fragment fragment, MenuItem item) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
