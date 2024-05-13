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
    private double puntuacion;
    @Column (name="IMDB/Metacritic")
    private String imdbMetacritic;
    @Column (name="Imagen")
    private String imagen;

    public Libreria() {}

    public Libreria(String nombre, String tipo, String fechaFin, double puntuacion, String imdbMetacritic, String imagen) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.fechaFin = fechaFin;
        this.puntuacion = puntuacion;
        this.imdbMetacritic = imdbMetacritic;
        this.imagen = imagen;
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

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getImdbMetacritic() {
        return imdbMetacritic;
    }

    public void setImdbMetacritic(String imdbMetacritic) {
        this.imdbMetacritic = imdbMetacritic;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
