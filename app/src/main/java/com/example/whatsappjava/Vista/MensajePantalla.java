package com.example.whatsappjava.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.whatsappjava.Modelos.Mensaje;
import com.example.whatsappjava.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MensajePantalla extends AppCompatActivity {
    public static FirebaseDatabase firebase;
    public static DatabaseReference database;
    public String idRemitente;
    public String NickUser;
    public String Asunto;
    public String Mensaje;
    TextView etNick;
    TextView etAsunto;
    TextView etMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);
        idRemitente = getIntent().getStringExtra("idRemitente");
        Asunto =  getIntent().getStringExtra("asunto");
        Mensaje =  getIntent().getStringExtra("mensaje");
        getNick();
        etNick=findViewById(R.id.tvNick);
        etAsunto=findViewById(R.id.tvAsunto);
        etMensaje=findViewById(R.id.tvMensaje);
        etNick.setText(NickUser);
        etAsunto.setText(Asunto);
        etMensaje.setText(Mensaje);
    }

    public void getNick(){
        firebase = FirebaseDatabase.getInstance();
        database = firebase.getReference("WhatsappJava").child("Usuarios");
        Query query = database.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    if (idRemitente==child.child("id").getValue()) {
                        NickUser=child.child("nickName").getValue().toString();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void retroceder(View view){
        onBackPressed();
    }
}