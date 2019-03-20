package com.example.modulos_utiles.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.modulos_utiles.APIs.API_DatosClientesReporte;
import com.example.modulos_utiles.Adaptadores.AdaptadorPaginasReporte;
import com.example.modulos_utiles.R;
import com.example.modulos_utiles.Utils.DatosTempCliente;
import com.example.modulos_utiles.Utils.LimpiarDatos;
import com.example.modulos_utiles.Utils.Util;

public class Reportes extends AppCompatActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton informacion;
    private View view;
    private TextView tecnico, contrato, estatus;
    private SharedPreferences preferences, preferences2;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

///////////////////////////////////////////////////////////////////////////////////////////////
        preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        preferences2 = getSharedPreferences("datosTempCliente", Context.MODE_PRIVATE);
        editor = preferences2.edit();
        enlaceUI();
        contrato.setText(DatosTempCliente.get_contratoCompRPreference(preferences2));
        tecnico.setText(Util.getNombreTecnicoPreference(preferences));
////////////////////////////////////////////////////////////////////////////////////////////////

        tabLayout = (TabLayout) findViewById(R.id.tabLayout1);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_Horas)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.reportes)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_Material)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_Ejecutar)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.viewPager1);
        PagerAdapter adapter = new AdaptadorPaginasReporte(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        regresar();
        return false;
    }

    private void regresar(){
        if ((position - 1) >= 0) {
            viewPager.setCurrentItem((position - 1));
        }else {
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

    private void enlaceUI() {
        //////////////////////////////////////////////////////////////////////
        tabLayout = (TabLayout) findViewById(R.id.tabLayout1);
        viewPager = (ViewPager) findViewById(R.id.viewPager1);
        tecnico = (TextView) findViewById(R.id.tv_NombreTecnico);
        contrato = (TextView) findViewById(R.id.tv_NContrato);
        ///////////////////////////////////////////////////////////////
        informacion = (ImageButton) findViewById(R.id.btn_Infomacion);
        informacion.setOnClickListener(this);
        view = (View) findViewById(R.id.activityReportes);
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
        if (v == informacion) new API_DatosClientesReporte(this, view);
    }
}



