package com.example.proyectomovil.Views.Actividades;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectomovil.Controllers.ActividadController;
import com.example.proyectomovil.Controllers.EventoController;
import com.example.proyectomovil.Listados.ListaDeActividadesCliente;
import com.example.proyectomovil.Listados.ListaDeEventosCliente;
import com.example.proyectomovil.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActividadesFavoritas extends Fragment {

    public ActividadesFavoritas() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment =  inflater.inflate(R.layout.fragment_actividades_favoritas, container, false);
        recyclerView = (RecyclerView) fragment.findViewById(R.id.recycler_actividades_favoritas);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));

        ListaDeActividadesCliente adapter = new ListaDeActividadesCliente(new ActividadController().buscarTodosFavoritos(fragment.getContext()), fragment.getContext());
        recyclerView.setAdapter(adapter);
        return fragment;
    }

}
