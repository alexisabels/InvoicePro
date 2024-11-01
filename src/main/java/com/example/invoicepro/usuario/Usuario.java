package com.example.invoicepro.usuario;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "USUARIOS")
@NamedQueries({
        @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuario u")
})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "CONTRASEÑA")
    private String contraseña;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_CREACION", insertable = false, updatable = false)
    private Date fechaCreacion;
    
    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
