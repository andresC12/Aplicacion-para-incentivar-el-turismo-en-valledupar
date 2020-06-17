package com.example.proyectomovil.Api;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.proyectomovil.Controllers.EventoController;
import com.example.proyectomovil.Views.Eventos.ListaEventos;
import com.example.proyectomovil.Listados.ListaDeEventosCliente;
import com.example.proyectomovil.Models.Evento;
import com.example.proyectomovil.Models.Imagenes;
import com.example.proyectomovil.Models.Sitio;
import com.example.proyectomovil.Routes.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ApiEventos extends AsyncTask<Void, Void, String> {

    public Context httpContext;


    public ApiEventos(Context context) {
        this.httpContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ListaEventos.activity_eventos.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListaEventos.recyclerView.setVisibility(View.GONE);
                ListaEventos.imagen_loading.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    protected String doInBackground(Void... voids) {


        String ruta = api.getEventos;

        URL url = null;
        try {
            url = new URL(ruta);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(25000 );
            urlConnection.setConnectTimeout(25000 );
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.flush();
            writer.close();
            os.close();

            int responseCode=urlConnection.getResponseCode();
            if(responseCode== HttpURLConnection.HTTP_OK){
                BufferedReader in= new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer sb= new StringBuffer("");
                String linea="";
                while ((linea=in.readLine())!= null){
                    sb.append(linea);
                    break;
                }
                in.close();
                String json = "";
                json = sb.toString();
                JSONObject jo = null;
                jo = new JSONObject(json);

                JSONArray eventos_json = jo.optJSONArray("eventos");

                EventoController.eliminarTodos(httpContext);

                for (int i=0; i<eventos_json.length();i++){

                    JSONObject r = eventos_json.getJSONObject(i);
                    Evento evento = new Evento();
                    evento.id_evento = r.getInt("id_evento");
                    evento.nombre = r.getString("nombre");
                    evento.descripcion = r.getString("descripcion");
                    evento.imagen = r.getString("imagen");
                    evento.fecha_inicio = r.getString("fecha_inicio");
                    evento.fecha_fin = r.getString("fecha_fin");
                    evento.calificacion = r.getString("calificacion");
                    JSONArray sitios_json = r.optJSONArray("sitios");
                    for (int j=0; j<sitios_json.length();j++){
                        JSONObject s = sitios_json.getJSONObject(j);
                        Sitio sitio = new Sitio();
                        sitio.id_sitio = s.getInt("id_sitio");
                        sitio.nombre = s.getString("nombre");
                        sitio.descripcion = s.getString("descripcion");
                        sitio.tipo = s.getString("tipo");
                        sitio.direccion = s.getString("direccion");
                        sitio.latitud = Double.parseDouble(s.getString("latitud"));
                        sitio.longitud = Double.parseDouble(s.getString("longitud"));
                        sitio.calificacion = s.getString("calificacion");
                        JSONArray imagenes_json = s.optJSONArray("imagenes");
                        for (int k=0; k<imagenes_json.length();k++){
                            JSONObject im = imagenes_json.getJSONObject(k);
                            Imagenes imagen = new Imagenes();
                            imagen.url = im.getString("imagen");
                            sitio.imagenes.add(imagen);
                        }
                        evento.sitios.add(sitio);
                    }
                    new EventoController().guardar(httpContext, evento);
                }
                return "1";
            }

            return "Error. Por favor comuniquese con un asesor ";

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error de red, revise su conexion";
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return  "";
    }
    @Override
    protected void onPostExecute(String resultadoapi) {
        super.onPostExecute(resultadoapi);
        //progressDialog.dismiss();
        if (resultadoapi.equals("1") == false){
            Toast.makeText(httpContext, "No se pudo actualizar la informacion", Toast.LENGTH_LONG).show();
        }
        ListaEventos.activity_eventos.runOnUiThread(new Runnable() {
            @Override

            public void run() {
                List<Evento> lista = new EventoController().buscarTodos(httpContext);
                ListaDeEventosCliente adapter = new ListaDeEventosCliente(lista, ListaEventos.activity_eventos);
                ListaEventos.recyclerView.setAdapter(adapter);
                ListaEventos.imagen_loading.setVisibility(View.GONE);
                ListaEventos.recyclerView.setVisibility(View.VISIBLE);

            }
        });
    }
}