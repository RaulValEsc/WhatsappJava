package com.example.whatsappjava.Modelos;

import java.util.ArrayList;

public class Usuario {
    private int ID;
    private String Email;
    private String Password;
    private String NickName;


    public Usuario() {}

    public Usuario(int id, String email, String password, String nickName) {
        ID = id;
        Email = email;
        Password = password;
        NickName = nickName;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        ID = ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }
}
