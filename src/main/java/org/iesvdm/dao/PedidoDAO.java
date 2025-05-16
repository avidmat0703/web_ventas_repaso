package org.iesvdm.dao;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;

import java.sql.*;
import java.util.*;

public class PedidoDAO {
    private Connection con;

    public PedidoDAO(Connection con) {
        this.con = con;
    }

    public List<Pedido> listarPedidos() throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.total, p.fecha, c.*, com.* FROM pedido p " +
                "JOIN cliente c ON p.id_cliente = c.id " +
                "JOIN comercial com ON p.id_comercial = com.id";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Pedido p = new Pedido();
            p.setId(rs.getInt("id"));
            p.setTotal(rs.getDouble("total"));
            p.setFecha(rs.getDate("fecha"));

            Cliente cliente = new Cliente();
            cliente.setId(rs.getInt("c.id"));
            cliente.setNombre(rs.getString("c.nombre"));
            cliente.setApellido1(rs.getString("c.apellido1"));
            cliente.setApellido2(rs.getString("c.apellido2"));
            p.setCliente(cliente);

            Comercial comercial = new Comercial();
            comercial.setId(rs.getInt("com.id"));
            comercial.setNombre(rs.getString("com.nombre"));
            comercial.setApellido1(rs.getString("com.apellido1"));
            comercial.setApellido2(rs.getString("com.apellido2"));
            p.setComercial(comercial);

            lista.add(p);
        }
        return lista;
    }

    public List<Pedido> listarPedidosPorCantidad(double min, double max) throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.total, p.fecha, c.*, com.* FROM pedido p " +
                "JOIN cliente c ON p.id_cliente = c.id " +
                "JOIN comercial com ON p.id_comercial = com.id " +
                "WHERE p.total BETWEEN ? AND ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1, min);
        ps.setDouble(2, max);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Pedido p = new Pedido();
            p.setId(rs.getInt("id"));
            p.setTotal(rs.getDouble("total"));
            p.setFecha(rs.getDate("fecha"));

            Cliente cliente = new Cliente();
            cliente.setId(rs.getInt("c.id"));
            cliente.setNombre(rs.getString("c.nombre"));
            cliente.setApellido1(rs.getString("c.apellido1"));
            cliente.setApellido2(rs.getString("c.apellido2"));
            p.setCliente(cliente);

            Comercial comercial = new Comercial();
            comercial.setId(rs.getInt("com.id"));
            comercial.setNombre(rs.getString("com.nombre"));
            comercial.setApellido1(rs.getString("com.apellido1"));
            comercial.setApellido2(rs.getString("com.apellido2"));
            p.setComercial(comercial);

            lista.add(p);
        }
        return lista;
    }

    public List<Double> obtenerTotalesUnicos() throws SQLException {
        List<Double> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT total FROM pedido ORDER BY total ASC";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            lista.add(rs.getDouble("total"));
        }
        return lista;
    }

    public Map<String, Integer> resumenClientesPorComercial() throws SQLException {
        Map<String, Integer> resumen = new LinkedHashMap<>();
        String sql = "SELECT com.nombre, com.apellido1, COUNT(DISTINCT p.id_cliente) AS total FROM comercial com " +
                "LEFT JOIN pedido p ON p.id_comercial = com.id GROUP BY com.id";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String nombre = rs.getString("nombre") + " " + rs.getString("apellido1");
            resumen.put(nombre, rs.getInt("total"));
        }
        return resumen;
    }
}