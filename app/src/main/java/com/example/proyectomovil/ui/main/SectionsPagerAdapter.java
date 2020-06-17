package com.example.proyectomovil.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.proyectomovil.Views.Actividades.ActividadesFavoritas;
import com.example.proyectomovil.Views.Eventos.EventosFavoritos;
import com.example.proyectomovil.R;
import com.example.proyectomovil.Views.Sitios.SitiosFavoritos;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,  R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                EventosFavoritos fragment_eventos = new EventosFavoritos();
                return fragment_eventos;
            case 1:
                ActividadesFavoritas fragment_actividades = new ActividadesFavoritas();
                return fragment_actividades;
            case 2:
                SitiosFavoritos fragment_sitios = new SitiosFavoritos();
                return fragment_sitios;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}