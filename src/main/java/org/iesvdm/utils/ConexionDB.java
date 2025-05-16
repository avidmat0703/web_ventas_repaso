package org.iesvdm.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/ventas?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConexion() throws Exception {
        System.out.println("Intentando conectar a MySQL...");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Conexión OK");
        return con;
    }
}