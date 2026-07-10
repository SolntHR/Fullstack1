**Proyecto Veredict**:

El proyecto esta basado en una arquitectura de microservicios con Spring Boot, orientado a la gestión de usuarios, inventario, carrito de compras, despacho, soporte, reportes, promociones y reseñas.



**Descripción**: 

Los microservicios están desplegados localmente dentro de contenedores Docker, cada uno ejecutándose en un puerto especifico y con responsabilidades separadas: usuario (8081), inventario (8083), carrito (8084), despacho (8085), soporte (8086), reportes (8087), promociones (8088) y reseña (8089). El proyecto usa controladores REST con @RequestBody para recibir JSON y @Valid/@Validated para la validación de datos de entrada. Además, se integra Swagger/OpenAPI para la documentación y visualización interactiva de los servicios, y Eureka para el registro y descubrimiento dinámico de microservicios dentro de la arquitectura. También se incorporan pruebas unitarias sobre la capa de servicio y controladores utilizando Mockito para el aislamiento de dependencias y la verificación del comportamiento.



**Equipo**:

Solange Hernández

Jesús Oropeza

Maximiliano Quezada



**Funcionalidades implementadas**:



**Servicio Usuario** - http://localhost:8081



Gestión de roles: listar, buscar por id, buscar por nombre, agregar y actualizar rol.

Gestión de usuarios: listar, buscar por id, validar existencia, buscar por nombre, apellido o email, agregar, actualizar, eliminar y obtener detalle simple.



**Servicio Inventario** - http://localhost:8083



Gestión de categorías: CRUD, listado DTO, detalle simple y detalle completo.

Gestión de productos: CRUD, búsqueda por id, nombre y categoría, listados DTO, detalle y descuento de stock.



**Servicio Carrito** - http://localhost:8084



Gestión de carritos: listar, buscar, guardar carrito y eliminar.

Gestión de pagos: listar, buscar, guardar pago, eliminar, obtener DTO simple, buscar por rango de fechas y validar compra.



*Integración con inventario y promociones durante el flujo de compra.*



**Servicio Despacho** - http://localhost:8085



Gestión de envíos: listar, buscar, guardar, actualizar y eliminar.

Gestión de verificaciones: listar, buscar, guardar, actualizar y eliminar.



**Servicio Soporte** - http://localhost:8086



Gestión de tickets: listar, buscar por id, buscar por cliente, buscar por estado, agregar, actualizar, eliminar y obtener DTOs.



*Integra usuarios para verificar la existencia de idUsuario.*



**Servicio Reportes** - http://localhost:8087

Gestión de reportes: listar, buscar por id, agregar, eliminar y ver pagos relacionados.



*Integra carrito para lista de ítems pagados.*



**Servicio Promociones** - http://localhost:8088

Gestión de promociones: listar, buscar, buscar por rango de fechas, agregar, actualizar, eliminar, aplicar promoción y listar DTO.



**Servicio Reseña** - http://localhost:8089

Gestión de reseñas: listar, buscar por id, buscar por usuario o producto, agregar, actualizar, eliminar y obtener DTOs.



*Integra carrito para verificar que la compra este completa.*





**Servicios y endpoints para Postman:**



SERVICIO USUARIO http://localhost:8081

ROL: 

/roles/listarRol

/roles/rol/{idRol}

/roles/nombre-rol/{nombreRol}

/roles/agregarRol

/roles/actualizarRol/{idRol}



USUARIO:

/usuarios/listar

/usuarios/user/{idUsuario}

/usuarios/existe/{idUsuario}

/usuarios/nombre/{nombre}

/usuarios/apellido/{apellido}

/usuarios/email/{email}

/usuarios/agregar

/usuarios/actualizar/{idUsuario}

/usuarios/eliminar/{idUsuario}

/usuarios/{idUsuario}/detalle-simple



SERVICIO INVENTARIO http://localhost:8083

CATEGORIA:

/inventario/categoria/listarcategoria

/inventario/categoria/categoriaI/{id}

/inventario/categoria/categoriaN/{nombre}

/inventario/categoria/agregar-categoria

/inventario/categoria/actualizar-categoria/{id}

/inventario/categoria/eliminar/{id}

/inventario/categoria/listar-dto

/inventario/categoria/{id}/detalle-simple

/inventario/categoria/{id}/detalle-completo



PRODUCTO:

/inventario/producto/listar

/inventario/producto/productoI/{id}

/inventario/producto/productoN/{nombre}

/inventario/producto/categoria/{idCategoria}

/inventario/producto/agregar-producto

/inventario/producto/actualizar/{id}

/inventario/producto/eliminar/{id}

/inventario/producto/listado-dto

/inventario/producto/simple-dto

/inventario/producto/{idProducto}/detalle

/inventario/producto/{idProducto}/descontar/{cantidad}



SERVICIO CARRITO http://localhost:8084

CARRITO:

/carrito/listar

/carrito/buscar/{id}

/carrito/guardar-carrito

/carrito/eliminar/{id} - falta un mensaje



PAGO:

/carrito/pagos/listar-pago

/carrito/pagos/buscar-pago/{id}

/carrito/pagos/guardar-pago

/carrito/pagos/eliminar-pago/{id}

/carrito/pagos/pago-simple/{id}

/carrito/pagos/rango-fechas

/carrito/pagos/validar-compra/{idPago}/{idUsuario}/{idProducto}



SERVICIO DESPACHO http://localhost:8085

ENVIO:

/api/envios/listar-envio

/api/envios/buscar-envio/{id}

/api/envios/guardar-envio

/api/envios/actualizar-envio/{id}

/api/envios/eliminar-envio/{id}



VERIFICACION:

/verificaciones/listar-verif

/verificaciones/buscar-verif/{id}

/verificaciones/guardar-verif

/verificaciones/actualizar-verif/{id}

/verificaciones/eliminar-verif/{id}



SERVICIO SOPORTE http://localhost:8086

TICKET:

/soporte/listar

/soporte/ticket/{idTicket}

/soporte/cliente/{idUsuario}

/soporte/estado/{estado}

/soporte/agregarTicket

/soporte/actualizar/{idTicket}

/soporte/eliminar/{idTicket}

/soporte/listadoDTO

/soporte/simpleDTO

/soporte/{idTicket}/detalleDTO





SERVICIO REPORTES http://localhost:8087

REPORTES:

/reportes/listar

/reportes/buscar-por-id/{idReporte}

/reportes/agregar

/reportes/eliminar/{idReporte}

/reportes/{id}/ver-pagos



SERVICIO PROMOCIONES http://localhost:8088 

PROMOCIONES:

/promociones/listar

/promociones/buscar/{idPromocion}

/promociones/buscar-rango-fechas

/promociones/agregar

/promociones/actualizar/{idPromocion}

/promociones/eliminar/{idPromocion}

/promociones/aplicar

/promociones/listar-dto





SERVICIO RESEÑA http://localhost:8089 - FUNCIONA TODO

RESENA:

/resenas/listar

/resenas/resena/{idResena}

/resenas/usuario/{idUsuario}

/resenas/producto/{idProducto}

/resenas/agregarResena

/resenas/actualizar/{idResena}

/resenas/eliminar/{idResena}

/resenas/listadoDTO

/resenas/simpleDTO

/resenas/{idResena}/detalleDTO



**Pasos para ejecutar sin Docker**:



1.Descomprimir el proyecto

2.Encender Laragon.

3.Creación de base de datos en Laragon:

CREATE DATABASE usuario;

CREATE DATABASE inventario;

CREATE DATABASE carrito;

CREATE DATABASE despacho;

CREATE DATABASE soporte;

CREATE DATABASE reportes;

CREATE DATABASE promociones;

CREATE DATABASE resenas;

4.Verificar puertos en application.properties de cada MS.

5.Levantar servicios en orden: usuario, inventario, promociones, carrito, despacho, reportes, ticket, resena.

6\. Abrir Postman y probar endspoints REST.

&#x09;En Postman usar: body -> raw -> JSON

**Usuario**

Usuario: Agregar / Actualizar usuario

{

&#x20; "nombre": "Juanito",

&#x20; "apellido": "Perez Gomez",

&#x20; "email": "juanito.perez@mail.com",

&#x20; "password": "123",

&#x20; "rol": {

&#x20;   "idRol": 1

&#x20; }

}

Rol: Agregar / actualizar rol

{

&#x20; "nombreRol": "EMPLOYEE"

}



**Inventario**

Categoria: Agregar / actualizar categoria

{

&#x20; "nombrecategoria": "Accesorios"

}

Producto: Agregar / actualizar producto

{

&#x20; "nombreProducto": "Audifonos Gamer",

&#x20; "descripcionproducto": "Audifonos con microfono y sonido envolvente",

&#x20; "precio": 39990,

&#x20; "stock": 20,

&#x20; "categoria": {

&#x20;   "idCategoria": 1

&#x20; }

}

**Carrito**

Carrito: Guardar / actualizar carrito

{

&#x20; "idUsuario": 2,

&#x20; "codigoCupon": "GAMER15",

&#x20; "items": \[

&#x20;   {

&#x20;     "idProducto": 3,

&#x20;     "nombreProducto": "Teclado Mecanico",

&#x20;     "cantidad": 1,

&#x20;     "precio": 45990

&#x20;   }

&#x20; ]

}Pago: guardar pago

{

&#x20; "idCarrito": 3,

&#x20; "metodoPago": "TARJETA",

&#x20; "monto": 45990

}

**Despacho**

Envio: Guardar / actualizar envio

{

&#x20; "idCarrito": 3,

&#x20; "direccion": "Av. Los Leones 123",

&#x20; "comuna": "Providencia",

&#x20; "region": "Metropolitana",

&#x20; "estado": "PENDIENTE"

}

Verificación: Guardar / actualizar verificación

{

&#x20; "idEnvio": 1,

&#x20; "fechaEntrega": "2026-06-04",

&#x20; "estadoEntrega": "ENTREGADO",

&#x20; "observacion": "Sin novedades"

}



**Soporte**

Agregar ticket

{

&#x20; "idUsuario": 2,

&#x20; "descripcion": "El producto llegó con falla en el botón de encendido"

}

Actualizar ticket

{

&#x20; "idUsuario": 2,

&#x20; "descripcion": "El producto sigue presentando falla luego de reinicio",

&#x20; "estado": "ENPROCESO"

}



**Reporte**

Agregar / actualizar reporte

{

&#x20; "nombreReporte": "Reporte Junio",

&#x20; "descripcionReporte": "Resumen de ventas del mes",

&#x20; "tipoReporte": "VENTASMENSUAL",

&#x20; "fechaInicio": "2026-06-01",

&#x20; "fechaFin": "2026-06-30"

}



**Promociones**

Agregar / actualizar promoción

{

&#x20; "nombrePromocion": "Promo Gamer",

&#x20; "codigoPromocional": "GAMER15",

&#x20; "descuento": 15.00,

&#x20; "fechaInicio": "2026-06-01",

&#x20; "fechaFin": "2026-06-30",

&#x20; "vecesUso": 100,

&#x20; "montoMinimo": 30000

}



Reseña

Agregar / actualizar resena

{

&#x20; "idUsuario": 2,

&#x20; "idProducto": 3,

&#x20; "idPago": 5,

&#x20; "estrellas": 5,

&#x20; "comentario": "Excelente producto, llegó en buen estado y funciona perfecto."

}



**Pasos a ejecutar con Docker**:

1. Iniciar Docker Desktop
2. Descargar repositorio y descomprimirlo
3. En la raíz del proyecto abrir terminal e ingresar el comando de construcción: docker compose up -d --build
4. Esperar a que se creen los contenedores.
5. Una vez que los servicios se encuentren activos se puede comprobar su registro en Eureka: localhost:8761. Y ver la documentación Swagger visitando los puertos utilizados agregando /swagger-ui/index.html
6. En caso de realizar cambios en el repositorio los contenedores de deben actualizar manualmente usando el mismo comando de construcción.