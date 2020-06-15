package com.example.proyectomovil.Api;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.proyectomovil.Controllers.EventoController;
import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.Listados.ListaDeEventosCliente;
import com.example.proyectomovil.Listados.ListaDeSitiosCliente;
import com.example.proyectomovil.Models.Evento;
import com.example.proyectomovil.Models.Imagenes;
import com.example.proyectomovil.Models.Sitio;
import com.example.proyectomovil.Routes.api;
import com.example.proyectomovil.Views.ListaEventos;
import com.example.proyectomovil.Views.ListaSitios;

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

public class ApiSitios  extends AsyncTask<Void, Void, String> {

    public Context httpContext;


    public ApiSitios(Context context) {
        this.httpContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ListaSitios.activity_sitios.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListaSitios.recyclerView.setVisibility(View.GONE);
                ListaSitios.imagen_loading.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    protected String doInBackground(Void... voids) {


        String ruta = api.getSitios;

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

                JSONArray sitios_json = jo.optJSONArray("sitios");

                SitioController.eliminarTodos(httpContext);


                    for (int j=0; j<sitios_json.length();j++){
                        JSONObject s = sitios_json.getJSONObject(j);
                        Sitio sitio = new Sitio();
                        sitio.id_sitio = s.getInt("id_sitio");
                        sitio.nombre = s.getString("nombre");
                        sitio.descripcion = s.getString("descripcion");
                        sitio.direccion = s.getString("direccion");
                        sitio.tipo = s.getString("tipo");
                        sitio.latitud = Double.parseDouble(s.getString("latitud"));
                        sitio.longitud = Double.parseDouble(s.getString("longitud"));
                        JSONArray imagenes_json = s.optJSONArray("imagenes");
                        for (int k=0; k<imagenes_json.length();k++){
                            JSONObject im = imagenes_json.getJSONObject(k);
                            Imagenes imagen = new Imagenes();
                            imagen.url = im.getString("imagen");
                            sitio.imagenes.add(imagen);
                        }
                        SitioController.guardar(httpContext, sitio);
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
            Toast.makeText(httpContext, resultadoapi, Toast.LENGTH_LONG).show();
        }
        ListaSitios.activity_sitios.runOnUiThread(new Runnable() {
            @Override

            public void run() {
                List<Sitio> lista = new SitioController().buscarTodos(httpContext);
                ListaDeSitiosCliente adapter = new ListaDeSitiosCliente(lista, ListaSitios.activity_sitios);
                ListaSitios.recyclerView.setAdapter(adapter);
                ListaSitios.imagen_loading.setVisibility(View.GONE);
                ListaSitios.recyclerView.setVisibility(View.VISIBLE);

            }
        });
    }
}