<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                            <th>Tienda Solicitante</th>    
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
                                <td>${pedido.dependiente.tienda}</td>
                                <td>Q. ${pedido.costoPedido}</td>
                                <td>
                                    <%-- boton de listar envia por get para recibir lista de productos --%>
                                    <%-- ${pageContext.request.contextPath}/ManageControlServlet?accion=eliminarTienda&codigoTienda=${tienda.codigo_tienda} --%>

                                    <c:set var="estadoPedido" value="${pedido.estadoPedido}" />                               
                                    <c:set var="estado" value="COMPLETADO" />
                                    <c:set var="estado1" value="RECHAZADO" />
                                    <c:set var="estado2" value="SOLICITADO"/>

                                    <c:if test="${ estado == estadoPedido }">
                                        <a href="${pageContext.request.contextPath}/bodegaControlServlet?accion=verPedido&codigoPedido=${pedido.codigoPedido}"
                                           class="btn btn-primary"> Ver Pedido</a>
                                    </c:if>
                                    <c:if test="${ estado1 == estadoPedido }">
                                        <a href="${pageContext.request.contextPath}/bodegaControlServlet?accion=verPedido&codigoPedido=${pedido.codigoPedido}"
                                           class="btn btn-primary"> Ver Pedido</a>
                                    </c:if>
                                    <c:if test="${ estado2 == estadoPedido }">
                                        <a href="${pageContext.request.contextPath}/bodegaControlServlet?accion=aprobarEnvio&codigoPedido=${pedido.codigoPedido}"
                                           class="btn btn-success"> Aprobar envio</a>
                                        <a href="${pageContext.request.contextPath}/bodegaControlServlet?accion=rechazarPedido&codigoPedido=${pedido.codigoPedido}"
                                           class="btn btn-danger"> Rechazar envio</a>
                                    </c:if>

                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="col-sm-3 col-md-6 col-lg-4">
            <c:if test="${ not empty pedidoRegistrado}">

                <div class="card">
                    <div class="card-header">
                        <h4>Detalle de pedido #${pedidoRegistrado.codigoPedido}</h4>
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
            </c:if>
        </div>
    </div>
</section>

<!-- Listado envios -->
<section id="enviosList">
    <div class="container-fluid row">

        <!-- Tabla de envios -->
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
                                <td>
                                    <c:set var="estadoEnvio" value="${envio.estado}" />
                                    <c:set var="isRec" value="RECIBIDO" />
                                    <c:if test="${ isRec == estadoEnvio }">
                                        ENTREGADO
                                    </c:if> <c:if test="${ isRec != estadoEnvio }">
                                        ${envio.estado}
                                    </c:if>
                                </td>
                                <td>${envio.tiendaDestino}</td>
                                <td>Q. ${envio.totalEnvio}</td>
                                <td>${envio.pedidoEnviado.codigoPedido}</td>
                                <td><a href="${pageContext.request.contextPath}/bodegaControlServlet?accion=verEnvio&codigoPedido=${envio.codigoEnvio}" 
                                       class="btn btn-secondary"> Detalles </a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Tabla de detalle del envio -->
        <div class="col-sm-3 col-md-6 col-lg-4">
            <c:if test="${ not empty envioRegistrado}">
                <div class="card">
                    <div class="card-header">
                        <h4>Detalle de envio #${envioRegistrado.codigoEnvio}</h4>
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


                </div>
            </c:if>
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
                            <th>Solucion</th>
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
                                <td>${incidencia.solucion}</td>
                                <td>${incidencia.encargado}</td>
                                <td>${incidencia.envioDevuelto.codigoEnvio}</td>

                                <%-- ${pageContext.request.contextPath}/ManageControlServlet?accion=eliminarTienda&codigoTienda=${tienda.codigo_tienda} --%>
                                <td>

                                    <c:set var="estadoIncidencia" value="${incidencia.estadoIncidencia}" />                               
                                    <c:set var="estado" value="ACTIVA" />
                                    <c:set var="estado1" value="SOLUCIONDA" />

                                    <c:if test="${ estado == estadoIncidencia }">
                                        <a href="${pageContext.request.contextPath}/bodegaControlServlet?accion=solucionarIncidencia&codigo=${incidencia.codigo}"
                                           class="btn btn-primary"> Solucionar</a>
                                    </c:if>
                                    <c:if test="${ estado1 == estadoIncidencia }">
                                        <a href="#"
                                           class="disabled btn btn-secondary disabled-linky"> Solucionada </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <c:if test="${not empty incidenciaSolucionable}">
            <div class="col-sm-3 col-md-6 col-lg-4 col-xl-2">
                <div class="card">
                    <div class="card-header">
                        <h4>Incidencia #${incidenciaSolucionable.codigo}</h4>
                    </div>
                    <form action="${pageContext.request.contextPath}/bodegaControlServlet?accion=actualizarIncidencia&codigo=${incidenciaSolucionable.codigo}" method="POST">
                        <div class="form-group">
                            <label for="solucion">Solucion: </label>
                            <textarea class="form-control" rows="5" id="solucion" name="solucion" required maxlength="50"></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">Solucionar</button>
                    </form>
                </div>
            </div>
        </c:if>
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
                                <td>

                                    <c:set var="estadoDevolucion" value="${devolucion.estadoDevolucion}" />                               
                                    <c:set var="estado" value="ACTIVA" />

                                    <c:if test="${ estado == estadoDevolucion }">
                                        <a href="${pageContext.request.contextPath}/bodegaControlServlet?accion=evaluarDevolucion&codigo=${devolucion.codigoDevolucion}&isRechazada=false"
                                           class="btn btn-success"> Aprobar Devolucion</a>
                                        <a href="${pageContext.request.contextPath}/bodegaControlServlet?accion=evaluarDevolucion&codigo=${devolucion.codigoDevolucion}&isRechazada=true"
                                           class="btn btn-danger"> Rechazar Devolucion</a>
                                    </c:if>
                                    <c:if test="${ estado != estadoDevolucion }">
                                        <a href="#"
                                           class="disabled btn btn-secondary disabled-linky"> Evaluada</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</section>