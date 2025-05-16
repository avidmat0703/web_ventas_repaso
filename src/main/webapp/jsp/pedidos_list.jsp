<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, org.iesvdm.modelo.Pedido" %>
<html>
<head>
  <title>Listado de Pedidos</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">

  <% String errorHorquilla = (String) request.getAttribute("errorHorquilla");
    if (errorHorquilla != null) { %>
  <div class="alert alert-danger"><%= errorHorquilla %></div>
  <% } %>

  <h3 class="text-primary">Resumen de clientes por comercial</h3>
  <table class="table table-bordered table-success">
    <thead>
    <tr>
      <th>Comercial</th>
      <th># Clientes</th>
    </tr>
    </thead>
    <tbody>
    <%
      Map<String, Integer> resumen = (Map<String, Integer>) request.getAttribute("resumen");
      if (resumen != null && !resumen.isEmpty()) {
        for (Map.Entry<String, Integer> entry : resumen.entrySet()) {
    %>
    <tr>
      <td><%= entry.getKey() %></td>
      <td><%= entry.getValue() %></td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="2">Sin datos de comerciales</td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>

  <h3 class="text-primary">Buscador por horquilla de cantidad</h3>
  <%
    List<Double> totales = (List<Double>) request.getAttribute("totales");
  %>
  <form action="listarPedidos" method="get" class="row g-3 mb-4">
    <div class="col-auto">
      <label>Cantidad mínima:</label>
      <select name="min" class="form-select" required>
        <% for (Double t : totales) { %>
        <option value="<%= t %>"><%= t %></option>
        <% } %>
      </select>
    </div>
    <div class="col-auto">
      <label>Cantidad máxima:</label>
      <select name="max" class="form-select" required>
        <% for (Double t : totales) { %>
        <option value="<%= t %>"><%= t %></option>
        <% } %>
      </select>
    </div>
    <div class="col-auto align-self-end">
      <button type="submit" class="btn btn-info">Buscar</button>
    </div>
  </form>

  <h3 class="text-primary">Listado de Pedidos</h3>
  <a href="crearPedido" class="btn btn-primary mb-3">Crear Pedido</a>
  <%
    List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");
    if (pedidos != null && !pedidos.isEmpty()) {
  %>
  <table class="table table-striped table-bordered">
    <thead class="table-primary">
    <tr>
      <th>ID</th>
      <th>Cantidad</th>
      <th>Fecha</th>
      <th>Cliente</th>
      <th>Comercial</th>
      <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <%
      for (Pedido p : pedidos) {
    %>
    <tr>
      <td><%= p.getId() %></td>
      <td><%= p.getTotal() %></td>
      <td><%= p.getFecha() %></td>
      <td><a href="editarCliente?id=<%= p.getCliente().getId() %>"><%= p.getCliente().getNombreCompleto() %></a></td>
      <td><%= p.getComercial().getNombreCompleto() %></td>
      <td>
        <a href="editarPedido?id=<%= p.getId() %>" class="btn btn-warning btn-sm">Editar</a>
        <a href="borrarPedido?id=<%= p.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('¿Estás seguro de borrar este pedido?');">Borrar</a>
      </td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>
  <% } else { %>
  <div class="alert alert-warning">No hay pedidos registrados.</div>
  <% } %>
</div>
</body>
</html>