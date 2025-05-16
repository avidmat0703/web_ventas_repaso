<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Editar Cliente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h3 class="text-warning">Editar Cliente</h3>

    <form action="editarCliente" method="post">
        <input type="hidden" name="id" value="<%= request.getAttribute("id") %>">

        <div class="mb-3">
            <label for="nombre">Nombre:</label>
            <input type="text" class="form-control" name="nombre" value="<%= request.getAttribute("nombre") %>" required>
        </div>

        <div class="mb-3">
            <label for="apellido1">Apellido 1:</label>
            <input type="text" class="form-control" name="apellido1" value="<%= request.getAttribute("apellido1") %>" required>
        </div>

        <div class="mb-3">
            <label for="apellido2">Apellido 2:</label>
            <input type="text" class="form-control" name="apellido2" value="<%= request.getAttribute("apellido2") != null ? request.getAttribute("apellido2") : "" %>">
        </div>

        <div class="mb-3">
            <label for="ciudad">Ciudad:</label>
            <input type="text" class="form-control" name="ciudad" value="<%= request.getAttribute("ciudad") != null ? request.getAttribute("ciudad") : "" %>" required>
        </div>

        <div class="mb-3">
            <label for="categoria">Categoría:</label>
            <input type="number" class="form-control" name="categoria" value="<%= request.getAttribute("categoria") != null ? request.getAttribute("categoria") : "" %>" required>
        </div>

        <button type="submit" class="btn btn-success">Actualizar Cliente</button>
        <a href="listarPedidos" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
</body>
</html>