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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PedidoCreateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ClienteDAO clienteDAO = new ClienteDAO(ConexionDB.getConexion());
            ComercialDAO comercialDAO = new ComercialDAO(ConexionDB.getConexion());
            request.setAttribute("clientes", clienteDAO.listarTodos());
            request.setAttribute("comerciales", comercialDAO.listarTodos());
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/pedidos_create.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cantidadStr = request.getParameter("cantidad");
        String fechaStr = request.getParameter("fecha");
        String clienteId = request.getParameter("cliente");
        String comercialId = request.getParameter("comercial");
        String error = "";

        try {
            double cantidad = Double.parseDouble(cantidadStr);
            Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
            Date hoy = new Date();

            if (cantidad < 0) {
                error += "La cantidad no puede ser negativa.<br>";
            }
            if (fecha.before(hoy)) {
                error += "La fecha no puede ser anterior al día actual.<br>";
            }

            if (error.isEmpty()) {
                Connection con = ConexionDB.getConexion();
                String sql = "INSERT INTO pedido (total, fecha, id_cliente, id_comercial) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setDouble(1, cantidad);
                ps.setDate(2, new java.sql.Date(fecha.getTime()));
                ps.setInt(3, Integer.parseInt(clienteId));
                ps.setInt(4, Integer.parseInt(comercialId));
                ps.executeUpdate();
                response.sendRedirect("listarPedidos");
            } else {
                request.setAttribute("error", error);
                doGet(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en el procesamiento de datos.");
            doGet(request, response);
        }
    }
}