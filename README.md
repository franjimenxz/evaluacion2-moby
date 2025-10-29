# Sistema de Gestion de Votaciones

API REST para gestionar votaciones de partidos politicos y candidatos

## Descripcion

Este sistema permite registrar partidos politicos, candidatos asociados a esos partidos, y llevar el conteo de votos recibidos por cada candidato. Tambien incluye endpoints para consultar estadisticas de votacion tanto por candidato como por partido

Desarrollado como parte de una evaluacion tecnica backend usando Spring Boot

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (en memoria)
- Maven
- JUnit 5 y Mockito para testing
- Swagger/OpenAPI para documentacion
- Lombok

## Requisitos Previos

- JDK 21 o superior
- Maven 3.6 o superior

## Instalacion

Clonar el repositorio:

```bash
git clone https://github.com/franjimenxz/evaluacion2-moby.git
cd evaluacion2-moby
```

Compilar el proyecto:

```bash
mvn clean install
```

## Ejecucion

Ejecutar la aplicacion:

```bash
mvn spring-boot:run
```

La aplicacion estara disponible en `http://localhost:8080`

## Documentacion de la API

Una vez la aplicacion este corriendo, podes acceder a la documentacion interactiva de Swagger en:

```
http://localhost:8080/swagger-ui.html
```

## Base de Datos

El proyecto usa H2 como base de datos en memoria. Podes acceder a la consola H2 en:

```
http://localhost:8080/h2-console
```

Credenciales:
- JDBC URL: `jdbc:h2:mem:votacionesdb`
- Username: `sa`
- Password: (dejar vacio)

## Endpoints Disponibles

### Partidos Politicos

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | /api/partidos | Crear un nuevo partido politico |
| GET | /api/partidos | Listar todos los partidos |
| GET | /api/partidos/{id} | Buscar partido por ID |
| DELETE | /api/partidos/{id} | Eliminar un partido |

### Candidatos

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | /api/candidatos | Crear un nuevo candidato |
| GET | /api/candidatos | Listar todos los candidatos |
| GET | /api/candidatos/{id} | Buscar candidato por ID |
| DELETE | /api/candidatos/{id} | Eliminar un candidato |
| GET | /api/candidatos/partido/{id} | Listar candidatos de un partido |

### Votos

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | /api/votos | Registrar un voto |
| GET | /api/votos | Listar todos los votos |
| GET | /api/votos/count/candidato/{id} | Contar votos de un candidato |
| GET | /api/votos/count/partido/{id} | Contar votos de un partido |
| GET | /api/votos/estadisticas/candidatos | Estadisticas por candidato |
| GET | /api/votos/estadisticas/partidos | Estadisticas por partido |

## Coleccion Postman

En la carpeta `/docs` encontras la coleccion de Postman con todos los endpoints configurados

Para usarla:
1. Importa el archivo `Sistema-Votaciones.postman_collection.json` en Postman
2. Importa el environment `Sistema-Votaciones.postman_environment.json`
3. Asegurate que la aplicacion este corriendo
4. Ejecuta los requests en el orden sugerido

## Ejecutar Tests

Para correr todos los tests:

```bash
mvn test
```

El proyecto cuenta con 38 tests unitarios que cubren services y controllers

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/mobydigital/votaciones/
│   │       ├── controller/     # REST Controllers
│   │       ├── service/        # Logica de negocio
│   │       ├── repository/     # Acceso a datos
│   │       ├── entity/         # Entidades JPA
│   │       ├── dto/            # Data Transfer Objects
│   │       ├── exception/      # Manejo de excepciones
│   │       └── config/         # Configuracion
│   └── resources/
│       └── application.properties
└── test/
    └── java/                   # Tests unitarios
```

## Calidad de Codigo

El proyecto ha sido analizado con SonarLint para garantizar la calidad del codigo y seguir buenas practicas

## Autor

Franco Jimenez
