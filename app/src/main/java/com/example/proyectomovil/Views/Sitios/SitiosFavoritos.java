package com.example.proyectomovil.Views.Sitios;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectomovil.Controllers.EventoController;
import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.Listados.ListaDeEventosCliente;
import com.example.proyectomovil.Listados.ListaDeSitiosCliente;
import com.example.proyectomovil.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SitiosFavoritos extends Fragment {


    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment =  inflater.inflate(R.layout.fragment_sitios_favoritos, container, false);
        recyclerView = (RecyclerView) fragment.findViewById(R.id.recycler_sitios_favoritos);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));

        ListaDeSitiosCliente adapter = new ListaDeSitiosCliente(new SitioController().buscarTodosFavoritos(fragment.getContext()), fragment.getContext());
        recyclerView.setAdapter(adapter);
        return fragment;
    }

}
