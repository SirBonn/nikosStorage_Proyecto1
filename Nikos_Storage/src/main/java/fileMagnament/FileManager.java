package fileMagnament;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;

import dataBase.*;
import dominio.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FileManager {

    private ObjectMapper objectMapper = new ObjectMapper();
    private InputStream fileContent;
    private BufferedReader bufferedReader;
    private JsonNode rootNode;
    private String json;
    public static String informe = "";

    public FileManager(String fileContent, BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
        this.json = fileContent;
        initJasonRootNode();

    }

    private void initJasonRootNode() {

        try {

            this.rootNode = objectMapper.readTree(json);
            System.out.println("exito ocn rootNode");
        } catch (JsonProcessingException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {

            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public int cargarDatos() throws IllegalArgumentException, JsonProcessingException {
        int rowsAffected = 0;

        rowsAffected += cargarProductos();
        rowsAffected += cargarTiendas();
        rowsAffected += cargarUsuarios();
        rowsAffected += cargarPedidos();
        rowsAffected += cargarEnvios();
        rowsAffected += cargarDevoluciones();
        rowsAffected += cargarIncidencias();
        return rowsAffected;
    }

    private int cargarUsuarios() {
        int rowsAffected = 0;

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        //supervisores
        ArrayList<Supervisor> supervisores = new ArrayList<>();
        JsonNode usuariosJsonNode = rootNode.get("supervisores");

        for (JsonNode usuarioJsonNode : usuariosJsonNode) {

            int codigo = usuarioJsonNode.get("codigo").asInt();
            String nombre = usuarioJsonNode.get("nombre").asText();
            String username = usuarioJsonNode.get("username").asText();
            String password = usuarioJsonNode.get("password").asText();
            String email = usuarioJsonNode.get("email").asText();
            Supervisor supervisor = new Supervisor(codigo, nombre, username, password, email);
            supervisores.add(supervisor);

        }
        for (Supervisor sup : supervisores) {

            rowsAffected += usuarioDAO.insertUsuario(sup, "SUPERVISORES");
        }

        //Administradores
        ArrayList<Usuario> admins = new ArrayList<>();
        JsonNode adminsNode = rootNode.get("admins");

        for (JsonNode usuarioJsonNode : adminsNode) {

            String nombre = usuarioJsonNode.get("nombre").asText();
            String username = usuarioJsonNode.get("username").asText();
            String password = usuarioJsonNode.get("password").asText();
            Usuario admin = new Usuario(nombre, username, password);
            admins.add(admin);

        }
        for (Usuario adm : admins) {

            rowsAffected += usuarioDAO.insertUsuario(adm, "ADMINISTRADORES");
        }

        //Dependientes
        ArrayList<Dependiente> dependientes = new ArrayList<>();
        JsonNode dependientesNode = rootNode.get("usuariostienda");

        for (JsonNode dependienteNode : dependientesNode) {

            int codigo = dependienteNode.get("codigo").asInt();
            int tienda = dependienteNode.get("tienda").asInt();
            String nombre = dependienteNode.get("nombre").asText();
            String username = dependienteNode.get("username").asText();
            String password = dependienteNode.get("password").asText();
            String email = dependienteNode.get("email").asText();
            Dependiente dependiente = new Dependiente(codigo, nombre, username, password, email, tienda);
            dependientes.add(dependiente);

        }

        for (Dependiente dep : dependientes) {

            rowsAffected += usuarioDAO.insertUsuario(dep, "DEPENDIENTES");
        }

        //Bodegueros
        ArrayList<Bodeguero> bodegueros = new ArrayList<>();
        JsonNode bodeguerosNode = rootNode.get("encargadosBodega");

        for (JsonNode bodegueroNode : bodeguerosNode) {

            JsonNode tiendas = bodegueroNode.get("tiendas");

            for (JsonNode tiendaArray : tiendas) {
                int codigo = bodegueroNode.get("codigo").asInt();
                int tienda = tiendaArray.asInt();
                String nombre = bodegueroNode.get("nombre").asText();
                String username = bodegueroNode.get("username").asText();
                String password = bodegueroNode.get("password").asText();
                String email = bodegueroNode.get("email").asText();
                Bodeguero bodeguero = new Bodeguero(codigo, nombre, username, password, email, tienda);
                bodegueros.add(bodeguero);
            }

        }
        for (Bodeguero bod : bodegueros) {

            rowsAffected += usuarioDAO.insertUsuario(bod, "BODEGUEROS");
        }

        return rowsAffected;
    }

    private int cargarTiendas() {

        TiendaDAO tiendaDAO = new TiendaDAO();
        CatalogoDAO catalogoDAO = new CatalogoDAO();
        int rowsAffected = 0;

        ArrayList<Tienda> tiendas = new ArrayList<>();
        JsonNode tiendasNode = rootNode.get("tiendas");

        for (JsonNode tiendaNode : tiendasNode) {

            int codigoTda = tiendaNode.get("codigo").asInt();
            String direccion = tiendaNode.get("direccion").asText();
            String tipo = tiendaNode.get("tipo").asText();
            JsonNode catalogo = tiendaNode.get("productos");
            Tienda tienda = new Tienda(codigoTda, direccion, tipo);
            tienda.crearCatalogo();
            for (JsonNode catArray : catalogo) {

                int codigoPdc = catArray.get("codigo").asInt();
                int existenciasPdc = catArray.get("existencias").asInt();

                tienda.agregarAlCatalogo(new Producto(codigoPdc, existenciasPdc));
            }
            tiendas.add(tienda);

        }
        for (Tienda td : tiendas) {

            rowsAffected += tiendaDAO.insertarTienda(td);
            rowsAffected += catalogoDAO.insertarCatalogo(td.getCatalogo());
        }

        return rowsAffected;
    }

    private int cargarProductos() {

        int rows = 0;
        ProductoDAO productoDAO = new ProductoDAO();

        ArrayList<Producto> productos = new ArrayList<>();
        JsonNode productoJsonNode = rootNode.get("productos");

        for (JsonNode productoNode : productoJsonNode) {
            int codigo = productoNode.get("codigo").asInt();
            String nombre = productoNode.get("nombre").asText();
            double costo = productoNode.get("costo").asDouble();
            double precio = productoNode.get("precio").asDouble();
            int existencia = productoNode.get("existencias").asInt();

            Producto producto = new Producto(codigo, nombre, costo, precio, existencia);
            productos.add(producto);

        }

        for (Producto producto : productos) {
            rows = productoDAO.insertarProducto(producto);
        }

        return rows;

    }

    private int cargarPedidos() {

        PedidoDAO pedidoDAO = new PedidoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ListadoProductosDAO listadoProductosDAO = new ListadoProductosDAO();
        int rowsAffected = 0;

        ArrayList<Pedido> pedidos = new ArrayList<>();
        JsonNode pedidosNode = rootNode.get("pedidos");

        for (JsonNode pedidoNode : pedidosNode) {

            int codigo = pedidoNode.get("id").asInt();
            int tiendaCodigo = pedidoNode.get("tienda").asInt();
            int usuario = pedidoNode.get("usuario").asInt();
            Usuario usuarioSol = new Usuario(usuario);
            usuarioSol = usuarioDAO.buscarUsuario(usuarioSol, "DEPENDIENTES");
            Tienda tienda = new Tienda(tiendaCodigo);
            tienda = new TiendaDAO().buscarTienda(tienda);
            String fecha = pedidoNode.get("fecha").asText();
            double total = pedidoNode.get("total").asDouble();
            String estado = pedidoNode.get("estado").asText();
            Pedido pedido = new Pedido(codigo, fecha, total, estado, usuarioSol, tienda);
            pedido.crearLista();
            JsonNode listadoProductosNode = pedidoNode.get("productos");

            for (JsonNode productoNode : listadoProductosNode) {
                int codigoPd = productoNode.get("codigo").asInt();
                double precio = productoNode.get("costoU").asDouble();
                int cantidad = productoNode.get("cantidad").asInt();
                double precioTotal = productoNode.get("costoTotal").asDouble();

                pedido.agregarAlListado(new Producto(codigoPd, precio, cantidad));

            }

            pedidos.add(pedido);

        }
        for (Pedido pd : pedidos) {

            rowsAffected += pedidoDAO.insertarPedido(pd);
            rowsAffected += listadoProductosDAO.insertarProducto(pd.getListadoProductos());
        }

        return rowsAffected;

    }

    private int cargarEnvios() {

        EnvioDAO envioDAO = new EnvioDAO();
        TiendaDAO tiendaDAO = new TiendaDAO();
        ListadoProductosDAO listadoProductosDAO = new ListadoProductosDAO();
        int rowsAffected = 0;

        ArrayList<Envio> envios = new ArrayList<>();
        JsonNode enviosNode = rootNode.get("envios");

        for (JsonNode envioNode : enviosNode) {

            int codigo = envioNode.get("id").asInt();
            int tienda = envioNode.get("tienda").asInt();
            int pedidoCodigo = envioNode.get("pedido").asInt();
            Pedido pedido = new PedidoDAO().buscarPedido(new Pedido(pedidoCodigo));
            Tienda tiendaDest = new Tienda(tienda);
            tiendaDest = tiendaDAO.buscarTienda(tiendaDest);
            String fechaSalida = envioNode.get("fechaSalida").asText();
            String fechaRecibido = envioNode.get("fechaRecibido").asText();
            double total = envioNode.get("total").asDouble();
            String estado = envioNode.get("estado").asText();
            Envio envio = new Envio(codigo, fechaSalida, fechaRecibido, estado, total, pedido, tiendaDest);
            envio.crearLista();
            JsonNode listadoProductosNode = envioNode.get("productos");

            for (JsonNode productoNode : listadoProductosNode) {
                int codigoPd = productoNode.get("codigo").asInt();
                double precio = productoNode.get("costoU").asDouble();
                int cantidad = productoNode.get("cantidad").asInt();
                double precioTotal = productoNode.get("costoTotal").asDouble();

                //envio.agregarAlListado(new Producto(codigoPd, precio, cantidad));

            }

            envios.add(envio);

        }
        for (Envio envio : envios) {

            rowsAffected += envioDAO.insertarEnvio(envio);
            rowsAffected += listadoProductosDAO.insertarProducto(envio.getListadoEnvio());
        }

        return rowsAffected;
    }

    private int cargarDevoluciones() {

        DevolucionDAO devolucionDAO = new DevolucionDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ReclamoDevolucionDAO reclamoDevolucionDAO = new ReclamoDevolucionDAO();
        int rowsAffected = 0;

        ArrayList<Devolucion> devoluciones = new ArrayList<>();
        JsonNode devolucionesNode = rootNode.get("devoluciones");

        for (JsonNode devolucionNode : devolucionesNode) {

            int codigo = devolucionNode.get("id").asInt();
            //int tienda = devolucionNode.get("tienda").asInt();
            int usuario = devolucionNode.get("usuario").asInt();
            Usuario encargado = new Usuario(usuario);
            encargado = usuarioDAO.buscarUsuario(encargado, "BODEGUEROS");
            int envioCod = devolucionNode.get("envio").asInt();
            Envio envio = new EnvioDAO().buscarEnvio(new Envio(envioCod));
            String fecha = devolucionNode.get("fecha").asText();
            double total = devolucionNode.get("total").asDouble();
            String estado = devolucionNode.get("estado").asText();
            Devolucion devolucion = new Devolucion(codigo, fecha, estado, total, envio, encargado);
            JsonNode listadoProductosNode = devolucionNode.get("productos");
            devolucion.crearReclamo();
            for (JsonNode productoNode : listadoProductosNode) {
                int codigoPd = productoNode.get("codigo").asInt();
                double precio = productoNode.get("costo").asDouble();
                int cantidad = productoNode.get("cantidad").asInt();
                double precioTotal = productoNode.get("costoTotal").asDouble();
                String motivo = productoNode.get("motivo").asText();
                devolucion.agregarProducto(new Producto(codigoPd, precio, cantidad));
                devolucion.getReclamoDevolucion().setMotivoDevolucion(motivo);

            }

            devoluciones.add(devolucion);

        }
        for (Devolucion devolucion : devoluciones) {

            rowsAffected += devolucionDAO.insertarDevolucion(devolucion);
            rowsAffected += reclamoDevolucionDAO.insertarReclamo(devolucion.getReclamoDevolucion());
        }

        return rowsAffected;

    }

    private int cargarIncidencias() {

        IncidenciaDAO incidenciasDAO = new IncidenciaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ReclamoIncidenciaDAO reclamoIncidenciaDAO = new ReclamoIncidenciaDAO();
        int rowsAffected = 0;

        ArrayList<Incidencia> incidencias = new ArrayList<>();
        JsonNode incidenciasNode = rootNode.get("incidencias");

        for (JsonNode incidenciaNode : incidenciasNode) {

            int codigo = incidenciaNode.get("id").asInt();
            //int tienda = devolucionNode.get("tienda").asInt();
            int usuario = incidenciaNode.get("usuario").asInt();
            Usuario encargado = new Usuario(usuario);
            encargado = usuarioDAO.buscarUsuario(encargado, "BODEGUEROS");
            int envioCod = incidenciaNode.get("envio").asInt();
            Envio envio = new EnvioDAO().buscarEnvio(new Envio(envioCod));
            String fechaSalida = incidenciaNode.get("fecha").asText();
            String estado = incidenciaNode.get("estado").asText();
            String solucion = incidenciaNode.get("solucion").asText();
            Incidencia incidencia = new Incidencia(codigo, fechaSalida, estado, encargado, envio);
            JsonNode listadoProductosNode = incidenciaNode.get("productos");
            incidencia.setSolucion(solucion);
            incidencia.crearReclamo();
            for (JsonNode productoNode : listadoProductosNode) {
                int codigoPd = productoNode.get("codigo").asInt();
                int cantidad = productoNode.get("cantidad").asInt();
                String motivo = productoNode.get("motivo").asText();
                incidencia.agregarProducto(new Producto(codigoPd, cantidad));
                incidencia.getReclamoIncidencia().setMotivoIncidencia(motivo);

            }

            incidencias.add(incidencia);

        }
        for (Incidencia incidencia : incidencias) {

            rowsAffected += incidenciasDAO.insertarIncidencia(incidencia);
            rowsAffected += reclamoIncidenciaDAO.insertarReclamo(incidencia.getReclamoIncidencia());
        }

        return rowsAffected;

    }

}
