FROM amazoncorretto:17

COPY ./build/libs/wex-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "wex-0.0.1-SNAPSHOT.jar"]