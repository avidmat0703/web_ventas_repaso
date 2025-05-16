package org.iesvdm.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesvdm.utils.ConexionDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClienteEditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Connection con = ConexionDB.getConexion();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM cliente WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                request.setAttribute("id", rs.getInt("id"));
                request.setAttribute("nombre", rs.getString("nombre"));
                request.setAttribute("apellido1", rs.getString("apellido1"));
                request.setAttribute("apellido2", rs.getString("apellido2"));
                request.setAttribute("ciudad", rs.getString("ciudad"));
                request.setAttribute("categoria", rs.getInt("categoria"));

                RequestDispatcher rd = request.getRequestDispatcher("/jsp/cliente_edit.jsp");
                rd.forward(request, response);
            } else {
                response.getWriter().println("Cliente no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error interno: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            String apellido1 = request.getParameter("apellido1");
            String apellido2 = request.getParameter("apellido2");
            String ciudad = request.getParameter("ciudad");
            int categoria = Integer.parseInt(request.getParameter("categoria"));

            Connection con = ConexionDB.getConexion();
            String sql = "UPDATE cliente SET nombre=?, apellido1=?, apellido2=?, ciudad=?, categoria=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido1);
            ps.setString(3, apellido2);
            ps.setString(4, ciudad);
            ps.setInt(5, categoria);
            ps.setInt(6, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                response.sendRedirect("listarPedidos");
            } else {
                response.getWriter().println("No se pudo actualizar el cliente.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error al actualizar cliente: " + e.getMessage());
        }
    }
}