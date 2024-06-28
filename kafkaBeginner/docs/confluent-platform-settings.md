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

### 1. Abrir powerShell o la terminal de comandos: 

Navega hasta el Directorio donde guardaste el archivo docker-compose.yml

### 2. Ejecuta Docker Compose:

```bash
docker-compose up -d
```
Este comando descargará las imágenes necesarias y levantará los contenedores en segundo plano (-d indica "detached mode").

### 3. Verifica que los contenedores esten corriendo usando el siguiente comando:

```bash
docker ps
```

Deberias ver algo similar a lo siguiente:

```bash
CONTAINER ID   IMAGE                                             COMMAND                  CREATED          STATUS                      PORTS                              NAMES
9f697dde8981   confluentinc/cp-enterprise-control-center:7.3.0   "/etc/confluent/dock…"   52 minutes ago   Up 52 minutes               0.0.0.0:9021->9021/tcp             confluent-platform-control-center-1
448df003523c   confluentinc/cp-ksqldb-server:7.3.0               "/etc/confluent/dock…"   52 minutes ago   Up 52 minutes               0.0.0.0:8088->8088/tcp             confluent-platform-ksql-server-1
29780e06d6e8   confluentinc/cp-kafka-connect:7.3.0               "/etc/confluent/dock…"   52 minutes ago   Up 52 minutes (unhealthy)   0.0.0.0:8083->8083/tcp, 9092/tcp   confluent-platform-kafka-connect-1
0ee7bd4f197d   confluentinc/cp-schema-registry:7.3.0             "/etc/confluent/dock…"   52 minutes ago   Up 52 minutes               0.0.0.0:8081->8081/tcp             confluent-platform-schema-registry-1
5f558845a2ac   confluentinc/cp-kafka:7.3.0                       "/etc/confluent/dock…"   52 minutes ago   Up 52 minutes               0.0.0.0:9092->9092/tcp             confluent-platform-kafka-1
c3beb88ad6e5   confluentinc/cp-zookeeper:7.3.0                   "/etc/confluent/dock…"   52 minutes ago   Up 52 minutes               2181/tcp, 2888/tcp, 3888/tcp       confluent-platform-zookeeper-1
```


### 4. Acceder a los Servicios

- Puedes acceder al **Control Center** en tu navegador web usando la URL: http://localhost:9021.
- El **Schema Registry** estará disponible en http://localhost:8081.
- **Kafka Connect** estará disponible en http://localhost:8083.
- **ksqlDB Server** estará disponible en http://localhost:8088.

### 5. Crear un topic 

Identificar el ID o nombre del contenedor de kafka con el comando 

```bash
docker ps
```
Busca la entrada correspondiente al contenedor de kafka, por ejemplo: confluentinc/cp-kafka:7.3.0

Crea un topic de kafka con el comando kafka-topics dentro de ese contenedor, supongamos que el nombre del contenedor es **confluent-platform_kafka_1 kafka-topics** 

```bash
docker exec -it confluent-platform_kafka_1 kafka-topics --create --topic my-new-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

**Desglose del comando:**

- docker exec -it: Ejecuta un comando dentro de un contenedor en modo interactivo.
- confluent-platform_kafka_1: El nombre del contenedor donde se ejecutará el comando.
- kafka-topics: El script de Kafka para administrar topics.
- --create: Indicamos que queremos crear un nuevo topic.
- --topic my-new-topic: El nombre del nuevo topic.
- --bootstrap-server localhost:9092: La dirección del servidor Kafka.
- --partitions 1: El número de particiones del topic.
- --replication-factor 1: El factor de replicación del topic.

### 6. Verificar que el topic fue creado

Para asegurarte que el topic fue creado correctamente, puedes listar los topics utilizando el comando:

```bash
docker exec -it confluent-platform_kafka_1 kafka-topics --list --bootstrap-server localhost:9092
```

Esto te mostrará una lista de todos los topics en tu cluster de kafka


### 7. (OPCIONAL) Detalles del Topic
Si deseas ver los detalles del topic recien creado, puedes utilizar el siguiente comando:

```bash
docker exec -it confluent-platform_kafka_1 kafka-topics --describe --topic my-new-topic --bootstrap-server localhost:9092
```