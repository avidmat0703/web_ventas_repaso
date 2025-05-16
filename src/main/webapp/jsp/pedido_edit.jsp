<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.iesvdm.modelo.Cliente, org.iesvdm.modelo.Comercial, java.util.List" %>
<html>
<head>
  <title>Editar Pedido</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
  <h3 class="text-warning">Editar Pedido</h3>
  <% String error = (String) request.getAttribute("error");
    if (error != null) { %>
  <div class="alert alert-danger"><%= error %></div>
  <% } %>

  <form action="editarPedido" method="post">
    <input type="hidden" name="id" value="<%= request.getAttribute("id") %>">

    <div class="mb-3">
      <label for="total">Cantidad:</label>
      <input type="number" step="0.01" min="0" name="total" class="form-control" value="<%= request.getAttribute("total") %>" required>
    </div>

    <div class="mb-3">
      <label for="fecha">Fecha:</label>
      <input type="date" name="fecha" class="form-control" value="<%= request.getAttribute("fecha") %>" required>
    </div>

    <div class="mb-3">
      <label for="cliente">Cliente:</label>
      <select name="cliente" class="form-select" required>
        <%
          List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
          int id_cliente = (int) request.getAttribute("id_cliente");
          if (clientes != null && !clientes.isEmpty()) {
            for (Cliente c : clientes) {
        %>
        <option value="<%= c.getId() %>" <%= c.getId() == id_cliente ? "selected" : "" %>>
          <%= c.getNombre() %> <%= c.getApellido1() %> <%= c.getApellido2() != null ? c.getApellido2() : "" %>
        </option>
        <%
          }
        } else { %>
        <option>No hay clientes disponibles</option>
        <% } %>
      </select>
    </div>

    <div class="mb-3">
      <label for="comercial">Comercial:</label>
      <select name="comercial" class="form-select" required>
        <%
          List<Comercial> comerciales = (List<Comercial>) request.getAttribute("comerciales");
          int id_comercial = (int) request.getAttribute("id_comercial");
          if (comerciales != null && !comerciales.isEmpty()) {
            for (Comercial com : comerciales) {
        %>
        <option value="<%= com.getId() %>" <%= com.getId() == id_comercial ? "selected" : "" %>>
          <%= com.getNombre() %> <%= com.getApellido1() %> <%= com.getApellido2() != null ? com.getApellido2() : "" %>
        </option>
        <%
          }
        } else { %>
        <option>No hay comerciales disponibles</option>
        <% } %>
      </select>
    </div>

    <button type="submit" class="btn btn-success">Actualizar Pedido</button>
    <a href="listarPedidos" class="btn btn-secondary">Cancelar</a>
  </form>
</div>
</body>
</html>