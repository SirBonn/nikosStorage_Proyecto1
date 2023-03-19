<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${Error != null}">
    <div class="alert alert-danger alert-dismissible">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>ERROR!</strong> ${Error}
    </div>
</c:if>

<c:if test="${Exito != null}">
    <div class="alert alert-success alert-dismissible">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>EXITO!</strong> ${Exito}
    </div>
</c:if>

<section id="catalogoList">
    <div class="container-fluid row">
        <div class="col-sm-9 col-md-6 col-lg-8">
            <div class="card">
                <div class="card-header">
                    <h4>Catalogo de ${tienda.nombre}</h4>
                </div>
                <table class="table table-striped table-bordered">

                    <thead class="thead-dark">
                        <tr>

                            <th>Codigo</th>
                            <th>Producto</th>
                            <th>Cantidad</th>
                            <th>Precio</th>    
                            <th></th>

                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="producto" items="${catalogo}">
                            <tr>
                                <td>${producto.codigo}</td>
                                <td>${producto.nombre}</td>
                                <td>${producto.cantidad}</td>
                                <td>Q. ${producto.precioVenta}</td>
                                <%-- ${pageContext.request.contextPath}/ManageControlServlet?accion=eliminarTienda&codigoTienda=${tienda.codigo_tienda} --%>
                                <td> 

                                    <c:choose>

                                        <c:when test="${0 < producto.cantidad }">
                                            <a href="${pageContext.request.contextPath}/storeControlServlet?accion=agregarAlPedido&codigoProducto=${producto.codigo}"
                                               class="btn btn-secondary">Agregar a pedido</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="disabled btn btn-secondary disabled-link">Agregar a pedido</a>
                                        </c:otherwise>

                                    </c:choose>    



                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="col-sm-3 col-md-6 col-lg-4">
            <div class="card">
                <div class="card-header">
                    <h4>Pedido actual</h4>
                </div>
                <table class="table table-striped table-bordered">

                    <thead class="thead-dark">
                        <tr>

                            <th>Codigo</th>
                            <th>Producto</th>
                            <th>Cantidad</th>
                            <th>Precio</th>    

                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="producto" items="${listadoPedido}">
                            <tr>
                                <td>${producto.codigo}</td>
                                <td>${producto.nombre}</td>
                                <td>${producto.cantidad}</td>
                                <td>Q. ${producto.cantidad * producto.precioVenta}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="btn-group btn-group-sm">
                    <a href="${pageContext.request.contextPath}/storeControlServlet?accion=limpiarLista"
                       type="btn btn-secondary" class="btn btn-secondary">Limpiar</a>
                    <a href="${pageContext.request.contextPath}/storeControlServlet?accion=crearPedido"
                       type="btn btn-secondary" class="btn btn-secondary">Crear pedido</a>
                    <a>Total: Q </a>
                </div>

            </div>


        </div>
    </div>
</section>

<section id="pedidosList">
    <div class="container-fluid row">
        <div class="col-sm-9 col-md-6 col-lg-8">
            <div class="card">
                <div class="card-header">
                    <h4>Pedidos Registrados</h4>
                </div>
                <table class="table table-striped table-bordered">

                    <thead class="thead-dark">
                        <tr>

                            <th>Codigo</th>
                            <th>Fecha pedido</th>
                            <th>Estado</th>
                            <th>Usuario Solicitante</th>    
                            <th>Total</th>
                            <th></th>

                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="pedido" items="${pedidos}">
                            <tr>
                                <td>${pedido.codigoPedido}</td>
                                <td>${pedido.fechaPedido}</td>
                                <td>${pedido.estadoPedido}</td>
                                <td>${pedido.dependienteSolicitante}</td>
                                <td>Q. ${pedido.costoPedido}</td>

                                <%-- boton de listar envia por get para recibir lista de productos --%>
                                <%-- ${pageContext.request.contextPath}/ManageControlServlet?accion=eliminarTienda&codigoTienda=${tienda.codigo_tienda} --%>
                                <td><a href="${pageContext.request.contextPath}/storeControlServlet?accion=verPedido&codigoPedido=${pedido.codigoPedido}"
                                       class="btn btn-secondary"> Ver Pedido</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="col-sm-3 col-md-6 col-lg-4">
            <div class="card">
                <div class="card-header">
                    <h4>Pedido #${pedidoRegistrado.codigoPedido}</h4>
                </div>
                <table class="table table-striped table-bordered">

                    <thead class="thead-dark">
                        <tr>

                            <th>Codigo</th>
                            <th>Producto</th>
                            <th>Cantidad</th>
                            <th>Precio</th>    

                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="prdRegistrado" items="${productosPdRegistrados}">
                            <tr>
                                <td>${prdRegistrado.codigo}</td>
                                <td>${prdRegistrado.nombre}</td>
                                <td>${prdRegistrado.cantidad}</td>
                                <td>Q. ${prdRegistrado.cantidad * prdRegistrado.precioVenta}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>


            </div>
        </div>

    </div>
</section>

<section id="enviosList">
    <div class="container-fluid row">
        <div class="col-sm-9 col-md-6 col-lg-8">
            <div class="card">
                <div class="card-header">
                    <h4>Envios Registrados</h4>
                </div>
                <table class="table table-striped table-bordered">

                    <thead class="thead-dark">
                        <tr>

                            <th>Codigo</th>
                            <th>Fecha envio</th>
                            <th>Fecha recepcion</th>
                            <th>Estado</th>
                            <th>Destino</th>    
                            <th>Precio</th>
                            <th>Pedido referente</th>
                            <th></th>

                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="envio" items="${envios}">
                            <tr>

                                <td>${envio.codigoEnvio}</td>
                                <td>${envio.fechaEnvio}</td>
                                <td>${envio.fechaRecepcion}</td>
                                <td>${envio.estado}</td>
                                <td>${envio.tiendaDestino}</td>
                                <td>Q. ${envio.totalEnvio}</td>
                                <td>${envio.pedidoEnviado.codigoPedido}</td>
                                <%-- ${pageContext.request.contextPath}/ManageControlServlet?accion=eliminarTienda&codigoTienda=${tienda.codigo_tienda} --%>
                                <td><a href="${pageContext.request.contextPath}/storeControlServlet?accion=verEnvio&codigoPedido=${envio.codigoEnvio}"
                                       class="btn btn-secondary"> Detalles </a></td>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="col-sm-3 col-md-6 col-lg-4">
            <div class="card">
                <div class="card-header">
                    <h4>Envio #${envioRegistrado.codigoEnvio}</h4>
                </div>
                <table class="table table-striped table-bordered">

                    <thead class="thead-dark">
                        <tr>

                            <th>Codigo</th>
                            <th>Producto</th>
                            <th>Cantidad</th>
                            <th>Precio</th>    

                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="prdRegistrado" items="${productosEnvRegistrados}">
                            <tr>
                                <td>${prdRegistrado.codigo}</td>
                                <td>${prdRegistrado.nombre}</td>
                                <td>${prdRegistrado.cantidad}</td>
                                <td>Q. ${prdRegistrado.cantidad * prdRegistrado.precioVenta}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="btn-group btn-group-sm">
                    <a href="${pageContext.request.contextPath}/storeControlServlet?accion="
                       type="btn btn-secondary" class="btn btn-secondary">Reportar Incidencia</a>
                    <a href="${pageContext.request.contextPath}/storeControlServlet?accion="
                       type="btn btn-secondary" class="btn btn-secondary">Marcar como recibido</a>
                    <a href="${pageContext.request.contextPath}/storeControlServlet?accion="
                       type="btn btn-secondary" class="btn btn-secondary">Devolver productos</a>
                </div>

            </div>
        </div>

    </div>
</section>


<section id="incidenciasList">
    <div class="container-fluid row">
        <div class="col-sm-9 col-md-6 col-lg-8 col-xl-10">
            <div class="card">
                <div class="card-header">
                    <h4>Incidencias Registradas</h4>
                </div>
                <table class="table table-striped table-bordered">

                    <thead class="thead-dark">
                        <tr>

                            <th>Codigo</th>
                            <th>Fecha</th>
                            <th>Estado</th>
                            <th>Encargado</th>        
                            <th>Envio incidente</th>        
                            <th></th>

                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="incidencia" items="${incidencias}">
                            <tr>
                                <td>${incidencia.codigo}</td>
                                <td>${incidencia.fechaIncidencia}</td>
                                <td>${incidencia.estadoIncidencia}</td>
                                <td>${incidencia.encargado}</td>
                                <td>${incidencia.envioDevuelto.codigoEnvio}</td>

                                <%-- ${pageContext.request.contextPath}/ManageControlServlet?accion=eliminarTienda&codigoTienda=${tienda.codigo_tienda} --%>
                                <td><a href="#"
                                       class="btn btn-secondary"> Detalles </a></td>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>


    </div>
</section>

<section id="devolucionesList">
    <div class="container-fluid row">
        <div class="col-sm-9 col-md-6 col-lg-8 col-xl-10">
            <div class="card">
                <div class="card-header">
                    <h4>Devoluciones Registradas</h4>
                </div>
                <table class="table table-striped table-bordered">

                    <thead class="thead-dark">
                        <tr>

                            <th>Codigo</th>
                            <th>Fecha</th>
                            <th>Estado</th>
                            <th>Total</th>
                            <th>Encargado</th>        
                            <th>Envio Devuelto</th>        
                            <th></th>

                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="devolucion" items="${devoluciones}">
                            <tr>
                                <td>${devolucion.codigoDevolucion}</td>
                                <td>${devolucion.fechaDevolucion}</td>
                                <td>${devolucion.estadoDevolucion}</td>
                                <td>Q. ${devolucion.totalDevuelto}</td>
                                <td>${devolucion.encargado}</td>
                                <td>${devolucion.envioDevuelto.codigoEnvio}</td>
                                <%-- ${pageContext.request.contextPath}/ManageControlServlet?accion=eliminarTienda&codigoTienda=${tienda.codigo_tienda} --%>
                                <td><a href="#"
                                       class="btn btn-secondary"> Detalles </a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</section>

<jsp:include page="../storePages/store-controlador.jsp"/>