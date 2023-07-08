FROM adoptopenjdk/openjdk11
EXPOSE 8080
COPY target/hexagonal-demo-0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]