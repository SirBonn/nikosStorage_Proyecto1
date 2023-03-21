<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="${pageContext.request.contextPath}/storeControlServlet?accion=enviarReporte&isDev=${isDev}" method="POST">

    <secion id="formEdit" >
        <div class="container">
            <div class="row">
                <div class="col">
                    <div class="card ">
                        <div class="card-header">
                            <div class="col-xl-3">
                            </div>
                            <c:if test="${isDev == false}">
                                <h4>Reportar Producto</h4>
                            </c:if>
                            <c:if test="${isDev != false}">
                                <h4>Solicitar devolucion</h4>
                            </c:if>

                        </div>

                        <div class="card-body">
                            <div class="form-group row">
                                <div class="col">
                                    <label for="codigo">Codigo del producto</label>
                                    <input type="text" class="form-control" name="codigoProducto"readonly  required value="${productoRep.codigo}">
                                </div>
                                <div class="col">
                                    <label for="nombre">Producto</label>
                                    <input type="text" class="form-control" name="nombre" readonly required value="${productoRep.nombre}">
                                </div>
                                <div class="col">
                                    <label for="codigoEnvio">Parte del envio</label>
                                    <input type="text" class="form-control" name="codigoEnvio" readonly required value="${envio.codigoEnvio}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col">
                                    <select name="cantidad" class="custom-select mb-3 col">
                                        <option selected>Cantidad</option>

                                        <c:forEach var="cantidad" begin="1" end="${productoRep.cantidad}" step="1">
                                            <option value ="${cantidad}">${cantidad}</option> 
                                        </c:forEach>

                                    </select>
                                </div>
                                <div class="col">
                                    <select name="motivo" class="custom-select mb-3 col">
                                        <option selected>Motivo</option>
                                        <option value="PRODUCTO EQUIVOCADO">Producto equivocado</option>
                                        <option value="PRODUCTO DAÑADO">Producto dañado</option>
                                        <option value="PRODUCTO NO SOLICITADO">producto no solicitrado</option>
                                        <option value="SOBRANTE DE PRODUCTO">sobrante de producto</option>
                                    </select>
                                </div>
                                <div class="col">
                                    <select name="encargado" class="custom-select mb-3 col">
                                        <option selected>Asigna un encargado</option>
                                        <c:forEach var="bodeguero" items="${bodegueros}">

                                            <option value ="${bodeguero.codigo}"> ${bodeguero.codigo} ${bodeguero.nombre}</option> 

                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </secion>
    <section id="actions" class="py-4 bg-light">
        <div class="container">
            <div class="row">
                <div class="col-xl-6">
                    <a href="${pageContext.request.contextPath}/storeControlServlet" class="btn btn-secondary btn-block">
                        Cancelar
                    </a>
                </div>
                <div class="col-xl-6">
                    <button type="submit" class="btn btn-danger btn-block">
                        Enviar Solicitud
                    </button>
                </div>
            </div>
        </div>
    </section>
</form>