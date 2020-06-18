package com.example.proyectomovil.Listados;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.Routes.api;
import com.example.proyectomovil.Views.Calificacion;
import com.example.proyectomovil.Views.ResultadoBusqueda;
import com.example.proyectomovil.Views.Sitios.ListaSitios;
import com.example.proyectomovil.Views.Mapa.Mapa;
import com.example.proyectomovil.Models.Sitio;
import com.example.proyectomovil.R;
import com.example.proyectomovil.Views.Eventos.ViewEvento;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListaDeSitiosCliente  extends RecyclerView.Adapter<ListaDeSitiosCliente.ViewHolder>{

    private View.OnClickListener listener;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nombre_sitio, calificacion_sitio;
        private ImageView  btn_favorito;
        private LinearLayout btn_info;
        private Context context;
        private int posicion = 0;
        private List<Sitio> sitios = new ArrayList<>();
        private RecyclerView recyclerImagenes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            nombre_sitio = (TextView) itemView.findViewById(R.id.txt_nombre_sitio_info);
            calificacion_sitio = (TextView) itemView.findViewById(R.id.txt_calificacion_sitio_info);
            btn_info = (LinearLayout) itemView.findViewById(R.id.btn_info_sitio);
            btn_favorito = (ImageView) itemView.findViewById(R.id.btn_favorito_sitio);
            recyclerImagenes = (RecyclerView) itemView.findViewById(R.id.recycler_imagenes_sitio);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

            recyclerImagenes.setLayoutManager(layoutManager);

        }

        void setOnClickListener(List<Sitio> lista, int position) {
            sitios = lista;
            posicion = position;
            btn_info.setOnClickListener(this);
            btn_favorito.setOnClickListener(this);
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
                    if(ResultadoBusqueda.activity_busqueda != null){
                        inflater = ResultadoBusqueda.activity_busqueda.getLayoutInflater();
                    }


                    View dialoglayout = inflater.inflate(R.layout.modal_info_sitio, null);
                    ImageView img_sitio = dialoglayout.findViewById(R.id.img_modal_sitio);
                    TextView txt_nombre_sitio = dialoglayout.findViewById(R.id.label_info_nombre_sitio);
                    TextView txt_descripcion_sitio = dialoglayout.findViewById(R.id.label_info_descripcion_sitio);
                    TextView txt_direccion_sitio = dialoglayout.findViewById(R.id.label_info_direccion_sitio);
                    TextView txt_tipo_sitio = dialoglayout.findViewById(R.id.label_info_tipo_sitio);


                    txt_nombre_sitio.setText(sitios.get(posicion).nombre);
                    txt_descripcion_sitio.setText(sitios.get(posicion).descripcion);
                    txt_direccion_sitio.setText(sitios.get(posicion).direccion);
                    txt_tipo_sitio.setText(sitios.get(posicion).tipo);
                    if(sitios.get(posicion).imagenes.size() > 0){
                        Picasso.get()
                                .load(api.server_imagenes_sitios+sitios.get(posicion).imagenes.get(0).url)
                                .into(img_sitio);
                    }
                    builder.setView(dialoglayout);
                    builder.setPositiveButton("Trazar ruta", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(context, Mapa.class);
                            intent.putExtra("id_sitio", sitios.get(posicion).id_sitio);
                            context.startActivity(intent);
                        }
                    }).setNegativeButton("Calificar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(context, Calificacion.class);
                            intent.putExtra("id_accion", sitios.get(posicion).id_sitio);
                            intent.putExtra("accion", "sitio");
                            context.startActivity(intent);
                        }
                    });
                    builder.create();
                    builder.show();
                    break;

                case R.id.btn_favorito_sitio:
                    if(sitios.get(posicion).favorito.equals("1")){
                        SitioController.quitar_favorito(context, sitios.get(posicion).id_sitio);
                        btn_favorito.setImageDrawable(context.getResources().getDrawable(R.drawable.estrella_vacia));
                        Toast.makeText(context, "Se quito de favoritos", Toast.LENGTH_SHORT).show();
                        sitios.get(posicion).favorito = "0";
                    }else{
                        SitioController.establecer_favorito(context, sitios.get(posicion).id_sitio);
                        btn_favorito.setImageDrawable(context.getResources().getDrawable(R.drawable.estrella_llena));
                        Toast.makeText(context, "Se agrego a favoritos", Toast.LENGTH_SHORT).show();
                        sitios.get(posicion).favorito = "1";
                    }
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
        ListaDeImagenesSitio adapter = new ListaDeImagenesSitio(listaSitios.get(position).imagenes,contexto);
        holder.recyclerImagenes.setAdapter(adapter);
        holder.setOnClickListener(listaSitios, position);
        holder.calificacion_sitio.setText(listaSitios.get(position).calificacion);
        if(listaSitios.get(position).favorito.equals("1")){
            holder.btn_favorito.setImageDrawable(contexto.getResources().getDrawable(R.drawable.estrella_llena));
        }else{
            holder.btn_favorito.setImageDrawable(contexto.getResources().getDrawable(R.drawable.estrella_vacia));
        }
    }


    @Override
    public int getItemCount() {
        return listaSitios.size();
    }


}
