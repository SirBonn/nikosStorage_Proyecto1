DROP DATABASE IF EXISTS nikos_storage;

CREATE DATABASE nikos_storage;
USE nikos_storage;

CREATE TABLE TIENDAS(
    codigo_tienda INT AUTO_INCREMENT NOT NULL,
    nombre_tienda VARCHAR(40) NULL,
    direccion_tienda VARCHAR(40) NOT NULL,
    tipo_tienda VARCHAR(12) NOT NULL,
    PRIMARY KEY (codigo_tienda)
);


CREATE TABLE PRODUCTOS(
    codigo_producto INT AUTO_INCREMENT NOT NULL,
    nombre_producto VARCHAR(40) NOT NULL,
    precio_costo_producto DECIMAL(9,2) NOT NULL,
    precio_venta_producto DECIMAL(9,2) NOT NULL,
    existencia_producto INT NOT NULL,
    PRIMARY KEY (codigo_producto)
);

CREATE TABLE ADMINISTRADORES(
    codigo INT AUTO_INCREMENT NOT NULL,
    nivel_usuario INT DEFAULT 0,
    nombre VARCHAR(25) NOT NULL,
    apellido VARCHAR(15) NULL,
    nick VARCHAR(15) NOT NULL,
    user_password VARCHAR(65) NOT NULL,
    email VARCHAR(45) NULL,
    PRIMARY KEY (codigo)
);

CREATE TABLE BODEGUEROS(
    codigo INT AUTO_INCREMENT NOT NULL,
    nivel_usuario INT DEFAULT 2,
    nombre VARCHAR(25) NOT NULL,
    apellido VARCHAR(15) NULL,
    nick VARCHAR(15) NOT NULL,
    user_password VARCHAR(65) NOT NULL,
    email VARCHAR(45) NOT NULL, 
    tienda_asignada INT NOT NULL,
    PRIMARY KEY (codigo),
    CONSTRAINT tienda_atendida_fk
    FOREIGN KEY(tienda_asignada)
    REFERENCES TIENDAS(codigo_tienda)
);

CREATE TABLE SUPERVISORES(
    codigo INT AUTO_INCREMENT NOT NULL,
    nivel_usuario INT DEFAULT 3,
    nombre VARCHAR(25) NOT NULL,
    apellido VARCHAR(15) NULL,
    nick VARCHAR(15) NOT NULL,
    user_password VARCHAR(65) NOT NULL,
    email VARCHAR(45) NOT NULL,
    PRIMARY KEY (codigo)
);


CREATE TABLE DEPENDIENTES(
    codigo INT AUTO_INCREMENT NOT NULL,
    nivel_usuario INT DEFAULT 1,
    nombre VARCHAR(25) NOT NULL,
    apellido VARCHAR(15) NULL,
    nick VARCHAR(15) NOT NULL,
    user_password VARCHAR(65) NOT NULL,
    email VARCHAR(45) NOT NULL,
    tienda_asignada INT NOT NULL,
    PRIMARY KEY (codigo),
    CONSTRAINT tienda_dependiente_fk
    FOREIGN KEY (tienda_asignada)
    REFERENCES TIENDAS (codigo_tienda)
);

CREATE TABLE PEDIDOS(
    codigo_pedido INT AUTO_INCREMENT NOT NULL,
    fecha_pedido DATE NOT NULL,
    tienda_solicitante INT NOT NULL,
    costo_total_pedido DECIMAL(9,2) NOT NULL,
    estado_pedido VARCHAR(15) NOT NULL, /*9 estados, de un valor 0 -> 4*/
    usuario_solicitante INT NOT NULL,
    PRIMARY KEY(codigo_pedido),
    CONSTRAINT tienda_solicitante_fk
    FOREIGN KEY (tienda_solicitante)
    REFERENCES TIENDAS(codigo_tienda),
    CONSTRAINT usuario_solicita_fk
    FOREIGN KEY(usuario_solicitante) 
    REFERENCES DEPENDIENTES(codigo)
);


CREATE TABLE LISTADO_PRODUCTOS(
    codigo_pedido INT NOT NULL, 
    codigo_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_costo DECIMAL(9,2) NOT NULL,
    precio_total DECIMAL(9,2) NOT NULL,
    /*PRIMARY KEY(codigo_pedido, codigo_producto),*/
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
    CONSTRAINT codigo_tienda_fk
    FOREIGN KEY(codigo_tienda) 
    REFERENCES TIENDAS(codigo_tienda),
    CONSTRAINT codigo_producto_fk
    FOREIGN KEY(codigo_producto)
    REFERENCES PRODUCTOS(codigo_producto)
);

CREATE TABLE ENVIOS(
    codigo_envio INT AUTO_INCREMENT NOT NULL,
    fecha_envio DATE NOT NULL, 
    fecha_recepcion DATE NULL,
    estado_envio VARCHAR(15) NOT NULL, /* 2 estados, va de un valor 0 -> 1*/
    precio_envio DECIMAL(9,2) NOT NULL,
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
    codigo_incidencia INT AUTO_INCREMENT NOT NULL,
    fecha_incidencia DATE NOT NULL,
    estado_incidencia VARCHAR(15) NOT NULL, /* 2 estados, va de un valor 0->1*/
    solucion_incidencia VARCHAR(50) NULL,
    bodeguero_encargado INT NOT NULL,
    envio_incidente INT NULL,
    PRIMARY KEY (codigo_incidencia),
    CONSTRAINT bodeguero_encargado_fk_incidencia
    FOREIGN KEY (bodeguero_encargado)
    REFERENCES BODEGUEROS(codigo)
);

CREATE TABLE RECLAMOS_INCIDENCIAS(
    codigo_incidencia INT NOT NULL,
    cantidad_afectada INT NOT NULL,
    motivo_incidencia VARCHAR(25) NOT NULL, /*9 motivos, va de un valor 0 -> 4*/
    codigo_producto_incidente INT NOT NULL,
    PRIMARY KEY (codigo_incidencia),
    CONSTRAINT codigo_incidencia_fk
    FOREIGN KEY (codigo_incidencia)
    REFERENCES INCIDENCIAS (codigo_incidencia),
    CONSTRAINT producto_incidente_fk
    FOREIGN KEY (codigo_producto_incidente)
    REFERENCES PRODUCTOS(codigo_producto)
);

CREATE TABLE DEVOLUCIONES(
    codigo_devolucion INT AUTO_INCREMENT NOT NULL,
    fecha_devolucion DATE NOT NULL,
    estado_devolucion VARCHAR(15) NOT NULL, /*3 estados, de un valor 0 -> 2*/
    total_devuelto DECIMAL(9,2) NOT NULL,
    bodeguero_encargado INT NOT NULL,
    envio_devuelto INT NULL,
    PRIMARY KEY (codigo_devolucion),
    CONSTRAINT bodeguero_encargado_fk_devolucion
    FOREIGN KEY (bodeguero_encargado)
    REFERENCES BODEGUEROS(codigo)
);

CREATE TABLE PRODUCTOS_DEVUELTOS(
    codigo_devolucion INT NOT NULL,
    cantidad_devuelta INT NOT NULL,
    precio_costo_devuelto DECIMAL(9,2) NOT NULL,
    precio_total_devuelto DECIMAL(9,2) NOT NULL,
    motivo_devolucion VARCHAR(25) NOT NULL, /*4 motivos, de un valor 0 -> 3*/
    codigo_producto_devuelto INT NOT NULL,
    /*PRIMARY KEY (codigo_devolucion),*/
    CONSTRAINT codigo_devolucion_fk
    FOREIGN KEY (codigo_devolucion)
    REFERENCES DEVOLUCIONES (codigo_devolucion),
    CONSTRAINT producto_devuelto_fk
    FOREIGN KEY (codigo_producto_devuelto)
    REFERENCES PRODUCTOS(codigo_producto)
);
INSERT INTO ADMINISTRADORES (codigo, nombre, apellido, nick, user_password, email) 
VALUES (0,'User', 'Pre-Create','admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','email@email.com');