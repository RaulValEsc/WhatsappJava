package com.example.whatsappjava.Vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.whatsappjava.Controladores.MensajeAdapter;
import com.example.whatsappjava.Modelos.Mensaje;
import com.example.whatsappjava.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewMensajes extends AppCompatActivity {
    public static FirebaseDatabase firebase;
    public static DatabaseReference database;
    public static ArrayList<Mensaje> listamensajes;
    public static RecyclerView rv;

    public String EmailIntent;
    int idUser;
    public String NickUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_mensajes);
        EmailIntent = getIntent().getStringExtra("email");
        listamensajes=new ArrayList<>();
        rv=findViewById(R.id.rvRecycler);
        idAndNickUser(this);
    }
    public void idAndNickUser(final Context context){
        firebase = FirebaseDatabase.getInstance();
        database = firebase.getReference("WhatsappJava").child("Usuarios");
        Query query = database.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    if (EmailIntent.equals(child.child("email").getValue().toString())) {
                        idUser=Integer.parseInt(child.child("id").getValue().toString());
                        NickUser=child.child("nickName").getValue().toString();
                        llenarLista(context);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void llenarLista(final Context context){

        listamensajes.clear();
        firebase=FirebaseDatabase.getInstance();
        database = firebase.getReference("WhatsappJava").child("Usuarios").child(idUser+"").child("Mensajes");
        Query query = database.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    listamensajes.add(new Mensaje(Integer.parseInt(child.child("id").getValue().toString()),
                            Integer.parseInt(child.child("idRemitente").getValue().toString()),
                            Integer.parseInt(child.child("idReceptor").getValue().toString()),
                            child.child("mensaje").getValue().toString(),
                            child.child("asunto").getValue().toString()));
                }
                rellenarRecyclerView(context);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public static void rellenarRecyclerView(Context context){
        MensajeAdapter adaptador = new MensajeAdapter(listamensajes,context);
        LinearLayoutManager lm = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

        rv.setLayoutManager(lm);
        rv.setAdapter(adaptador);
    }
    public void fbListener(View v){
        Intent intentAccess = new Intent(this, EnviarMensaje.class);
        intentAccess.putExtra("email",EmailIntent);
        startActivity(intentAccess);
    }
}