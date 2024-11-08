package com.example.pruebalaboratorio1.beans;

public class pelicula {

    private int idPelicula;
    private String titulo;
    private String director;
    private int anoPublicacion;
    private Double rating;
    private double boxOffice;
    private genero _genero;
    private streaming _streaming;
    private String duracion;
    private boolean premioOscar;

    public pelicula(){
    }
    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getAnoPublicacion() {
        return anoPublicacion;
    }

    public void setAnoPublicacion(int anoPublicacion) {
        this.anoPublicacion = anoPublicacion;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public double getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(double boxOffice) {
        this.boxOffice = boxOffice;
    }

    public genero getGenero() {
        return genero;
    }

    public void setGenero(genero genero) {
        this.genero = genero;
    }

    public streaming getStreaming() {
        return streaming;
    }

    public void setStreaming(streaming streaming) {
        this.streaming = streaming;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
        this.actualizarValidadorBorrado();
    }

    public boolean isPremioOscar() {
        return premioOscar;
    }

    public void setPremioOscar(boolean premioOscar) {
        this.premioOscar = premioOscar;
        this.actualizarValidadorBorrado();
    }
    public boolean getPremioOscar(){
        return premioOscar;
    }
    private static boolean validadorBorrado = false;

    public boolean getValidadorBorrado(){
        return this.validadorBorrado;
    }
    public void setValidadorBorrado(boolean validadorBorrado){
        this.validadorBorrado = validadorBorrado;
    }
    public void actualizarValidadorBorrado(){
        int duracion = Integer.parseInt(this.duracion.substring(0, original.length() - 3));
        //quito los tres últimos dígitos del string y lo convierto a integer
        //cada que se cargue un valor a la duracion y premioOscar este valor se actualiza
        if(duracion > 120 && !this.premioOscar){
            this.validadorBorrado = true;
        }
    }
}
