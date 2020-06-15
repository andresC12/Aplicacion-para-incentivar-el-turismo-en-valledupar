package com.example.proyectomovil.Listados;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.proyectomovil.Models.Imagenes;
import com.example.proyectomovil.R;
import com.example.proyectomovil.Routes.api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListaDeImagenesSitio extends RecyclerView.Adapter<ListaDeImagenesSitio.ViewHolder>{

private View.OnClickListener listener;


public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private int posicion = 0;
    private ImageView img_sitio;
    private List<Imagenes> imagenes = new ArrayList<>();


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        img_sitio = (ImageView) itemView.findViewById(R.id.img_sitio);

    }

    void setOnClickListener(List<Imagenes> lista, int posicion) {
        imagenes = lista;
        this.posicion = posicion;
        img_sitio.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_sitio:

            break;
        }
    }

}

    public List<Imagenes> listaimagenes;
    public Context contexto;
    public int posi;

    public ListaDeImagenesSitio(List<Imagenes> listaimagenes, Context contexto) {
        this.listaimagenes = listaimagenes;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ListaDeImagenesSitio.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_imagenes_sitio, parent, false);
        ListaDeImagenesSitio.ViewHolder viewHolder = new ListaDeImagenesSitio.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListaDeImagenesSitio.ViewHolder holder, int position) {
        posi = position;

        Picasso.get()
                .load(api.server_imagenes_sitios+listaimagenes.get(position).url)
                .into(holder.img_sitio);
        holder.setOnClickListener(listaimagenes, position);

    }


    @Override
    public int getItemCount() {
        return listaimagenes.size();
    }


}