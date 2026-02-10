# Reto API – Spring Boot

## 📌 Descripción

API REST desarrollada en **Java 17 con Spring Boot** para la gestión de **Customers** y sus **Accounts** asociadas.

El proyecto demuestra buenas prácticas de:

- Arquitectura en capas
- Diseño de API REST
- Manejo de errores
- Tests unitarios
- Documentación con Swagger

---

## 🧱 Arquitectura

Arquitectura basada en capas:

Controller → Service → Repository

### Tecnologías

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- H2 (base de datos en memoria)
- Maven
- Swagger / OpenAPI
- JUnit 5 + Mockito

---

## 🚀 Cómo ejecutar el proyecto

### Requisitos

- Java 17
- Maven 3.6+

### Pasos

```bash
git clone https://github.com/ing-jjarmenta/api-java-spring-boot.git
cd api-java-spring-boot
mvn spring-boot:run
```

La aplicación quedará disponible en:

[http://localhost:8080](http://localhost:8080)

## Swagger / OpenAPI

La documentación de la API está disponible en:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
