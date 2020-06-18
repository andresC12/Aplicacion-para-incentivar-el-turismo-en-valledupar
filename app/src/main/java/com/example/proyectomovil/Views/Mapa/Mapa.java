package com.example.proyectomovil.Views.Mapa;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.Models.Sitio;
import com.example.proyectomovil.R;
import com.example.proyectomovil.Routes.api;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Sitio sitio;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        int id_sitio = getIntent().getIntExtra("id_sitio", 0);
        if (id_sitio != 0){
            sitio = new SitioController().buscar(this, id_sitio);
        }
        LayoutInflater inflater = this.getLayoutInflater();
        view = getWindow().getDecorView();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    public void pintar_ruta(){


            mMap.clear();
            LatLng posicion_actual = new LatLng(getMiPosicion().getLatitude(), getMiPosicion().getLongitude());
            float zoom = 13.7f;
            int pintar_todos_sitios = getIntent().getIntExtra("pintar_todos", 0);

            if(pintar_todos_sitios == 1){
                for(Sitio sitio: new SitioController().buscarTodos(this)){
                    mMap.addMarker(new MarkerOptions().position(new LatLng(sitio.latitud, sitio.longitud))
                        .title(sitio.nombre)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.map)));
                }
            }else{
                    mMap.addMarker(new MarkerOptions().position(new LatLng(sitio.latitud, sitio.longitud))
                            .title(sitio.nombre)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.map)));


            }

            mMap.addMarker(new MarkerOptions().position(posicion_actual)
                    .title("Aqui estoy yo")
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.miposi))
            );

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(posicion_actual)
                    .zoom(zoom)
                    .bearing(360)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));




            if(pintar_todos_sitios != 1){
                Location locacion_punto_destino = new Location(sitio.nombre);
                locacion_punto_destino.setLatitude(sitio.latitud);
                locacion_punto_destino.setLongitude(sitio.longitud);
                float distancia = getMiPosicion().distanceTo(locacion_punto_destino) / 1000;
                DecimalFormat formato1 = new DecimalFormat("#.##");
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(posicion_actual, new LatLng(sitio.latitud, sitio.longitud))
                        .width(5)
                        .color(Color.parseColor("#4e73df")));
                Snackbar.make(view, "La distancia entre su posicion y el sitio escojido es de "+formato1.format(distancia)+" Km.", 10000)
                        .setAction("Action", null).show();
            }


    }
    View modal_info = null;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final Context context = this;
        pintar_ruta();
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                if(modal_info==null){
                    modal_info = getLayoutInflater().inflate(R.layout.info_mapa, null);
                }
                TextView nombre_sitio = modal_info.findViewById(R.id.info_mapa_nombre);
                String info = marker.getTitle();
                nombre_sitio.setText(info);
                ImageView imagen_sitio = modal_info.findViewById(R.id.info_mapa_imagen);
                if(info.equals("Aqui estoy yo") == false){
                    imagen_sitio.setVisibility(View.VISIBLE);
                    Sitio sitio = new SitioController().buscarTodosPorNombre(context, info).get(0);
                    if(sitio != null) {
                        if(sitio.imagenes.get(0) != null){
                            Picasso.get()
                                    .load(api.server_imagenes_sitios + sitio.imagenes.get(0).url)
                                    .into(imagen_sitio);
                        }
                    }
                }else{
                    imagen_sitio.setVisibility(View.GONE);
                }
                    return modal_info;
            }
        });



    }

    public Location getMiPosicion(){
        LocationManager locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return null;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return  location;
    }

    public static float redondearDecimales(float valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = (double) valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return (float)resultado;
    }
}
