package com.example.rafatarrega.aadfirebase.model;

/**
 * Created by rafatarrega on 31/1/18.
 */

public class Usuarios {

    private String nickname;
    private String nombre;
    private String apellido;
    private String direccion;
    private String mail;

    public Usuarios(){

    }

    public Usuarios(String nickname, String nombre, String apellido, String direccion, String mail) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.mail = mail;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "nickname='" + nickname + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", direccion='" + direccion + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
