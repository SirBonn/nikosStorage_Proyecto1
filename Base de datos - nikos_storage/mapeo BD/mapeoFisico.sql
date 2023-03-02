DROP DATABASE IF EXISTS nikos_storage;

CREATE DATABASE nikos_storage;
USE nikos_storage;

CREATE TABLE ADMINISTRADORES(
    codigo_administrador INT  AUTO_INCREMENT UNIQUE NOT NULL,
    nivel_usuario INT DEFAULT 0,
    nombre VARCHAR(15) NOT NULL,
    apellido VARCHAR(15) NOT NULL,
    nick VARCHAR(15) NOT NULL,
    user_password VARCHAR(20) NOT NULL,
    email VARCHAR(45) NOT NULL,
    PRIMARY KEY (codigo_administrador)
);

CREATE TABLE BODEGUEROS(
    codigo_bodeguero INT AUTO_INCREMENT UNIQUE NOT NULL,
    nivel_usuario INT DEFAULT 2,
    nombre VARCHAR(15) NOT NULL,
    apellido VARCHAR(15) NOT NULL,
    nick VARCHAR(15) NOT NULL,
    user_password VARCHAR(20) NOT NULL,
    email VARCHAR(45) NOT NULL, 
    PRIMARY KEY (codigo_bodeguero)
);

CREATE TABLE SUPERVISORES(
    codigo_supervisor INT AUTO_INCREMENT UNIQUE NOT NULL,
    nivel_usuario INT DEFAULT 3,
    nombre VARCHAR(15) NOT NULL,
    apellido VARCHAR(15) NOT NULL,
    nick VARCHAR(15) NOT NULL,
    user_password VARCHAR(20) NOT NULL,
    email VARCHAR(45) NOT NULL,
    PRIMARY KEY (codigo_supervisor)
);

CREATE TABLE TIENDAS(
    nombre_tienda VARCHAR(40) NOT NULL,
    codigo_tienda INT AUTO_INCREMENT UNIQUE NOT NULL,
    direccion_tienda VARCHAR(40) NOT NULL,
    tipo_tienda TINYINT(1) NOT NULL,  /*2 tipos, va de un valor 0 -> 1*/
    supervisor_asignado INT NULL,
    bodeguero_proveedor INT NOT NULL,
    PRIMARY KEY (codigo_tienda),
    CONSTRAINT bodeguero_proveedor_fk
    FOREIGN KEY (bodeguero_proveedor)
    REFERENCES BODEGUEROS (codigo_bodeguero),
    CONSTRAINT supervisor_asignado_fk
    FOREIGN KEY (supervisor_asignado)
    REFERENCES SUPERVISORES(codigo_supervisor)
);

CREATE TABLE DEPENDIENTES(
    codigo_dependiente INT AUTO_INCREMENT UNIQUE NOT NULL,
    nivel_usuario INT DEFAULT 1,
    nombre VARCHAR(15) NOT NULL,
    apellido VARCHAR(15) NOT NULL,
    nick VARCHAR(15) NOT NULL,
    user_password VARCHAR(20) NOT NULL,
    email VARCHAR(45) NOT NULL,
    empleado_tienda INT NOT NULL,
    PRIMARY KEY (codigo_dependiente),
    CONSTRAINT empleado_tienda_fk
    FOREIGN KEY (empleado_tienda)
    REFERENCES TIENDAS (codigo_tienda)
);

CREATE TABLE PEDIDOS(
    codigo_pedido INT AUTO_INCREMENT UNIQUE NOT NULL,
    fecha_pedido DATETIME NOT NULL,
    costo_total_pedido DECIMAL(2) NOT NULL,
    estado_pedido TINYINT(4) NOT NULL, /*5 estados, de un valor 0 -> 4*/
    usuario_solicitante INT NOT NULL,
    PRIMARY KEY(codigo_pedido),
    CONSTRAINT usuario_solicita_fk
    FOREIGN KEY(usuario_solicitante) 
    REFERENCES DEPENDIENTES(codigo_dependiente)
);


CREATE TABLE PRODUCTOS(
    codigo_producto INT AUTO_INCREMENT UNIQUE NOT NULL,
    nombre_producto VARCHAR(40) NOT NULL,
    precio_costo_producto DECIMAL(2) NOT NULL,
    precio_venta_producto DECIMAL(2) NOT NULL,
    existencia_producto INT NOT NULL,
    PRIMARY KEY (codigo_producto)
);

CREATE TABLE LISTADO_PRODUCTOS(
    codigo_pedido INT NOT NULL, 
    codigo_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_costo DECIMAL(2) NOT NULL,
    precio_total DECIMAL(2) NOT NULL,
    PRIMARY KEY(codigo_pedido, codigo_producto),
    CONSTRAINT pedido_listado_fk
    FOREIGN KEY (codigo_pedido) 
    REFERENCES PEDIDOS (codigo_pedido),
    CONSTRAINT producto_pedido_fk
    FOREIGN KEY (codigo_producto)
    REFERENCES PRODUCTOS (codigo_producto)
);

CREATE TABLE CATALOGO(
    codigo_tienda INT NOT NULL,
    codigo_producto INT NOT NULL,
    existencia_producto INT NOT NULL,
    PRIMARY KEY(codigo_tienda),
    CONSTRAINT codigo_tienda_fk
    FOREIGN KEY(codigo_tienda) 
    REFERENCES TIENDAS(codigo_tienda),
    CONSTRAINT codigo_producto_fk
    FOREIGN KEY(codigo_producto)
    REFERENCES PRODUCTOS(codigo_producto)
);

CREATE TABLE ENVIOS(
    codigo_envio INT AUTO_INCREMENT UNIQUE NOT NULL,
    fecha_envio DATETIME NOT NULL, 
    fecha_recepcion DATETIME NOT NULL,
    estado_envio TINYINT(1) NOT NULL, /* 2 estados, va de un valor 0 -> 1*/
    precio_envio DECIMAL(2) NOT NULL,
    productos_enviados INT NOT NULL,
    tienda_destino INT NOT NULL,
    PRIMARY KEY (codigo_envio),
    CONSTRAINT productos_enviados_fk
    FOREIGN KEY (productos_enviados)
    REFERENCES PEDIDOS(codigo_pedido),
    CONSTRAINT tienda_destino_fk
    FOREIGN KEY (tienda_destino)
    REFERENCES TIENDAS(codigo_tienda)
);

CREATE TABLE INCIDENCIAS(
    codigo_incidencia INT AUTO_INCREMENT UNIQUE NOT NULL,
    fecha_incidencia DATETIME NOT NULL,
    estado_incidencia TINYINT(1) NOT NULL, /* 2 estados, va de un valor 0->1*/
    total_afectado DECIMAL(2) NOT NULL,
    envio_incidente INT NOT NULL,
    PRIMARY KEY (codigo_incidencia),
    CONSTRAINT envio_incidente_fk
    FOREIGN KEY (envio_incidente) 
    REFERENCES ENVIOS (codigo_envio)
);

CREATE TABLE RECLAMOS_INCIDENCIAS(
    codigo_incidencia INT NOT NULL,
    cantidad_afectada INT NOT NULL,
    motivo_incidencia TINYINT(4) NOT NULL, /*5 motivos, va de un valor 0 -> 4*/
    bodeguero_encargado INT NOT NULL,
    codigo_producto_incidente INT NOT NULL,
    PRIMARY KEY (codigo_incidencia),
    CONSTRAINT codigo_incidencia_fk
    FOREIGN KEY (codigo_incidencia)
    REFERENCES INCIDENCIAS (codigo_incidencia),
    CONSTRAINT bodeguero_encargado_fk_incidencia
    FOREIGN KEY (bodeguero_encargado)
    REFERENCES BODEGUEROS(codigo_bodeguero)
);

CREATE TABLE DEVOLUCIONES(
    codigo_devolucion INT AUTO_INCREMENT UNIQUE NOT NULL,
    fecha_devolucion DATETIME NOT NULL,
    estado_devolucion TINYINT(2) NOT NULL, /*3 estados, de un valor 0 -> 2*/
    total_devuelto DECIMAL(2) NOT NULL,
    envio_devuelto INT NOT NULL,
    PRIMARY KEY (codigo_devolucion),
    CONSTRAINT envio_devuelto_fk
    FOREIGN KEY (envio_devuelto)
    REFERENCES ENVIOS(codigo_envio)
);

CREATE TABLE PRODUCTOS_DEVUELTOS(
    codigo_devolucion INT NOT NULL,
    cantidad_devuelta INT NOT NULL,
    precio_costo_devuelto DECIMAL(2) NOT NULL,
    precio_total_devuelto DECIMAL(2) NOT NULL,
    motivo_devolucion TINYINT(3) NOT NULL, /*4 motivos, de un valor 0 -> 3*/
    bodeguero_encargado INT NOT NULL,
    codigo_producto_incidente INT NOT NULL,
    PRIMARY KEY (codigo_devolucion),
    CONSTRAINT codigo_devolucion_fk
    FOREIGN KEY (codigo_devolucion)
    REFERENCES DEVOLUCIONES (codigo_devolucion),
    CONSTRAINT bodeguero_encargado_fk_devolucion
    FOREIGN KEY (bodeguero_encargado)
    REFERENCES BODEGUEROS(codigo_bodeguero)
);
INSERT INTO ADMINISTRADORES (codigo_administrador, nombre, apellido, nick, user_password, email) 
VALUES (0,'User', 'Pre-Create','admin','admin','email@email.com');