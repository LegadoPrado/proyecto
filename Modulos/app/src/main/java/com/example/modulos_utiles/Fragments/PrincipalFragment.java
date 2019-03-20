package com.example.modulos_utiles.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.modulos_utiles.APIs.API_OrdenesQuejasTotales;
import com.example.modulos_utiles.APIs.API_SiguienteCita;
import com.example.modulos_utiles.R;
import com.github.mikephil.charting.charts.PieChart;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrincipalFragment extends Fragment {

    private PieChart graficaPastel;

    public PrincipalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new API_SiguienteCita(getActivity(), view);
        new API_OrdenesQuejasTotales(getActivity(), view);
    }
}
