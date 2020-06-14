# YISAS GATEWAY

## Introducción

Microservicio que hace las veces de puerta de entrada al resto de microservicios del dominio Yisas. A través de redirecciones, actúa de fachada que oculta el acceso directo a los microservicios que forman parte del negocio de la aplicación. De esta forma se pueden aplicar también políticas de seguridad comunes y todos los microservicios. 

* Nombre del microservicio: yisas-gateway

## Desarrollo

### Prerequisitos

* Un IDE con soporte al proyecto Lombok (<https://projectlombok.org/>): Eclipse, IntelliJ, Visual Studio Code.
* Máquina versión Java instalada. Bien:
    * JKD 1.8 de Oracle
    * La JVM OpenJ9 instalada (<https://adoptopenjdk.net/installation.html#linux-pkg>, <https://adoptopenjdk.net/releases.html?variant=openjdk8&jvmVariant=openj9>) Imagen docker: adoptopenjdk/openjdk11-openj9:alpine-jre
* Maven: No necesario, está integrado en el proyecto mediante "maven wrapper" / mvnw (<https://github.com/takari/maven-wrapper>)
* Docker: para construir y ejecutar imágenes Docker:
    * Windows / Mac: <https://www.docker.com/products/docker-desktop>
    * WSL (Windows Subsystem Linux) + Docker Desktop: <https://nickjanetakis.com/blog/setting-up-docker-for-windows-and-wsl-to-work-flawlessly>
    * WSL + Remote Docker Server: <https://dev.to/sebagomez/installing-the-docker-client-on-windows-subsystem-for-linux-ubuntu-3cgd>
    * Ubuntu: <https://docs.docker.com/install/linux/docker-ce/ubuntu/> - Ubuntu

### Configuración

* Se hace uso de cloud config server para la obtención de propiedades dependientes del entorno.
* Se configura el registro en un servidor Eureka en el fichero application.yml
* El puerto de escucha por defecto es el 8760
* Se exponen los siguiente servicios de actuator: mappings, env, health, info
* Los mapeos para la redirección a los microservicios se hace también en el fichero application.yml

### Variables de entorno y valores por defecto

Se deben tener las siguientes variables de entorno para poder arrancar la aplicación

* Registro en Eureka. EUREKA_URI -> <http://localhost:8761/eureka>
* Cloud Config Server. Se definen las siguientes:
    * CONFIG_ENABLED -> true
    * CONFIG_SERVER -> <http://localhost:8888>
    * SPRING_PROFILES_ACTIVE -> local
    * CONFIG_SERVER_LABEL -> master
    * CONFIG_FAIL_FAST -> false
    * CONFIG_SERVER_USER -> ????
    * CONFIG_SERVER_PASSWORD -> ????

### Banner de arranque

* Banner generado con la fuente alligator2 (https://devops.datenkollektiv.de/banner.txt/index.html)


### Ejecución en desarrollo

Se deben tener las variables de entorno configuradas y ejecutar, dentro del proyecto, el siguiente comando:

```sh
$ cd yisas-gateway
$ EUREKA_URI=http://localhost:8761/eureka
$ CONFIG_SERVER=http://localhost:8888
$ ./mvnw spring-boot:run
```

## Docker

### Variables Docker

El dockerfile incluye las mismas variables de entorno para poder configurar el endpoint de Eureka

* EUREKA_URI -> <http://localhost:8761/eureka>
* CONFIG_SERVER -> <http://localhost:8888>

### Construcción de imagen Docker

El nombre de la imagen se genera a partir de tres propiedades definidas en el pom.xml

${docker.image.prefix}/${artifactId}:${version} -> yisasthemanuel/gateway:0.0.1-SNAPSHOT

```sh
$ cd yisas-gateway
$ ./mvnw clean package
```

### Registro de imagen Docker

El nombre de la imagen se genera a partir de tres propiedades definidas en el pom.xml (igual que la construcción)

El registro de hace en Docker Hub (<https://hub.docker.com/>)

Propiedades de conexión a Docker Hub: ${USER_HOME}/.docker/config.json 

```sh
$ cd yisas-gateway
$ ./mvnw clean install
```

### Ejecución de la imagen

```shell
docker run -d -e EUREKA_URI=http://host.docker.internal:8761/eureka -e CONFIG_SERVER=http://host.docker.internal:8888 -e SPRING_PROFILES_ACTIVE=development -p 8760:8760 --name yisas-gateway yisasthemanuel/gateway:0.0.1-SNAPSHOT
```

## TO DOs

* Uso de Lombok
* Incluir un mecanismo para la configuración de redirecciones de forma automática, basada en prefijos y host:
    * El par <prefijo> - <host> determinar la redirección http://gateway_host:8760/<prefijo>/<resto> hacia host/<resto>
    * Ejemplo: http://localhost:8760/gproresults/managers -> http://localhost:9080/managers

## Referencias [EN]

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/maven-plugin/)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/htmlsingle/#production-ready)
* [Spring Boot Monitor metrics with Prometheus](https://www.callicoder.com/spring-boot-actuator-metrics-monitoring-dashboard-prometheus-grafana/)
* [Spring Boot Thin Launcher](https://github.com/spring-projects-experimental/spring-boot-thin-launcher)
* [Spring Boot Thin Launcher & Docker](https://dev.to/bufferings/spring-boot-thin-launcher-anddocker-2oa7)

## Guías [EN]

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
