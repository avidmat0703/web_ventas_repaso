package org.iesvdm.dao;

import org.iesvdm.modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection con;

    public ClienteDAO(Connection con) {
        this.con = con;
    }

    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Cliente c = new Cliente();
            c.setId(rs.getInt("id"));
            c.setNombre(rs.getString("nombre"));
            c.setApellido1(rs.getString("apellido1"));
            c.setApellido2(rs.getString("apellido2"));
            lista.add(c);
        }
        return lista;
    }

    public Cliente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Cliente c = new Cliente();
            c.setId(rs.getInt("id"));
            c.setNombre(rs.getString("nombre"));
            c.setApellido1(rs.getString("apellido1"));
            c.setApellido2(rs.getString("apellido2"));
            return c;
        }
        return null;
    }

    public void actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nombre=?, apellido1=?, apellido2=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, cliente.getNombre());
        ps.setString(2, cliente.getApellido1());
        ps.setString(3, cliente.getApellido2());
        ps.setInt(4, cliente.getId());
        ps.executeUpdate();
    }
}