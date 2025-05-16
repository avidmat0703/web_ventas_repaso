package org.iesvdm.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesvdm.utils.ConexionDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class PedidoDeleteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement("DELETE FROM pedido WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            response.sendRedirect("listarPedidos");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error al borrar pedido: " + e.getMessage());
        }
    }
}