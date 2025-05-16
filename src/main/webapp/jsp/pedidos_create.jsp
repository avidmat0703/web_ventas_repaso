<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.iesvdm.modelo.Cliente, org.iesvdm.modelo.Comercial, java.util.List" %>
<html>
<head>
    <title>Crear Pedido</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h3 class="text-success">Crear Pedido</h3>
    <% String error = (String) request.getAttribute("error");
        if (error != null) { %>
    <div class="alert alert-danger"><%= error %></div>
    <% } %>
    <form action="crearPedido" method="post">
        <div class="mb-3">
            <label for="cantidad" class="form-label">Cantidad:</label>
            <input type="number" step="0.01" min="0" class="form-control" id="cantidad" name="cantidad" required>
        </div>
        <div class="mb-3">
            <label for="fecha" class="form-label">Fecha:</label>
            <input type="date" class="form-control" id="fecha" name="fecha" required>
        </div>
        <div class="mb-3">
            <label for="cliente" class="form-label">Cliente:</label>
            <select name="cliente" id="cliente" class="form-select" required>
                <%
                    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
                    if (clientes != null) {
                        for (Cliente c : clientes) {
                %>
                <option value="<%= c.getId() %>"><%= c.getNombreCompleto() %></option>
                <%
                    }
                } else {
                %>
                <option>No hay clientes</option>
                <%
                    }
                %>
            </select>
        </div>
        <div class="mb-3">
            <label for="comercial" class="form-label">Comercial:</label>
            <select name="comercial" id="comercial" class="form-select" required>
                <%
                    List<Comercial> comerciales = (List<Comercial>) request.getAttribute("comerciales");
                    if (comerciales != null) {
                        for (Comercial com : comerciales) {
                %>
                <option value="<%= com.getId() %>"><%= com.getNombreCompleto() %></option>
                <%
                    }
                } else {
                %>
                <option>No hay comerciales</option>
                <%
                    }
                %>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Crear Pedido</button>
        <a href="listarPedidos" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
</body>
</html>