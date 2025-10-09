# App

## Commands

Compile

```sh
$ mvn compile
```

Run

```sh
$ mvn exec:java
```

or with compile first

```sh
$ mvn compile exec:java
```

Package (jar)

```sh
$ mvn package
```

Clean

```sh
$ mvn clean
```

Clean and compile

```sh
$ mvn clean compile
```

Clean and package

```sh
$ mvn clean package
```

## El código Java se compila usando un compilador de Java, normalmente desde la terminal o una herramienta de construcción como Maven, según la configuración de tu proyecto.

### Compilación en tu proyecto

En tu caso, tu proyecto usa Maven como gestor de dependencias y compilación, según el archivo pom.xml adjunto. Maven maneja la compilación y el empaquetado de los archivos .java en bytecode (.class), que luego puede ejecutar la JVM.

### ¿Cómo y dónde se compila?

El comando estándar para compilar tu proyecto desde la raíz (donde está tu pom.xml) es:

```bash
mvn compile
```

Maven buscará en la carpeta src/main/java para compilar el código fuente. Los archivos compilados (.class) se generan en target/classes.

- Si quieres ejecutar tu aplicación, puedes usar:

```bash
mvn exec:java
```

Este comando usará el plugin exec-maven-plugin configurado en tu pom.xml, que ejecuta la clase principal indicada (en este caso, App).

- Resumen

El código se compila en la carpeta target/classes dentro del proyecto.

La compilación se lanza con el comando mvn compile desde la raíz del proyecto, gracias a Maven.

Tu archivo pom.xml también determina detalles de compilación, versión de Java, y clase de entrada principal.

Todo esto ocurre localmente en tu máquina, donde esta configurado Java y Maven (pom.xml).
