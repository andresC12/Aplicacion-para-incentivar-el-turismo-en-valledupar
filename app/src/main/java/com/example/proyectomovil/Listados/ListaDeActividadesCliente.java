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

import com.example.proyectomovil.Controllers.ActividadController;
import com.example.proyectomovil.Controllers.EventoController;
import com.example.proyectomovil.Models.Actividad;
import com.example.proyectomovil.Models.Evento;
import com.example.proyectomovil.R;
import com.example.proyectomovil.Routes.api;
import com.example.proyectomovil.Views.Actividades.ViewActividad;
import com.example.proyectomovil.Views.Eventos.ViewEvento;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListaDeActividadesCliente extends RecyclerView.Adapter<ListaDeActividadesCliente.ViewHolder>{

    private View.OnClickListener listener;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nombre_actividad,  fecha_actividad, calificacion_actividad;
        private LinearLayout layout_actividad;
        private Context context;
        private int posicion = 0;
        private ImageView img_actividad, img_favorito;
        private List<Actividad> actividades = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            nombre_actividad = (TextView) itemView.findViewById(R.id.txt_nombre_actividad_lista_cliente);
            fecha_actividad = (TextView) itemView.findViewById(R.id.txt_lista_actividad_fecha);
            calificacion_actividad = (TextView) itemView.findViewById(R.id.txt_lista_actividad_calificacion);
            img_actividad = (ImageView) itemView.findViewById(R.id.img_actividad_info);
            img_favorito = (ImageView) itemView.findViewById(R.id.img_favorito);
            layout_actividad = (LinearLayout) itemView.findViewById(R.id.layout_actividad_cliente);
        }

        void setOnClickListener(List<Actividad> lista, int poeventon) {
            actividades = lista;
            posicion = poeventon;
            layout_actividad.setOnClickListener(this);
            img_favorito.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.layout_actividad_cliente:
                    Intent activity = new Intent(context, ViewActividad.class);
                    activity.putExtra("id_actividad", actividades.get(posicion).id_actividad);
                    context.startActivity(activity);
                    break;

                case R.id.img_favorito:
                    if(actividades.get(posicion).favorito.equals("1")){
                        ActividadController.quitar_favorito(context, actividades.get(posicion).id_actividad);
                        img_favorito.setImageDrawable(context.getResources().getDrawable(R.drawable.estrella_vacia));
                        Toast.makeText(context, "Se quito de favoritos", Toast.LENGTH_SHORT).show();
                        actividades.get(posicion).favorito = "0";
                    }else{
                        ActividadController.establecer_favorito(context,actividades.get(posicion).id_actividad);
                        img_favorito.setImageDrawable(context.getResources().getDrawable(R.drawable.estrella_llena));
                        Toast.makeText(context, "Se agrego a favoritos", Toast.LENGTH_SHORT).show();
                        actividades.get(posicion).favorito = "1";
                    }
            }
        }

    }

    public List<Actividad> listaactividades;
    public Context contexto;
    public int posi;

    public ListaDeActividadesCliente(List<Actividad> listaActividad, Context contexto) {
        this.listaactividades = listaActividad;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ListaDeActividadesCliente.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_actividades_cliente, parent, false);
        ListaDeActividadesCliente.ViewHolder viewHolder = new ListaDeActividadesCliente.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListaDeActividadesCliente.ViewHolder holder, int position) {
        posi = position;
        holder.nombre_actividad.setText(listaactividades.get(position).nombre);
        holder.fecha_actividad.setText(listaactividades.get(position).fecha);
        holder.calificacion_actividad.setText(listaactividades.get(position).calificacion);

        Picasso.get()
                .load(api.server_imagenes_actividades+listaactividades.get(position).imagen)
                .into(holder.img_actividad);

        if(listaactividades.get(position).favorito.equals("1")){
            holder.img_favorito.setImageDrawable(contexto.getResources().getDrawable(R.drawable.estrella_llena));
        }else{
            holder.img_favorito.setImageDrawable(contexto.getResources().getDrawable(R.drawable.estrella_vacia));
        }

        holder.setOnClickListener(listaactividades, position);
    }


    @Override
    public int getItemCount() {
        return listaactividades.size();
    }


}