package com.example.modulos_utiles.Fragments.Ordenes;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modulos_utiles.APIs.API_DCOrdServ;
import com.example.modulos_utiles.APIs.API_TecnicoSec;
import com.example.modulos_utiles.R;
import com.example.modulos_utiles.Utils.DatosOrdTrabajo;
import com.example.modulos_utiles.Utils.DatosTempCliente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.RequiresApi;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorasFragment extends Fragment implements DatePickerDialog.OnDateSetListener, CompoundButton.OnCheckedChangeListener, LocationListener, View.OnClickListener {

    private View view;
    private TextView tv_latitud;
    private TextView tv_longitud;
    private TextView ancla;
    private TextView visita_PrimeraVisita;
    private TextView visita_SegundaVisita;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Calendar calendar = Calendar.getInstance();
    private Spinner spinner;

    public HorasFragment() {
        // Required empty public constructor
    }

    private LocationManager locationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_horas, container, false);
        preferences = getActivity().getSharedPreferences("datosOrdTrabajo", Context.MODE_PRIVATE);
        editor = preferences.edit();
        locationManager = (LocationManager)
                getActivity().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
        return view;
    }

    private RadioButton rb_Visita;
    private RadioButton rb_Ejecutada;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EnlazarUI();
        comprobarGPSActivo();

        new API_DCOrdServ(view, getActivity());
        //Carga espiner de técnicos secundarios
        new API_TecnicoSec(getActivity(), view);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (rb_Ejecutada.isChecked()) {
            view.findViewById(R.id.constrain_Visita).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.constrain_Visita).setEnabled(false);

            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) ancla.getLayoutParams();
            params.setMargins(params.leftMargin, 8, params.rightMargin, params.bottomMargin);
            ancla.setLayoutParams(params);
            editor.putBoolean("rb_ejecutada", rb_Ejecutada.isChecked()).commit();

        } else if (rb_Visita.isChecked()) {
            view.findViewById(R.id.constrain_Visita).setVisibility(View.VISIBLE);
            view.findViewById(R.id.constrain_Visita).setEnabled(true);

            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) ancla.getLayoutParams();
            params.setMargins(params.leftMargin, 190, params.rightMargin, params.bottomMargin);
            ancla.setLayoutParams(params);
            editor.putBoolean("rb_visita", rb_Visita.isChecked()).commit();
        }
    }

    private boolean comprobarGPSActivo() {
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (gpsSignal == 0) {
                //No hay señal de gps(esta desactivado)
                mostrarInformacionDeAlertaGPS();
            } else {
                setearCoordenadas();
                return true;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    private void setearCoordenadas() {

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (location != null) {
            double latitude = location.getLatitude();
            editor.putFloat("latitud", (float) latitude).commit();
            double longitud = location.getLongitude();
            editor.putFloat("longitud", (float) longitud).commit();
            tv_latitud.setText(String.valueOf(latitude));
            tv_longitud.setText(String.valueOf(longitud));
            isCoordenadas = true;
        }
    }

    private void mostrarInformacionDeAlertaGPS() {
        new AlertDialog.Builder(getContext())
                .setTitle("Señal de gps")
                .setMessage("El gps esta desactivado, ¿deseas activarlo?")
                .setPositiveButton("Activar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Es necesario activar el GPS", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private boolean isCoordenadas = false;

    @Override
    public void onLocationChanged(Location location) {
        if (!isCoordenadas) {
            setearCoordenadas();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void EnlazarUI() {
        tv_latitud = (TextView) view.findViewById(R.id.tv_Latitud);
        tv_longitud = (TextView) view.findViewById(R.id.tv_Longitud);
        rb_Visita = (RadioButton) view.findViewById(R.id.rb_Visita);
        rb_Visita.setOnCheckedChangeListener(this);
        rb_Ejecutada = (RadioButton) view.findViewById(R.id.rb_Ejecutada);
        rb_Ejecutada.setOnCheckedChangeListener(this);
        ancla = (TextView) view.findViewById(R.id.tv_textTecSec);
        visita_PrimeraVisita = (TextView) view.findViewById(R.id.tv_PrimerVisita);
        visita_PrimeraVisita.setOnClickListener(this);
        visita_SegundaVisita = (TextView) view.findViewById(R.id.tv_SegundaVisita);
        visita_SegundaVisita.setOnClickListener(this);

        PV = new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        SV = new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        setearDatos();
    }

    private DatePickerDialog PV, SV;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_PrimerVisita:
                PV.show();
                break;
            case R.id.tv_SegundaVisita:
                SV.show();
                break;
        }
    }

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        SharedPreferences preferences = getActivity().getSharedPreferences("datosTempCliente", Context.MODE_PRIVATE);
        String fechaSelect = "" + dayOfMonth + "/" + (month + 1) + "/" + year;

        try {
            Date dateSelect = dateFormat.parse(fechaSelect);
            Date dateActual = new Date();
            String fechaActual = dateFormat.format(dateActual);

            Date date = dateFormat.parse(DatosTempCliente.get_fechaSolPreference(preferences));

            int comparaFecSol = dateSelect.compareTo(date);
            int comparaFecActual = dateSelect.compareTo(dateFormat.parse(fechaActual));

            //compara puede ser 0 o mayor a 0 y comparaFecActual solo puede ser 0 o menor a 0
            if (view == PV.getDatePicker()) {
                if (comparaFecSol >= 0 & comparaFecActual <= 0) {
                    visita_PrimeraVisita.setText(dateFormat.format(dateSelect));
                    editor.putString("visita1", dateFormat.format(dateSelect)).commit();
                } else {
                    visita_PrimeraVisita.setText("");
                    Toast.makeText(getActivity(), "La fecha seleccionada no esta dentro del rango permitido", Toast.LENGTH_LONG).show();
                }
            }
            if (view == SV.getDatePicker()) {
                visita_SegundaVisita.setText((dateFormat.format(dateSelect)));
                editor.putString("visita2", dateFormat.format(dateSelect)).commit();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setearDatos() {
        visita_PrimeraVisita.setText(DatosOrdTrabajo.get_Visita1(preferences));
        visita_SegundaVisita.setText(DatosOrdTrabajo.get_Visita2(preferences));
        rb_Ejecutada.setChecked(DatosOrdTrabajo.isEjecutada(preferences));
        rb_Visita.setChecked(DatosOrdTrabajo.isVisita(preferences));
    }
}
