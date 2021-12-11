package com.example.passwordwallet.Activities;

public class Subcuentas {

    private int id;
    private String cuentaprincipal_id;
    private String icono_id;
    private String email;
    private String password;
    private String fecha;


    public Subcuentas(int id, String cuentaprincipal_id, String icono_id, String email, String password, String fecha) {
        this.id = id;
        this.cuentaprincipal_id = cuentaprincipal_id;
        this.icono_id = icono_id;
        this.email = email;
        this.password = password;
        this.fecha = fecha;
    }

    public Subcuentas() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCuentaprincipal_id() {
        return cuentaprincipal_id;
    }

    public void setCuentaprincipal_id(String cuentaprincipal_id) {
        this.cuentaprincipal_id = cuentaprincipal_id;
    }

    public String getIcono_id() {
        return icono_id;
    }

    public void setIcono_id(String icono_id) {
        this.icono_id = icono_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
