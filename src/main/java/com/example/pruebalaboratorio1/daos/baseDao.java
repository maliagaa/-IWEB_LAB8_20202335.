package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.pelicula;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class baseDao {
    String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";
    String username = "root";
    String password = "123456";


    public Connection getConnection() throws SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        String user = "root";
        String pass = "123456";
        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";

        return DriverManager.getConnection(url, user, pass);
    }


    public boolean validarBorrado(pelicula movie) {
        boolean validador = false;
        String[] duracionCompleta = movie.getDuracion().split("m");
        int duracion = Integer.parseInt(duracionCompleta[0]);
        if(duracion>120 && !movie.isPremioOscar()){
            validador = true;
        }

        return validador;
    }
    
    protected abstract boolean validarBorrado(); //método abstracto que verifica si se puede eliminar

    protected List listarTodos(){
        List lista = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            String sql = this.generarSQLParaListar();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                this.agregarObjetoALaLista(lista,rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    protected abstract String generarSQLParaListar();
    protected abstract void agregarObjetoALaLista(List lista, ResultSet resultSet) throws SQLException;


    protected void modificar(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password);) {
                String sql = this.generarSQLParaModificar();
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    this.incluirValoresModificacionEnStatement(pstmt);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    protected abstract String generarSQLParaModificar();
    protected abstract void incluirValoresModificacionEnStatement(PreparedStatement pstmt);

    protected void eliminar(){
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(this.validadorBorrado()){
                try (Connection conn = DriverManager.getConnection(url, username, password);) {
                    String sql = this.generarSQLParaEliminacion();
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        this.incluirValoresEliminacionEnStatement(pstmt);
                        pstmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected abstract String generarSQLParaEliminacion();
    protected abstract void incluirValoresEliminacionEnStatement(PreparedStatement pstmt);
}

