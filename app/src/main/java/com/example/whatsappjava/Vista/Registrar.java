package com.example.whatsappjava.Vista;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

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

public class Registrar extends AppCompatActivity {
    public static FirebaseDatabase firebase;
    public static DatabaseReference database;
    ArrayList<String> lEmailUser;
    Usuario newUsuario;
    public static int idUsuario;
    EditText tEmail;
    EditText tContrasena;
    EditText tNick;
    Button breg;
    static int aux=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        lEmailUser=new ArrayList<>();
        tEmail = findViewById(R.id.etEmailReg);
        tContrasena = findViewById(R.id.etPasswordReg);
        tNick = findViewById(R.id.etNickReg);
        breg = findViewById(R.id.bRegistrar);
        obtenerUltimoIdUser();
        rellenarListEmail();
        breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compRepEmail(v);
            }
        });

    }



    public void obtenerUltimoIdUser(){
        database=FirebaseDatabase.getInstance().getReference().child("WhatsappJava").child("Usuarios");
        Query query = database.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Log.d("Key Usuario", child.getKey());
                    idUsuario=Integer.parseInt(child.child("id").getValue().toString())+1;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void compRepEmail(View v) {
        if (tEmail.getText().toString().equals("") || tContrasena.getText().toString().equals("") || tNick.getText().toString().equals("")) {
            Snackbar.make(v, "Complete todos los campos", Snackbar.LENGTH_SHORT).show();
        }else{
            qEmailRep();
            accerder(v);
        }
    }

    public void accerder(View v){
        if(aux==0){
        firebase= FirebaseDatabase.getInstance();
            obtenerUltimoIdUser();
            Snackbar.make(v, "Compruebe los campos de email y grupo", Snackbar.LENGTH_SHORT).show();
            newUsuario = new Usuario(idUsuario,tEmail.getText().toString(),tContrasena.getText().toString(),tNick.getText().toString());
            database = firebase.getReference("WhatsappJava").child("Usuarios").child(newUsuario.getID()+"");
            database.setValue(newUsuario);
            Snackbar.make(v, "Registro", Snackbar.LENGTH_LONG).show();
            onBackPressed();
        }else{
            Snackbar.make(v, "El email ya esta en uso", Snackbar.LENGTH_SHORT).show();
        }
        Log.d("ENTRAAA", " Auxiliar"+aux);
    }
    public void qEmailRep(){
        aux=0;
        for(int i=0;i<lEmailUser.size();i++){
            if(lEmailUser.get(i).equals(tEmail.getText().toString())){
                aux=-1;
            }
        }
    }

    public void rellenarListEmail(){
        lEmailUser.clear();
        database=FirebaseDatabase.getInstance().getReference().child("WhatsappJava").child("Usuarios");
        Query query = database.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    lEmailUser.add(child.child("email").getValue().toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void retrocederListener(View v){
        onBackPressed();
    }
}