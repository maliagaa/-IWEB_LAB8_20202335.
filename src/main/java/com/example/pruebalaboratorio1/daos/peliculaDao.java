package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.genero;
import com.example.pruebalaboratorio1.beans.pelicula;
import com.example.pruebalaboratorio1.beans.streaming;

import java.sql.*;
import java.util.ArrayList;

public class peliculaDao extends baseDao{
    private static boolean usaFiltro = false;
    private pelicula peli = new pelicula();

    public ArrayList<pelicula> listarPeliculas() {
        ArrayList<pelicula> listaPeliculas = new ArrayList<>();
        listaPeliculas = (ArrayList<pelicula>) this.listarTodos(); 
        return listaPeliculas;
    }

    public ArrayList<pelicula> listarPeliculasFiltradas(int idGenero, int idStreaming) {
        ArrayList<pelicula> listaPeliculasFiltradas= new ArrayList<>();
        this.usaFiltro = true; //va a realizar una busqueda con filtro
        genero gene = new genero();
        gene.setIdGenero(idGenero);
        this.peli.setGenero(gene);
        streaming stre = new streaming();
        stre.setIdStreaming(idStreaming);
        this.peli.setStreaming(stre);
        listaPeliculasFiltradas = (ArrayList<pelicula>) this.listarTodos();
        return listaPeliculasFiltradas;
    }

    @Override
    protected abstract String generarSQLParaListar(){
        String sql="";
        if(usaFiltro==false){
            sql += "SELECT A.*, B.NOMBRE, C.NOMBRESERVICIO FROM  \n" +
                    "(SELECT * FROM PELICULA ) AS A \n" +
                    "INNER JOIN \n" +
                    "(SELECT * FROM GENERO) AS B\n" +
                    "ON A.IDGENERO = B.IDGENERO\n" +
                    "INNER JOIN \n" +
                    "(SELECT * FROM STREAMING) AS C\n" +
                    "ON A.IDSTREAMING = C.IDSTREAMING\n" +
                    "ORDER BY RATING DESC , BOXOFFICE DESC";
        } 
        else{
            sql += "SELECT A.*, B.NOMBRE, C.NOMBRESERVICIO FROM  \n" +
                    "(SELECT * FROM PELICULA) AS A \n" +
                    "INNER JOIN \n" +
                    "(SELECT * FROM GENERO WHERE idGenero="+this.idGenero+") AS B\n" +
                    "ON A.IDGENERO = B.IDGENERO\n" +
                    "INNER JOIN \n" +
                    "(SELECT * FROM STREAMING WHERE idStreaming="+this.idStreaming+") AS C\n" +
                    "ON A.IDSTREAMING = C.IDSTREAMING\n" +
                    "ORDER BY RATING DESC , BOXOFFICE DESC";
        }
        return sql;
    }
    @Override
    protected abstract void agregarObjetoALaLista(List lista, ResultSet resultSet) throws SQLException{
        pelicula movie = new pelicula();
        genero genero = new genero();
        streaming streaming = new streaming();
        
        int idPelicula = rs.getInt("idPelicula");
        movie.setIdPelicula(idPelicula);
        String titulo = rs.getString("titulo");
        movie.setTitulo(titulo);
        String director = rs.getString("director");
        movie.setDirector(director);
        int anoPublicacion = rs.getInt("anoPublicacion");
        movie.setAnoPublicacion(anoPublicacion);
        double rating = rs.getDouble("rating");
        movie.setRating(rating);
        double boxOffice = rs.getDouble("boxOffice");
        movie.setBoxOffice(boxOffice);
        int idGenero = rs.getInt("idGenero");
        genero.setIdGenero(idGenero);
        String duracion = rs.getString("duracion");
        movie.setDuracion(duracion);
        boolean premiooscar = rs.getString("premioOscar");
        movie.setPremioOscar(premiooscar);
        int idStreaming = rs.getInt("idStreaming");
        streaming.setIdStreaming(idStreaming);
        String nombregenero = rs.getString("nombre");
        genero.setGenero(nombregenero);
        String nombrestreaming = rs.getString("nombreServicio");
        streaming.setNombreStreaming(nombrestreaming);
        
        movie.setStreaming(streaming);
        movie.setGenero(genero);
        boolean validador= validarBorrado(movie);
        movie.setValidadorBorrado(validador);

        lista.add(movie);
    }

    // AGREGAR CAMPOS FALTANTES (GENERO, STREAMING)
    public void editarPelicula(int idPelicula, String titulo, String director, int anoPublicacion, double rating, double boxOffice, int idGenero, String duracion, boolean premioOscar, int idStreaming){
        peli.setIdPelicula(idPelicula);
        peli.setTitulo(titulo);
        peli.setDirector(director);
        peli.setAnoPublicacion(anoPublicacion);
        peli.setRating(rating);
        peli.setBoxOffice(boxOffice);
        genero genero = new genero();
        genero.setIdGenero(idGenero);
        peli.setGenero(genero);
        peli.setDuracion(duracion);
        peli.setPremioOscar(premioOscar);
        streaming streaming = new streaming();
        streaming.setIdStreaming(idStreaming);
        peli.setStreaming(streaming);
        this.modificar(); //método de la clase baseDao
    }

    @Override
    protected abstract String generarSQLParaModificar(){
        return "UPDATE PELICULA SET titulo = ?, director = ?, anoPublicacion = ? ," +
                "rating = ?, boxOffice = ?, idGenero = ?, duracion = ?, premioOscar = ?,idStreaming = ? "+
                "WHERE IDPELICULA = ?";
    }
    @Override
    protected abstract void incluirValoresModificacionEnStatement(PreparedStatement pstmt){
        pstmt.setString(1, peli.getTitulo());
        pstmt.setString(2, peli.getDirector());
        pstmt.setInt(3, peli.getAnoPublicacion());
        pstmt.setDouble(4, peli.getRating());
        pstmt.setDouble(5, peli.getBoxOffice());
        pstmt.setInt(6, peli.getGenero().getIdGenero());
        pstmt.setString(7, peli.getDuracion());
        pstmt.setBoolean(8, peli.getPremioOscar());
        pstmt.setInt(9, peli.getStreaming().getIdStreaming());
        pstmt.setInt(10, peli.getIdPelicula());
    }

    public void borrarPelicula(int idPelicula) {
        // NOTA: PARA BORRAR UNA PELICULA CORRECTAMENTE NO OLVIDAR PRIMERO BORRARLA DE LA TABLA PROTAGONSITAS
        peli.setIdPelicula(idPelicula);
        this.eliminar();
    }
    @Override
    protected abstract boolean validarBorrado(){
        return this.peli.getValidadorBorrado();
    }
    @Override
    protected abstract String generarSQLParaEliminacion(){
        String sql="";
        sql += "DELETE FROM protagonistas WHERE idPelicula = ?;";
        sql += "DELETE FROM pelicula WHERE idPelicula = ?;";
        return 
    }
    @Override
    protected abstract void incluirValoresEliminacionEnStatement(PreparedStatement pstmt){
        int idPelicula = peli.getIdPelicula();
        pstmt.setInt(1, idPelicula);
        pstmt.setInt(2, idPelicula);
    }
}
