# Cojali - Gestión de Taller Mecánico

Aplicación web desarrollada con **Spring Boot**, **Thymeleaf** y **MySQL** para la gestión integral de un taller mecánico: clientes, vehículos, reservas, empleados, órdenes de trabajo, facturación y valoraciones del servicio.

---

##  Funcionalidades principales

###  Clientes
- Registro y login.
- Gestión de sus vehículos.
- Reservas de citas (máx. 5 por día).
- Cancelación y modificación de reservas.
- Visualización de facturas.
- Valoración del servicio.

###  Empleados
- **Recepcionista**: gestión de clientes, vehículos y reservas.
- **Mecánico**: ver y finalizar órdenes asignadas.
- **Admin**: gestión de empleados, asignación de tareas, control total.

###  Órdenes de trabajo y facturación
- Generación de órdenes desde reservas.
- Asignación a mecánicos.
- Factura automática al finalizar el trabajo.

###  Valoraciones
- Envío de puntuación y mensaje.
- Visualización de últimas 5 valoraciones en el footer del `index`.

---

##  Tecnologías utilizadas

- Java 17 + Spring Boot
- Spring Security
- Thymeleaf
- MySQL
- Bootstrap
- Docker (opcional)
- Railway (para despliegue online)

---
 Despliegue en Railway
Sube tu proyecto a GitHub (ya hecho ✅).

Ve a https://railway.app y crea un nuevo proyecto.

Elige Deploy from GitHub repo y selecciona Cojali.

Configura las variables de entorno en Railway para que coincidan con tu application.properties:

spring.datasource.url

spring.datasource.username

spring.datasource.password

Railway detectará el Dockerfile o el jar y desplegará la app automáticamente.

Usa el dominio generado para acceder a tu app desde internet.

Estado del proyecto
 En desarrollo activo
 Últimas funcionalidades:

Sistema de roles con seguridad.

Facturación automática.

Valoraciones visibles en el index.

Autor
Ismael Patón
 github.com/IsmaelPaton/Cojali
