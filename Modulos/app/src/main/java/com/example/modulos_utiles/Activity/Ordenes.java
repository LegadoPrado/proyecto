package com.example.modulos_utiles.Activity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modulos_utiles.APIs.API_DatosClienteInfo;
import com.example.modulos_utiles.Adaptadores.AdaptadorPaginasOrden;
import com.example.modulos_utiles.R;
import com.example.modulos_utiles.Utils.LimpiarDatos;
import com.example.modulos_utiles.Utils.Util;

import androidx.annotation.RequiresApi;

public class Ordenes extends AppCompatActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tecnico;
    private TextView contrato;
    private TextView estatus;
    private ImageButton informacion;
    private SharedPreferences preferences, preferences2;
    private SharedPreferences.Editor editor;
    private View view;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);

        SharedPreferences preferences = getSharedPreferences("datosOrdTrabajo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        preferences2 = getSharedPreferences("datosTempCliente", Context.MODE_PRIVATE);
        editor = preferences2.edit();

        enlaceUI();

        contrato.setText(getIntent().getStringExtra("nContrato"));
        //estatus.setText(getString(validacion()));


        tecnico.setText(Util.getNombreTecnicoPreference(preferences));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_Trabajo)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_Horas)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_Material)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_Ejecutar)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        PagerAdapter adapter = new AdaptadorPaginasOrden(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(this);
        validar_Permiso();
    }

    @Override
    public boolean onSupportNavigateUp() {
        regresar();
        return false;
    }

    int position = 0;

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        position = tab.getPosition();
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void regresar() {
        if ((position - 1) >= 0) {
            viewPager.setCurrentItem((position - 1));
        } else {
            SharedPreferences preferences = getSharedPreferences("datosOrdTrabajo", Context.MODE_PRIVATE);
            LimpiarDatos.limpiarDatos(preferences);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        regresar();
    }

    @Override
    protected void onDestroy() {
        SharedPreferences preferences = getSharedPreferences("datosOrdTrabajo", Context.MODE_PRIVATE);
        LimpiarDatos.limpiarDatos(preferences);
        super.onDestroy();
    }

    private void enlaceUI() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tecnico = (TextView) findViewById(R.id.tv_NombreTecnico);
        contrato = (TextView) findViewById(R.id.tv_NContrato);
        //estatus = (TextView) findViewById(R.id.tv_Estatus);
        informacion = (ImageButton) findViewById(R.id.btn_Infomacion);
        informacion.setOnClickListener(this);
        view = (View) findViewById(R.id.activityOrdenes);
    }

    //e p v
    private int validacion() {
        if (getIntent().getStringExtra("estatus").equals("P")) {
            return R.string.pendiente;
        }
        if (getIntent().getStringExtra("estatus").equals("E")) {
            return R.string.ejecutada;
        }
        if (getIntent().getStringExtra("estatus").equals("V")) {
            return R.string.visita;
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        if (v == informacion) new API_DatosClienteInfo(this, view);
    }

    private void validar_Permiso() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1008);
        } else {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1008) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            } else {
                Toast.makeText(this, "Debe aceptar los permisos", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
