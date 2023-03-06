
<div class="modal fade" id="importarArchivo" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-tittle">Importar archivo</h5>
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div >
                <form class="" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/filesMagnamentServlet"> 
                    <div class="modal-body">
                        <input type="file" class="form-control-file border" id="JSONfile" name="JSONfile">
                    </div>

                    <div class="modal-footer">
                        <button class="btn btn-primary" type="submit"> Cargar</button>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>
