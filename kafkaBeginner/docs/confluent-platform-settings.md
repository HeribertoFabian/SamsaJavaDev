# Configuracion del Confluent platform usando Docker

Este archivo muestra la configuracion del dockerFile para levantar la plataforma de Confluent, entre ellos:

- cp-enterprise-control-center 
- cp-ksqldb-server               
- cp-kafka-connect               
- cp-schema-registry            
- cp-kafka                       
- cp-zookeeper  

## Archivos Involucrados

- `confluent-platform/docker-compose.yml`

## Pasos para Ejecutar

1. Abrir powerShell o la terminal de comandos: Navega hasta el Directorio donde guardaste el archivo docker-compose.yml
2. Ejecuta Docker Compose:

```bash
docker-compose up -d
```
Este comando descargará las imágenes necesarias y levantará los contenedores en segundo plano (-d indica "detached mode").

3. Verifica que los contenedores esten corriendo usando el siguiente comando:

```bash
docker ps
```

Deberias ver algo similar a lo siguiente:





4. Acceder a los Servicios
- Puedes acceder al **Control Center** en tu navegador web usando la URL: http://localhost:9021.
- El **Schema Registry** estará disponible en http://localhost:8081.
- **Kafka Connect** estará disponible en http://localhost:8083.
- **ksqlDB Server** estará disponible en http://localhost:8088.