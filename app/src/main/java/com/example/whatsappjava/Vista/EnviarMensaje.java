package com.example.whatsappjava.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.whatsappjava.Modelos.Mensaje;
import com.example.whatsappjava.Modelos.Usuario;
import com.example.whatsappjava.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EnviarMensaje extends AppCompatActivity {
    EditText etEmail;
    EditText etAsunto;
    EditText etMensaje;
    Button bEnviar;
    String NickUser;
    String EmailIntent;
    Mensaje newMensaje;
    public int idUser;
    public int idUser2;
    public int idMensaje1;
    public int idMensaje2;
    public ArrayList<Usuario> listaUsuarios;
    public static FirebaseDatabase firebase;
    public static DatabaseReference database;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_mensaje);
        listaUsuarios=new ArrayList<Usuario>();
        EmailIntent = getIntent().getStringExtra("email");
        cargarid();
        obtenerUltimoIdMensaje(idUser2);
        bEnviar=findViewById(R.id.bEnviar);
        etEmail=findViewById(R.id.etEmail);
        etAsunto=findViewById(R.id.etAsunto);
        etMensaje=findViewById(R.id.etMensaje);
        firebase = FirebaseDatabase.getInstance();
        database = firebase.getReference("WhatsappJava").child("Usuarios");
        rellenarListEmail();

        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                bEnviarListener(v);
            }
        });
    }

    public void bEnviarListener(View v){
        if(etEmail.getText().toString().equals("") || etAsunto.getText().toString().equals("") || etMensaje.getText().toString().equals("")) {
            Snackbar.make(v, "Complete todos los campos", Snackbar.LENGTH_SHORT).show();
            limpiar();
        }else{

            for(int i=0;i<listaUsuarios.size();i++){
                if(etEmail.getText().toString().equals(listaUsuarios.get(i).getEmail())){
                    // Snackbar.make(v, "Va bien por ahora", Snackbar.LENGTH_SHORT).show();
                    idUser2=listaUsuarios.get(i).getID();
                    //Snackbar.make(v, "Va bien por ahora "+idUser2, Snackbar.LENGTH_SHORT).show();
                    enviarMensaje();
                }
            }
            onBackPressed();
        }
    }

    public void obtenerUltimoIdMensaje(int id){
        //idMensaje1=0;
        database=FirebaseDatabase.getInstance().getReference().child("WhatsappJava").child("Usuarios").child(id+"").child("Mensajes");
        Query query = database.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    idMensaje1=Integer.parseInt(child.child("id").getValue().toString())+1;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    


    public void enviarMensaje(){
        firebase = FirebaseDatabase.getInstance();
        obtenerUltimoIdMensaje(idUser2);
        database = firebase.getReference("WhatsappJava").child("Usuarios").child(idUser2+"").child("Mensajes");
        database.child(idMensaje1+"").child("id").setValue(idMensaje1+"");
        database.child(idMensaje1+"").child("idRemitente").setValue(idUser);
        database.child(idMensaje1+"").child("idReceptor").setValue(idUser2);
        database.child(idMensaje1+"").child("mensaje").setValue(etMensaje.getText().toString());
        database.child(idMensaje1+"").child("asunto").setValue(etAsunto.getText().toString());


        /*obtenerUltimoIdMensajeR(idUser);
        database = firebase.getReference("WhatsappJava").child("Usuarios").child(idUser+"").child("Mensajes");
        database.child(idMensaje2+"").child("id").setValue(idMensaje2+"");
        database.child(idMensaje2+"").child("idRemitente").setValue(idUser);
        database.child(idMensaje2+"").child("idReceptor").setValue(idUser2);
        database.child(idMensaje2+"").child("mensaje").setValue(etMensaje.getText().toString());
        database.child(idMensaje2+"").child("asunto").setValue(etAsunto.getText().toString());
        database.child(idMensaje2+"").child("enviadoRecibido").setValue("enviado");
        obtenerUltimoIdMensaje(idUser2);*/
    }

    public void rellenarListEmail(){
        listaUsuarios.clear();
        database=FirebaseDatabase.getInstance().getReference().child("WhatsappJava").child("Usuarios");
        Query query = database.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    listaUsuarios.add(new Usuario(Integer.parseInt(child.child("id").getValue().toString()),child.child("email").getValue().toString(),child.child("password").getValue().toString(),child.child("nickName").getValue().toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void cargarid(){
        firebase=FirebaseDatabase.getInstance();
        database=firebase.getReference().child("WhatsappJava").child("Usuarios");
        Query query = database.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    if (EmailIntent.equals(child.child("email").getValue().toString())) {
                        idUser=Integer.parseInt(child.child("id").getValue().toString());
                        //NickUser=child.child("nickName").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void limpiar(){
        etEmail.setText("");
        etAsunto.setText("");
        etMensaje.setText("");
    }
}