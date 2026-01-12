# Usa una imagen base de OpenJDK 17
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR generado por Gradle al contenedor
# El nombre del JAR se basa en la configuración de build.gradle
ARG JAR_FILE=build/libs/client-manager-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Expone el puerto 9090 para que sea accesible desde fuera del contenedor
EXPOSE 9090

# Define el comando para ejecutar la aplicación al iniciar el contenedor
ENTRYPOINT ["java","-jar","app.jar"]
