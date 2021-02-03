package com.example.whatsappjava.Modelos;

public class Mensaje {
    public int ID;
    public int idRemitente;
    public int idReceptor;
    public String Mensaje;
    public String Asunto;

    public Mensaje(int ID, int idRemitente, int idReceptor, String mensaje, String asunto) {
        this.ID = ID;
        this.idRemitente = idRemitente;
        this.idReceptor = idReceptor;
        Mensaje = mensaje;
        Asunto = asunto;
    }

    public int getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(int idRemitente) {
        this.idRemitente = idRemitente;
    }

    public int getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(int idReceptor) {
        this.idReceptor = idReceptor;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public String getAsunto() {
        return Asunto;
    }

    public void setAsunto(String asunto) {
        Asunto = asunto;
    }
}
