FROM openjdk:11
ADD /target/app-0.0.1-SNAPSHOT.jar backend.jar
COPY src/main/resources/images /backend/images
COPY application-deploy.properties /backend/config/
COPY src/main/resources/firebase.json /backend/config
ENV SPRING_CONFIG_LOCATION=file:/backend/config/application-deploy.properties
VOLUME /src/main/resources/application.properties:SPRING_CONFIG_LOCATION
EXPOSE 587/tcp
ENTRYPOINT ["java", "-jar", "backend.jar", "-C", "backend/images"]