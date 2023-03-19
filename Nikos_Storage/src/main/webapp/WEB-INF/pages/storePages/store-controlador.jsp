<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- insertar un producto -->
<div class="modal fade" id="insertarPedido" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-tittle">Insertar Pedido</h5>
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">

                <div class="container mt-3">


                    <p>Selecciona el pedido en donde se agregara el producto o anotalo.</p>
                    <form class="" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/storeControlServlet?accion=agregarAlPedido"> 
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <select type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="tienda" name="seleccionPedido" required>
                                    <option value="empty">Selecciona</option>
                                    <c:forEach var="pedido" items="${pedidos}">
                                        <tr>
                                        <option value ="${pedido.codigoPedido}">${pedido.codigoPedido}</option> 
                                        </tr>
                                    </c:forEach>
                                </select>

                            </div>
                            <div class="input-group-append">
                                <button class="btn btn-success" type="button">Crear nuevo pedido</button>
                            </div>
                            <input type="text" class="form-control" placeholder="Some text" name="pedidoEscrito">
                            <div class="input-group-append">
                                <button class="btn btn-success" type="button">Crear nuevo pedido</button>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Submit</button>

                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- insertar un producto -->
<div class="modal fade" id="insertarProducto" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-tittle">Insertar pedido en la tienda</h5>
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">

            </div>
        </div>
    </div>
</div>
