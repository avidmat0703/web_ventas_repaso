package org.iesvdm.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.utils.ConexionDB;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class PedidoEditServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Connection con = ConexionDB.getConexion();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM pedido WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                request.setAttribute("id", rs.getInt("id"));
                request.setAttribute("total", rs.getDouble("total"));
                request.setAttribute("fecha", rs.getDate("fecha"));
                request.setAttribute("id_cliente", rs.getInt("id_cliente"));
                request.setAttribute("id_comercial", rs.getInt("id_comercial"));

                ClienteDAO clienteDAO = new ClienteDAO(con);
                ComercialDAO comercialDAO = new ComercialDAO(con);
                List clientes = clienteDAO.listarTodos();
                List comerciales = comercialDAO.listarTodos();
                request.setAttribute("clientes", clientes);
                request.setAttribute("comerciales", comerciales);

                RequestDispatcher rd = request.getRequestDispatcher("/jsp/pedido_edit.jsp");
                rd.forward(request, response);
            } else {
                response.getWriter().println("Pedido no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error interno: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            String cantidadStr = request.getParameter("total");
            String fechaStr = request.getParameter("fecha");
            String clienteId = request.getParameter("cliente");
            String comercialId = request.getParameter("comercial");

            // Sin validaciones
            Connection con = ConexionDB.getConexion();
            String sql = "UPDATE pedido SET total=?, fecha=?, id_cliente=?, id_comercial=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, Double.parseDouble(cantidadStr));
            ps.setDate(2, java.sql.Date.valueOf(fechaStr));
            ps.setInt(3, Integer.parseInt(clienteId));
            ps.setInt(4, Integer.parseInt(comercialId));
            ps.setInt(5, Integer.parseInt(idStr));

            int filas = ps.executeUpdate();
            if (filas > 0) {
                response.sendRedirect("listarPedidos");
            } else {
                request.setAttribute("error", "No se ha podido actualizar el pedido. Verifique el ID.");
                doGet(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error al actualizar pedido: " + e.getMessage());
        }
    }
}