package com.example.whatsappjava.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.whatsappjava.Modelos.Mensaje;
import com.example.whatsappjava.R;
import com.google.android.material.snackbar.Snackbar;
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
    public String Asunto;
    public String Mensaje;
    TextView etAsunto;
    TextView etMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);
        idRemitente = getIntent().getStringExtra("idRemitente");
        Asunto =  getIntent().getStringExtra("asunto");
        Mensaje =  getIntent().getStringExtra("mensaje");
        etAsunto=findViewById(R.id.tvAsunto);
        etMensaje=findViewById(R.id.tvMensaje);
        etAsunto.setText(Asunto);
        etMensaje.setText(Mensaje);
    }

    public void retroceder(View view){
        onBackPressed();
    }
}