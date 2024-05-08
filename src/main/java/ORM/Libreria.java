package ORM;

import jakarta.persistence.*;
@Entity
@Table (name="Libreria")
public class Libreria {
    @Id
    @Column (name="Nombre")
    private String nombre;
    @Column (name="Tipo")
    private String tipo;
    @Column (name="Fecha fin")
    private String fechaFin;
    @Column (name="Puntuacion")
    private int puntuacion;
    @Column (name="enlace")
    private String enlace;

    public Libreria() {}

    public Libreria(String nombre, String tipo, String fechaFin, int puntuacion, String enlace) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.fechaFin = fechaFin;
        this.puntuacion = puntuacion;
        this.enlace = enlace;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }
}
