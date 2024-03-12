FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY /applications/app-service/build/libs/taller_docker_cliente.jar app.jar
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70.0 -Dcom.sun.management.jmxremote.rmi.port=8087 -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.port=8087 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.local.only=false -Djava.rmi.server.hostname=localhost"
EXPOSE 9080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]
