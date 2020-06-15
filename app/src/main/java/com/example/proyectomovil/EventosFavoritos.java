package com.example.proyectomovil;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectomovil.Api.ApiEventos;
import com.example.proyectomovil.Controllers.EventoController;
import com.example.proyectomovil.Listados.ListaDeEventosCliente;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventosFavoritos extends Fragment {


    public EventosFavoritos() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                // Inflate the layout for this fragment

        View fragment =  inflater.inflate(R.layout.fragment_eventos_favoritos, container, false);
        recyclerView = (RecyclerView) fragment.findViewById(R.id.recycler_eventos_favoritos);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));

        ListaDeEventosCliente adapter = new ListaDeEventosCliente(new EventoController().buscarTodosFavoritos(fragment.getContext()), fragment.getContext());
        recyclerView.setAdapter(adapter);
        return fragment;
    }

}
