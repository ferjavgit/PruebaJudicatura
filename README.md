# Client Manager API

Microservicio para la gestión de clientes.

## Requisitos

- Java 17
- Gradle
- Docker

## Cómo ejecutar la aplicación

### Ejecución en local

1.  **Clonar el repositorio:**
    ```sh
    git clone <URL_DEL_REPOSITORIO>
    cd client-manager-api
    ```

2.  **Ejecutar la aplicación con Gradle:**
    ```sh
    ./gradlew bootRun
    ```

La aplicación estará disponible en `http://localhost:9090`.

### Ejecución con Docker

1.  **Construir el archivo JAR:**
    Primero, necesitas empaquetar la aplicación en un archivo JAR.
    ```sh
    ./gradlew build
    ```

2.  **Construir la imagen de Docker:**
    Desde la raíz del proyecto, ejecuta el siguiente comando para construir la imagen.
    ```sh
    docker build -t client-manager-api .
    ```

3.  **Ejecutar el contenedor de Docker:**
    Una vez construida la imagen, puedes iniciar un contenedor.
    ```sh
    docker run -p 9090:9090 client-manager-api
    ```

La aplicación estará disponible en `http://localhost:9090`.

## Endpoints de la API

- `GET /api/v1/clients`: Obtiene todos los clientes.
- `GET /api/v1/clients/{id}`: Obtiene un cliente por su ID.
- `POST /api/v1/clients`: Crea un nuevo cliente.
- `PUT /api/v1/clients/{id}`: Actualiza un cliente existente.
- `DELETE /api/v1/clients/{id}`: Elimina un cliente.

### Consola H2

La base de datos en memoria H2 está disponible en la siguiente ruta:
`http://localhost:9090/h2-console`

**Configuración:**
- **Driver Class:** `org.h2.Driver`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User Name:** `sa`
- **Password:** (dejar en blanco)
