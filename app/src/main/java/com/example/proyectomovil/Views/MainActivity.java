package com.example.proyectomovil.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomovil.Controllers.UsuarioController;
import com.example.proyectomovil.Models.Usuario;
import com.example.proyectomovil.R;

public class MainActivity extends AppCompatActivity {

    CheckBox checkRecordar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkRecordar = (CheckBox)findViewById(R.id.checkBtnRecordar);
        if(new UsuarioController().buscar(this).recordar.equals("administrador")){
            Intent activity = new Intent(this, MenuAdministrador.class);
            startActivity(activity);
        }
        if(new UsuarioController().buscar(this).recordar.equals("invitado")){
            Intent activity = new Intent(this, MenuInvitados.class);
            startActivity(activity);
        }
    }

    public void login(View view){
        TextView txt_usuario, txt_clave;
        Usuario usuario = new UsuarioController().buscar(this);
        txt_usuario = (TextView) findViewById(R.id.txt_usuario_login);
        txt_clave = (TextView) findViewById(R.id.txt_clave_login);

        String usu, cla;
        usu = txt_usuario.getText().toString();
        cla = txt_clave.getText().toString();
        if(usuario != null){
            if(usuario.usuario.equals(usu) && usuario.clave.equals(cla)){
                Toast.makeText(this, "Bienvenido administrador", Toast.LENGTH_SHORT).show();
                String recordar = "";
                if(checkRecordar.isChecked()) recordar = "administrador";
                new UsuarioController().recordarOpcionLogin(this, recordar);
                Intent activity = new Intent(this, MenuAdministrador.class);
                startActivity(activity);
            }else{
                Toast.makeText(this, "Credenciales invalidas", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "No existe el usuario", Toast.LENGTH_SHORT).show();
        }
    }

    public void entrarComoInvitado(View view){
        String recordar = "";
        if(checkRecordar.isChecked()) recordar = "invitado";
        new UsuarioController().recordarOpcionLogin(this, recordar);
        Intent activity = new Intent(this, MenuInvitados.class);
        startActivity(activity);
    }
}
