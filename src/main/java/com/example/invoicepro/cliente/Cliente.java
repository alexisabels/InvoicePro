package com.example.invoicepro.cliente;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CLIENTES")
@NamedQueries({
        @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Cliente c")
})
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "CORREO")
    private String email;

    @Column(name = "TELEFONO")
    private String telefono;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = new Date();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public String getNombre() {
        return nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
