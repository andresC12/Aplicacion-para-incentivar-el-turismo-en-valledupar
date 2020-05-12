package com.example.proyectomovil.Listados;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomovil.Models.Sitio;
import com.example.proyectomovil.R;
import com.example.proyectomovil.Views.FormularioSitio;

public class ListaDeSitios extends RecyclerView.Adapter<ListaDeSitios.ViewHolder>{

    private View.OnClickListener listener;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nombre_sitio;
        private LinearLayout layout_sitio;
        private Context context;
        private int posicion = 0;
        private List<Sitio> sitios = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            nombre_sitio = (TextView) itemView.findViewById(R.id.txt_nombre_sitio_listas);
            layout_sitio = (LinearLayout) itemView.findViewById(R.id.layout_sitio_admi);

        }

        void setOnClickListener(List<Sitio> lista, int position) {
            sitios = lista;
            posicion = position;
            layout_sitio.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.layout_sitio_admi:
                    Intent activity = new Intent(context, FormularioSitio.class);
                    activity.putExtra("id_sitio", sitios.get(posicion).id_sitio);
                    context.startActivity(activity);
                    break;
            }
        }

    }

    public List<Sitio> listaSitios;
    public Context contexto;
    public int posi;

    public ListaDeSitios(List<Sitio> lista, Context contexto) {
        this.listaSitios = lista;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_sitios, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        posi = position;
        holder.nombre_sitio.setText((position+1) + ". "+ listaSitios.get(position).nombre);
        holder.setOnClickListener(listaSitios, position);
    }


    @Override
    public int getItemCount() {
        return listaSitios.size();
    }


}
