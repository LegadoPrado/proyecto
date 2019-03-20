package com.example.modulos_utiles.Adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.modulos_utiles.Fragments.Ordenes.EjecutarFragment;
import com.example.modulos_utiles.Fragments.Ordenes.HorasFragment;
import com.example.modulos_utiles.Fragments.Ordenes.MaterialFragment;
import com.example.modulos_utiles.Fragments.Ordenes.TrabajoFragment;

public class AdaptadorPaginasOrden extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public AdaptadorPaginasOrden(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new TrabajoFragment();
            case 1:
                return new HorasFragment();
            case 2:
                return new MaterialFragment();
            case 3:
                return new EjecutarFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
