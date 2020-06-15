package com.example.proyectomovil.Listados;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomovil.Controllers.EventoController;
import com.example.proyectomovil.Models.Evento;
import com.example.proyectomovil.R;
import com.example.proyectomovil.Routes.api;
import com.example.proyectomovil.Views.ViewEvento;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListaDeEventosCliente extends RecyclerView.Adapter<ListaDeEventosCliente.ViewHolder>{

    private View.OnClickListener listener;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nombre_evento, descripcion_evento, fecha_evento;
        private LinearLayout layout_evento;
        private Context context;
        private int posicion = 0;
        private ImageView img_evento, img_favorito;
        private List<Evento> eventos = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            nombre_evento = (TextView) itemView.findViewById(R.id.txt_nombre_evento_lista_cliente);
            fecha_evento = (TextView) itemView.findViewById(R.id.txt_lista_evento_fecha);
            descripcion_evento = (TextView) itemView.findViewById(R.id.txt_descripcion_evento_lista_cliente);
            img_evento = (ImageView) itemView.findViewById(R.id.img_evento_info);
            img_favorito = (ImageView) itemView.findViewById(R.id.img_favorito);
            layout_evento = (LinearLayout) itemView.findViewById(R.id.layout_evento_cliente);
        }

        void setOnClickListener(List<Evento> lista, int poeventon) {
            eventos = lista;
            posicion = poeventon;
            layout_evento.setOnClickListener(this);
            img_favorito.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.layout_evento_cliente:
                    Intent activity = new Intent(context, ViewEvento.class);
                    activity.putExtra("id_evento", eventos.get(posicion).id_evento);
                    context.startActivity(activity);
                    break;

                case R.id.img_favorito:
                    if(eventos.get(posicion).favorito.equals("1")){
                        EventoController.quitar_favorito(context, eventos.get(posicion).id_evento);
                        img_favorito.setImageDrawable(context.getResources().getDrawable(R.drawable.estrella_vacia));
                        Toast.makeText(context, "Se quito de favoritos", Toast.LENGTH_SHORT).show();
                        eventos.get(posicion).favorito = "0";
                    }else{
                        EventoController.establecer_favorito(context, eventos.get(posicion).id_evento);
                        img_favorito.setImageDrawable(context.getResources().getDrawable(R.drawable.estrella_llena));
                        Toast.makeText(context, "Se agrego a favoritos", Toast.LENGTH_SHORT).show();
                        eventos.get(posicion).favorito = "1";
                    }
            }
        }

    }

    public List<Evento> listaeventos;
    public Context contexto;
    public int posi;

    public ListaDeEventosCliente(List<Evento> listaEvento, Context contexto) {
        this.listaeventos = listaEvento;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ListaDeEventosCliente.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_eventos_cliente, parent, false);
        ListaDeEventosCliente.ViewHolder viewHolder = new ListaDeEventosCliente.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListaDeEventosCliente.ViewHolder holder, int position) {
        posi = position;
        holder.nombre_evento.setText(listaeventos.get(position).nombre);
        holder.descripcion_evento.setText(listaeventos.get(position).descripcion);
        holder.fecha_evento.setText(listaeventos.get(position).fecha_inicio);

        Picasso.get()
                .load(api.server_imagenes_eventos+listaeventos.get(position).imagen)
                .into(holder.img_evento);

        if(listaeventos.get(position).favorito.equals("1")){
            holder.img_favorito.setImageDrawable(contexto.getResources().getDrawable(R.drawable.estrella_llena));
        }else{
            holder.img_favorito.setImageDrawable(contexto.getResources().getDrawable(R.drawable.estrella_vacia));
        }

        holder.setOnClickListener(listaeventos, position);
    }


    @Override
    public int getItemCount() {
        return listaeventos.size();
    }


}