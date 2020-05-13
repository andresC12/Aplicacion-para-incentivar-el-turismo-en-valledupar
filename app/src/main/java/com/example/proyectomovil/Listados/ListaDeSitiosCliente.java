package com.example.proyectomovil.Listados;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomovil.ListaSitios;
import com.example.proyectomovil.Mapa;
import com.example.proyectomovil.Models.Sitio;
import com.example.proyectomovil.R;
import com.example.proyectomovil.ViewEvento;
import com.example.proyectomovil.Views.FormularioSitio;

import java.util.ArrayList;
import java.util.List;

public class ListaDeSitiosCliente  extends RecyclerView.Adapter<ListaDeSitiosCliente.ViewHolder>{

    private View.OnClickListener listener;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nombre_sitio;
        private ImageView btn_info, btn_map;
        private Context context;
        private int posicion = 0;
        private List<Sitio> sitios = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            nombre_sitio = (TextView) itemView.findViewById(R.id.txt_nombre_sitio_info);
            nombre_sitio = (TextView) itemView.findViewById(R.id.txt_nombre_sitio_info);
            btn_info = (ImageView) itemView.findViewById(R.id.btn_info_sitio);
            btn_map = (ImageView) itemView.findViewById(R.id.btn_pintar_sitio);

        }

        void setOnClickListener(List<Sitio> lista, int position) {
            sitios = lista;
            posicion = position;
            btn_info.setOnClickListener(this);
            btn_map.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btn_info_sitio:

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    // Get the layout inflater
                    LayoutInflater inflater = null;
                    if(ViewEvento.activity_viewEvento != null){
                        inflater = ViewEvento.activity_viewEvento.getLayoutInflater();
                    }
                    if(ListaSitios.activity_sitios != null){
                         inflater = ListaSitios.activity_sitios.getLayoutInflater();
                    }


                    View dialoglayout = inflater.inflate(R.layout.modal_info_sitio, null);

                    TextView txt_nombre_sitio = dialoglayout.findViewById(R.id.label_info_nombre_sitio);
                    TextView txt_descripcion_sitio = dialoglayout.findViewById(R.id.label_info_descripcion_sitio);
                    TextView txt_coordenadas_sitio = dialoglayout.findViewById(R.id.label_info_coordenadas_sitio);

                    txt_nombre_sitio.setText(sitios.get(posicion).nombre);
                    txt_descripcion_sitio.setText(sitios.get(posicion).descripcion);
                    txt_coordenadas_sitio.setText(sitios.get(posicion).latitud+" , "+sitios.get(posicion).longitud);

                    builder.setView(dialoglayout);
                    builder.setPositiveButton("Ver ruta", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(context, Mapa.class);
                            intent.putExtra("id_sitio", sitios.get(posicion).id_sitio);
                            context.startActivity(intent);
                        }
                    }).setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    builder.create();
                    builder.show();
                    break;

                case R.id.btn_pintar_sitio:
                    Intent intent = new Intent(context, Mapa.class);
                    intent.putExtra("id_sitio", sitios.get(posicion).id_sitio);
                    context.startActivity(intent);
                    break;
            }
        }

    }

    public List<Sitio> listaSitios;
    public Context contexto;
    public int posi;

    public ListaDeSitiosCliente(List<Sitio> lista, Context contexto) {
        this.listaSitios = lista;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ListaDeSitiosCliente.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_sitios_cliente, parent, false);
        ListaDeSitiosCliente.ViewHolder viewHolder = new ListaDeSitiosCliente.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListaDeSitiosCliente.ViewHolder holder, int position) {
        posi = position;
        holder.nombre_sitio.setText(listaSitios.get(position).nombre);
        holder.setOnClickListener(listaSitios, position);
    }


    @Override
    public int getItemCount() {
        return listaSitios.size();
    }


}
