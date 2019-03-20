package com.example.modulos_utiles.Adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.modulos_utiles.Fragments.Reportes.EjecutarFragmentReportes;
import com.example.modulos_utiles.Fragments.Reportes.HorasFragmentReportes;
import com.example.modulos_utiles.Fragments.Reportes.MaterialesFragmentReportes;
import com.example.modulos_utiles.Fragments.Reportes.ReportesFragmentReportes;

public class AdaptadorPaginasReporte extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public AdaptadorPaginasReporte(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new HorasFragmentReportes();
            case 1:
                return new ReportesFragmentReportes();
            case 2:
                return new MaterialesFragmentReportes();
            case 3:
                return new EjecutarFragmentReportes();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
