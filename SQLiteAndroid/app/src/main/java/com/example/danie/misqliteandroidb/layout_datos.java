package com.example.danie.misqliteandroidb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danie.misqliteandroidb.DAOS.Contacto;
import com.example.danie.misqliteandroidb.DAOS.DaoContactos;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class layout_datos extends AppCompatActivity {

    Button guardar;
    Button actualizar;
    Button eliminar;
    EditText id;
    EditText nombre;
    EditText email;
    EditText twiter;
    EditText tel;
    EditText fec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_datos);
        guardar=(Button)findViewById(R.id.btnguardar);
        actualizar = (Button)findViewById(R.id.btnactualizar);
        eliminar = (Button)findViewById(R.id.btneliminar);
        id = (EditText) findViewById(R.id.txtid);
        nombre = (EditText) findViewById(R.id.txtnombre);
        email = (EditText) findViewById(R.id.txtemail);
        twiter = (EditText) findViewById(R.id.txttwiter);
        tel = (EditText) findViewById(R.id.txttel);
        fec = (EditText) findViewById(R.id.txtfecha);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Eliminar();
            }
        });

        buscarcontacto();
    }

    public void buscarcontacto(){
        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Pattern p = Pattern.compile("[0-9]+");
                DaoContactos dao = new DaoContactos(layout_datos.this);
                ArrayList<Contacto> contacto;

                contacto = (ArrayList<Contacto>) dao.obtenercontacto(id.getText().toString());
                if(id.getText().length()>0 && p.matcher(id.getText().toString()).matches()==true && contacto.size()>0) {

                    nombre.setText(contacto.get(0).getNombre());
                    email.setText(contacto.get(0).getCorreo_electronico());
                    twiter.setText(contacto.get(0).getTwitter());
                    tel.setText(contacto.get(0).getTelefono());
                    fec.setText(contacto.get(0).getFecha_nacimiento());
                }else{
                    nombre.setText("");
                    email.setText("");
                    twiter.setText("");
                    tel.setText("");
                    fec.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    public void actualizar(){

                try {
                    DaoContactos buscar = new DaoContactos(layout_datos.this);

                    Contacto contacto1 = new Contacto(Integer.parseInt(id.getText().toString()),
                            nombre.getText().toString(),
                            email.getText().toString(),
                            twiter.getText().toString(),
                            tel.getText().toString(),
                            fec.getText().toString());

                        if (buscar.update(contacto1) > 0) {
                            Toast.makeText(getBaseContext(), "Contacto Actualizado", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getBaseContext(), "Ocurrio un Error al Actualizar", Toast.LENGTH_SHORT).show();
                        }

                }catch (Exception err){
                    Toast.makeText(getBaseContext(),err.getMessage(),Toast.LENGTH_LONG).show();
                }

    }


    public void insertar(){
                Intent atras = new Intent();
                    Contacto alum = new Contacto();
                    alum.setNombre(nombre.getText().toString());
                    alum.setCorreo_electronico(email.getText().toString());
                    alum.setTwitter(twiter.getText().toString());
                    alum.setTelefono(tel.getText().toString());
                    alum.setFecha_nacimiento(fec.getText().toString());
                    atras.putExtra("c", alum);
                    setResult(RESULT_OK, atras);
                    finish();
    }

    public void Eliminar(){

                try {
                    DaoContactos dao = new DaoContactos(layout_datos.this);
                    Pattern p = Pattern.compile("[0-9]+");
                    if(id.getText().length()>0 && p.matcher(id.getText().toString()).matches()==true) {
                        if(dao.delete(id.getText().toString())>0){
                            Toast.makeText(getBaseContext(),"Contacto Eliminado",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getBaseContext(),"Contacto no se pudo Eliminado",Toast.LENGTH_SHORT).show();
                        }
                    }

                }catch (Exception err){
                    Toast.makeText(getBaseContext(),err.getMessage(),Toast.LENGTH_LONG).show();
                }

    }



}
