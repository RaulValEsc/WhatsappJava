package com.example.whatsappjava.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.whatsappjava.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    public static FirebaseDatabase firebase;
    public static DatabaseReference database;
    Button bRegister;
    EditText Email;
    EditText Contrasena;
    Button bAcceder;
    String EmailIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email = findViewById(R.id.etEmail);
        Contrasena = findViewById(R.id.etContraseñaEmail);
        bAcceder= findViewById(R.id.LogInButton);
        bRegister = findViewById(R.id.bRegis);

        bAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (Email.getText().toString().equals("") || Contrasena.getText().toString().equals("")) {
                    Snackbar.make(v, "Complete todos los campos.", Snackbar.LENGTH_LONG).show();
                } else {
                    firebase = FirebaseDatabase.getInstance();
                    database = firebase.getReference("WhatsappJava").child("Usuarios");
                    Query query = database.orderByKey();
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                if (Email.getText().toString().equals(child.child("email").getValue().toString()) && Contrasena.getText().toString().equals(child.child("password").getValue().toString())) {
                                    EmailIntent = Email.getText().toString();
                                    AccessListener();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
                    Snackbar.make(v, "Datos incorrectos", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }



    public void AccessListener(){
        Intent intentAccess = new Intent(this, RecyclerViewMensajes.class);
        intentAccess.putExtra("email",EmailIntent);
        startActivity(intentAccess);
    }

    public void RegisterListener(View v){
        Intent intentRegister = new Intent(this, Registrar.class);
        startActivity(intentRegister);
    }
}