package com.example.proyectomovil.Listados;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomovil.Models.Evento;
import com.example.proyectomovil.R;
import com.example.proyectomovil.Views.FormularioEvento;

import java.util.ArrayList;
import java.util.List;

public class ListaDeEventos extends RecyclerView.Adapter<ListaDeEventos.ViewHolder>{

    private View.OnClickListener listener;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nombre_evento;
        private LinearLayout layout_evento;
        private Context context;
        private int posicion = 0;
        private List<Evento> eventos = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            nombre_evento = (TextView) itemView.findViewById(R.id.txt_nombre_evento_listas);
            layout_evento = (LinearLayout) itemView.findViewById(R.id.layout_evento_admi);

        }

        void setOnClickListener(List<Evento> lista, int poeventon) {
            eventos = lista;
            posicion = poeventon;
            layout_evento.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.layout_evento_admi:
                    Intent activity = new Intent(context, FormularioEvento.class);
                    activity.putExtra("id_evento", eventos.get(posicion).id_evento);
                    context.startActivity(activity);
                    break;
            }
        }

    }

    public List<Evento> listaeventos;
    public Context contexto;
    public int posi;

    public ListaDeEventos(List<Evento> listaEvento, Context contexto) {
        this.listaeventos = listaEvento;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ListaDeEventos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_eventos, parent, false);
        ListaDeEventos.ViewHolder viewHolder = new ListaDeEventos.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListaDeEventos.ViewHolder holder, int position) {
        posi = position;
        holder.nombre_evento.setText((position+1) + ". "+ listaeventos.get(position).nombre);
        holder.setOnClickListener(listaeventos, position);
    }


    @Override
    public int getItemCount() {
        return listaeventos.size();
    }


}
