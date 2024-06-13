package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.genero;
import com.example.pruebalaboratorio1.beans.pelicula;
import com.example.pruebalaboratorio1.beans.streaming;

import java.sql.*;
import java.util.ArrayList;

public class peliculaDao extends baseDao{

    public ArrayList<pelicula> listarPeliculas() {

        ArrayList<pelicula> listaPeliculas = new ArrayList<>();

        try {

            String sql = "SELECT A.*, B.NOMBRE, C.NOMBRESERVICIO FROM  \n" +
                    "(SELECT * FROM PELICULA ) AS A \n" +
                    "INNER JOIN \n" +
                    "(SELECT * FROM GENERO) AS B\n" +
                    "ON A.IDGENERO = B.IDGENERO\n" +
                    "INNER JOIN \n" +
                    "(SELECT * FROM STREAMING) AS C\n" +
                    "ON A.IDSTREAMING = C.IDSTREAMING\n" +
                    "ORDER BY RATING DESC , BOXOFFICE DESC";
            Connection conn = DriverManager.getConnection(sql);
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                pelicula movie = new pelicula();
                //movie.setGenero(genero);
                //int idPelicula = rs.getInt(1);
                movie.setIdPelicula(rs.getInt(1));
                //movie.setIdPelicula(idPelicula);
                movie.setTitulo(rs.getString("titulo"));
                //String titulo = rs.getString("titulo");
                //movie.setTitulo(titulo);
                movie.setDirector(rs.getString("director"));
                //String director = rs.getString("director");
                //movie.setDirector(director);
                movie.setAnoPublicacion(rs.getInt("anoPublicacion"));
                //int anoPublicacion = rs.getInt("anoPublicacion");
                //movie.setAnoPublicacion(anoPublicacion);
                movie.setRating(rs.getDouble("raiting"));
                //double rating = rs.getDouble("rating");
                //movie.setRating(rating);
                movie.setBoxOffice(rs.getDouble("boxOffice"));
                //double boxOffice = rs.getDouble("boxOffice");
                //movie.setBoxOffice(boxOffice);
                genero genero = new genero();

                genero.setIdGenero(rs.getInt("idGenero"));
                //int idGenero = rs.getInt("idGenero");
                movie.setDuracion(rs.getString("duracion"));
                movie.setPremioOscar(rs.getBoolean("premioOscar"));

                streaming streaming = new streaming();
                streaming.setIdStreaming(rs.getInt("idStreaming"));

                genero.setNombre(rs.getString("nombre"));

                streaming.setNombreServicio(rs.getString("nombreServicio"));

                listaPeliculas.add(movie);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaPeliculas;
    }

    public ArrayList<pelicula> listarPeliculasFiltradas(int idGenero, int idStreaming) {

        ArrayList<pelicula> listaPeliculasFiltradas= new ArrayList<>();


        return listaPeliculasFiltradas;
    }

    // AGREGAR CAMPOS FALTANTES (GENERO, STREAMING)
    public void editarPelicula(int idPelicula, String titulo, String director, int anoPublicacion, double rating, double boxOffice){

        try {
            String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";
            String username = "root";
            String password = "root";

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password);) {
                String sql = "UPDATE PELICULA SET titulo = ?, director = ?, anoPublicacion = ? ," +
                        "rating = ?, boxOffice = ? WHERE IDPELICULA = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, titulo);
                    pstmt.setString(2, director);
                    pstmt.setInt(3, anoPublicacion);
                    pstmt.setDouble(4, rating);
                    pstmt.setDouble(5, boxOffice);
                    pstmt.setInt(6, idPelicula);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    public void borrarPelicula(int idPelicula) {

        // NOTA: PARA BORRAR UNA PELICULA CORRECTAMENTE NO OLVIDAR PRIMERO BORRARLA DE LA TABLA PROTAGONSITAS


    }


}
