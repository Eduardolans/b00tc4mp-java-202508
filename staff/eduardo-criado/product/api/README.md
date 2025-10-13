### Contents of `api/README.md`

# Hello World Servlet

This project is a simple Maven web application that demonstrates how to create a servlet that responds with "Hello World" to GET requests.

## Project Structure

- `pom.xml`: Maven configuration file.
- `src/main/java/com/example/HelloServlet.java`: The servlet implementation.
- `src/main/webapp/WEB-INF/web.xml`: Deployment descriptor for the web application.

## How to Run

1. Build the project using Maven:

   ```
   mvn clean package
   ```

2. Deploy the generated WAR file to a servlet container (e.g., Apache Tomcat).

3. Access the servlet at the following URL:

   ```
   http://localhost:8080/api/hello
   ```

api/
┣ src/
┃ ┗ main/
┃ ┣ java/
┃ ┃ ┃ ┗ com/
┃ ┃ ┃ ┗ example/
┃ ┗ webapp/
┃ ┃ ┗ WEB-INF/
┃ ┃ ┃ ┗ web.xml
┣ target/
┃ ┣ api/
┃ ┃ ┣ META-INF/
┃ ┃ ┗ WEB-INF/
┃ ┃ ┣ classes/
┃ ┃ ┃ ┃ ┗ com/
┃ ┃ ┗ web.xml
┃ ┣ classes/
┃ ┃ ┗ com/
┃ ┃ ┗ example/
┃ ┃ ┃ ┗ HelloServlet.class
┃ ┣ generated-sources/
┃ ┃ ┗ annotations/
┃ ┣ maven-archiver/
┃ ┃ ┗ pom.properties
┃ ┣ maven-status/
┃ ┃ ┗ maven-compiler-plugin/
┃ ┃ ┗ compile/
┃ ┃ ┃ ┗ default-compile/
┃ ┗ api.war
┗ pom.xml
