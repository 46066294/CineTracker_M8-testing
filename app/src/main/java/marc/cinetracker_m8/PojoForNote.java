package marc.cinetracker_m8;

/**
 * Created by 46066294p on 24/02/16.
 */
public class PojoForNote {


    String titulo;
    String descripcion;
    Double longitud;
    Double latitud;


    public PojoForNote(){}

    public PojoForNote(String titulo, String descripcion, Double longitud, Double latitud) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    //getters-setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }


}
