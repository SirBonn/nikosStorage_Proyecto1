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

    ObjectMapper objectMapper = new ObjectMapper();
    InputStream fileContent;
    BufferedReader bufferedReader;
    JsonNode rootNode;
    String json;

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

        cargarProductos();
        cargarTiendas();
        cargarUsuarios();

        return rowsAffected;
    }

    private int cargarUsuarios() {
        int rowsAffected = 0;

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        //supervisores
        ArrayList<Supervisor> supervisores = new ArrayList<>();
        JsonNode supervisorNode = rootNode.get("supervisores");

        for (JsonNode usuarioJsonNode : supervisorNode) {

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
        ArrayList<Usuario> usuarios = new ArrayList<>();
        JsonNode usuariosJsonNode = rootNode.get("admins");

        for (JsonNode usuarioJsonNode : usuariosJsonNode) {

            int codigo = usuarioJsonNode.get("codigo").asInt();
            String nombre = usuarioJsonNode.get("nombre").asText();
            String username = usuarioJsonNode.get("username").asText();
            String password = usuarioJsonNode.get("password").asText();
            String email = usuarioJsonNode.get("email").asText();
            Usuario usuario = new Usuario(codigo, nombre, username, password, email);
            usuarios.add(usuario);

        }
        for (Usuario usr : usuarios) {

            rowsAffected += usuarioDAO.insertUsuario(usr, "SUPERVISORES");
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

        int rowsAffected = 0;

        ArrayList<Tienda> tiendas = new ArrayList<>();
        JsonNode tiendasNode = rootNode.get("tiendas");

        for (JsonNode tiendaNode : tiendasNode) {

            int codigoTda = tiendaNode.get("codigo").asInt();
            String direccion = tiendaNode.get("direccion").asText();
            String tipo = tiendaNode.get("tipo").asText();
            JsonNode productos = tiendaNode.get("productos");
            Tienda tienda = new Tienda(codigoTda, direccion, tipo);
            tienda.crearCatalogo();
            for (JsonNode productosArray : productos) {
                int codigoPdc = productosArray.get("codigo").asInt();
                int existenciasPdc = productosArray.get("existencias").asInt();

                Producto producto = new Producto(codigoPdc, existenciasPdc);
                tienda.agregarAlCatalogo(producto);
            }
            tiendas.add(tienda);

        }
        for (Tienda td : tiendas) {

            rowsAffected += tiendaDAO.insertarTienda(td);
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

}
