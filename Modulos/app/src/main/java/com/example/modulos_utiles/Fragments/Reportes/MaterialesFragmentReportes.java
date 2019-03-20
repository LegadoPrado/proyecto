package com.example.modulos_utiles.Fragments.Reportes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.modulos_utiles.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialesFragmentReportes extends Fragment {


    public MaterialesFragmentReportes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_materiales_fragment_reportes, container, false);
    }

}
