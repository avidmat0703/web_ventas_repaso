package org.iesvdm.dao;

import org.iesvdm.modelo.Comercial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComercialDAO {
    private Connection con;

    public ComercialDAO(Connection con) {
        this.con = con;
    }

    public List<Comercial> listarTodos() throws SQLException {
        List<Comercial> lista = new ArrayList<>();
        String sql = "SELECT * FROM comercial";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Comercial c = new Comercial();
            c.setId(rs.getInt("id"));
            c.setNombre(rs.getString("nombre"));
            c.setApellido1(rs.getString("apellido1"));
            c.setApellido2(rs.getString("apellido2"));
            lista.add(c);
        }
        return lista;
    }

    public Comercial buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM comercial WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Comercial c = new Comercial();
            c.setId(rs.getInt("id"));
            c.setNombre(rs.getString("nombre"));
            c.setApellido1(rs.getString("apellido1"));
            c.setApellido2(rs.getString("apellido2"));
            return c;
        }
        return null;
    }

    public void actualizar(Comercial comercial) throws SQLException {
        String sql = "UPDATE comercial SET nombre=?, apellido1=?, apellido2=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, comercial.getNombre());
        ps.setString(2, comercial.getApellido1());
        ps.setString(3, comercial.getApellido2());
        ps.setInt(4, comercial.getId());
        ps.executeUpdate();
    }
}