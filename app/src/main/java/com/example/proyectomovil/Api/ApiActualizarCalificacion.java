package com.example.proyectomovil.Api;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.proyectomovil.Controllers.CalificacionController;
import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.Listados.ListaDeSitiosCliente;
import com.example.proyectomovil.Models.Imagenes;
import com.example.proyectomovil.Models.Sitio;
import com.example.proyectomovil.Routes.api;
import com.example.proyectomovil.Views.Calificacion;
import com.example.proyectomovil.Views.Sitios.ListaSitios;

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
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

public class ApiActualizarCalificacion  extends AsyncTask<Void, Void, String> {

    public Context httpContext;
    public int calificacion_nueva, calificacion_antigua, id_accion;
    public String accion;
    ProgressDialog dialog;
    public ApiActualizarCalificacion(Context context, String accion,int id_accion, int calificacion_nueva, int calificacion_antigua) {
        this.httpContext = context;
        this.calificacion_antigua = calificacion_antigua;
        this.calificacion_nueva = calificacion_nueva;
        this.id_accion = id_accion;
        this.accion = accion;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(httpContext, "Calificacion", "Enviando calificacion...");

    }
    @Override
    protected String doInBackground(Void... voids) {


        String ruta = api.actualizarCalificacion;

        URL url = null;
        try {
            url = new URL(ruta);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(25000 );
            urlConnection.setConnectTimeout(25000 );
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            JSONObject data_post= new JSONObject();
            data_post.put("id_accion",id_accion);
            data_post.put("accion",accion);
            data_post.put("calificacion_antigua",calificacion_antigua);
            data_post.put("calificacion_nueva",calificacion_nueva);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(data_post));
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

                String error = jo.getString("error");

                return error;
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
        dialog.dismiss();

        if(resultadoapi.equals("0")) {
            if (CalificacionController.actualizarCalificacion(httpContext, accion, id_accion, calificacion_nueva)) {
                Toast.makeText(httpContext, "Se guardo correctamente tu calificacion", Toast.LENGTH_LONG).show();
                Calificacion.activity_calificaciones.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Calificacion.activity_calificaciones.onBackPressed();
                    }
                });
            } else {
                Toast.makeText(httpContext, "No se pudo guardar calificacion", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(httpContext, "Ocurrio un error al guardar la calificacion", Toast.LENGTH_LONG).show();
        }

    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}