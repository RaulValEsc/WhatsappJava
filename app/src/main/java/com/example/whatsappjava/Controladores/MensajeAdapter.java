package com.example.whatsappjava.Controladores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappjava.Modelos.Mensaje;
import com.example.whatsappjava.R;
import com.example.whatsappjava.Vista.MensajePantalla;

import java.util.ArrayList;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.ViewHolder>{
    ArrayList<Mensaje> listamensajes;
    Context context;

    public MensajeAdapter(ArrayList<Mensaje> mensajes, Context context) {
        this.listamensajes = mensajes;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asunto_recycler,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv.setText(listamensajes.get(position).getAsunto());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MensajePantalla.class);
                intent.putExtra("idRemitente",listamensajes.get(position).getIdRemitente());
                intent.putExtra("asunto",listamensajes.get(position).getAsunto());
                intent.putExtra("mensaje",listamensajes.get(position).getMensaje());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listamensajes.isEmpty()) {
            return 0;
        }else{
            return listamensajes.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvAsunto);
        }
    }
}
