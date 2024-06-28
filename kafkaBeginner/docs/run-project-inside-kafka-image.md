# Correr el Proyecto Dentro De La Imagen De Kafka 

Esta no es la opcion mas recomendada, quiza lo ideal sería generar una nueva imagen de nuestro proyecto, subirlo a Docker dentro de la misma red de los componentes de confluent platform, pero para fines practicos y dado que no es un curso de Docker se opta por seguir estos pasos

## 1. Descomentar plugin de maven

Se requiere descomentar el siguiente plugin de maven, este plugin lo que hace es empaquetar dentro de la carpeta target toda las dependencias que va a usar el proyecto

```xml
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-dependency-plugin</artifactId>
				<version>3.1.2</version>
				<executions>
					<execution>
						<id>copy-dependencies</id>
						<phase>package</phase>
						<goals>
							<goal>copy-dependencies</goal>
						</goals>
						<configuration>
							<outputDirectory>
								${project.build.directory}/dependency</outputDirectory>
						</configuration>
					</execution>
				</executions>
			</plugin>
```

## 2. Comentar/Descomentar Properties

Debido a que vamos a ejecutar el proyecto dentro del contenedor de Docker, se requiere comentar/descomentar las siguientes propiedades tanto del Productor como del Consumidor

```
		props.setProperty("bootstrap.servers", "localhost:9092"); // uncomment if you want to run from your machine
//		props.setProperty("bootstrap.servers", "kafka:9092");

		props.setProperty("schema.registry.url", "http://localhost:8081"); // uncomment if you want to run from your machine
//		props.setProperty("schema.registry.url", "http://schema-registry:8081");
```

## 3. Empaquetar el proyecto y copiar las dependencias:

```bash
mvn clean package
```

## 4. Comprimir la carpeta 'target':

```bash
tar -czvf target.tar.gz target
```

## 5. Copiar el archivo .tar.gz al contenedor:

```bash
docker cp target.tar.gz confluent-platform-kafka-1:/target.tar.gz
```

## 6. Ingresar al contenedor:

Es importante que se ingrese al contenedor desde dos terminales diferentes

```bash
docker exec -it confluent-platform-kafka-1 /bin/bash
```

## 7. Descomprimir el archivo .tar.gz dentro del contenedor:

```bash
tar -xzvf /target.tar.gz -C /home/appuser/
```

## 8. Verificar que el directorio dependency existe:

```bash
ls /home/appuser/target/dependency
```

## 9. Ejecutar el OrderConsumer y OrderProducer desde 2 terminales distintas

Terminal 1
```bash 
cd /home/appuser/target/classes
java -cp ".:/home/appuser/target/classes:/home/appuser/target/dependency/*" com.fash.example.kafkav.avroserializers.OrderConsumer 
```

Terminal 2
```bash
cd /home/appuser/target/classes
java -cp ".:/home/appuser/target/classes:/home/appuser/target/dependency/*" com.fash.example.kafkav.avroserializers.OrderProducer
```


**Siguiendo estos pasos, deberías poder ejecutar tus clases OrderConsumer y OrderProducer dentro del contenedor Docker con todas las dependencias necesarias en el classpath.**