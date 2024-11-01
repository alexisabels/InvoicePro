package com.example.invoicepro.venta;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "VENTAS")
@NamedQueries({
        @NamedQuery(name = "Ventas.findAll", query = "SELECT v FROM Venta v")
})
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "id_cliente")
    private int idCliente;

    @Column(name = "id_usuario")
    private int idUsuario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_venta", insertable = false, updatable = false)
    private Date fechaVenta;

    @Column(name = "total")
    private double total;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Timestamp fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
}
