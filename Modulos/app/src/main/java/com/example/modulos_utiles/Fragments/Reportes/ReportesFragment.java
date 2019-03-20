package com.example.modulos_utiles.Fragments.Reportes;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.modulos_utiles.APIs.API_QuejasAgendadas;
import com.example.modulos_utiles.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportesFragment extends Fragment {


    public ReportesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reportes, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new API_QuejasAgendadas(view, getContext(), "1", "0", "");
    }

}
