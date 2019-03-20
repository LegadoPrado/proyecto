package com.example.modulos_utiles.Fragments.Ordenes.CAPAG;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.modulos_utiles.APIs.API_AparatodelCliente;
import com.example.modulos_utiles.APIs.API_CambioAparato;
import com.example.modulos_utiles.APIs.API_EstatusdelAparato;
import com.example.modulos_utiles.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FTTH extends Fragment {

    private Button btn;

    public FTTH() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ftth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        new API_AparatodelCliente(getActivity(), view);
        new API_EstatusdelAparato(getActivity(), view);
        new API_CambioAparato(getActivity(), view, ((Activity) getActivity()).getIntent().getStringExtra("clave"));

        btn = (Button) view.findViewById(R.id.btn_Aceptar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
