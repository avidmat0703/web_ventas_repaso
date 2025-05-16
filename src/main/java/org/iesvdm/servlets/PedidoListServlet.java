package org.iesvdm.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.utils.ConexionDB;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PedidoListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PedidoDAO dao = new PedidoDAO(ConexionDB.getConexion());

            List<Double> totales = dao.obtenerTotalesUnicos();
            request.setAttribute("totales", totales);

            List<Pedido> pedidos = dao.listarPedidos();

            String minStr = request.getParameter("min");
            String maxStr = request.getParameter("max");

            if (minStr != null && maxStr != null) {
                double min = Double.parseDouble(minStr);
                double max = Double.parseDouble(maxStr);
                if (min > max) {
                    request.setAttribute("errorHorquilla", "La cantidad mínima no puede ser mayor que la máxima.");
                } else {
                    pedidos = dao.listarPedidosPorCantidad(min, max);
                }
            }

            Map<String, Integer> resumen = dao.resumenClientesPorComercial();
            request.setAttribute("pedidos", pedidos);
            request.setAttribute("resumen", resumen);

            RequestDispatcher rd = request.getRequestDispatcher("/jsp/pedidos_list.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error interno: " + e.getMessage());
        }
    }
}