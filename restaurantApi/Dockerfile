
FROM maven:3.6.1-jdk-8-alpine AS MAVEN_BUILD_STAGE

#We are copying all the files inside of this directory into the container directory
COPY ./ ./

RUN mvn clean package -DskipTests


CMD ["java", "-jar", "/target/restaurantApi-0.0.1-SNAPSHOT.jar"]