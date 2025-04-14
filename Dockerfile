FROM maven:3.9.9-sapmachine-21 AS build
WORKDIR /family-budget
COPY pom.xml /family-budget/pom.xml
COPY ./src /family-budget/src
RUN mvn -f /family-budget/pom.xml clean package -Dmaven.test.skip=true

FROM sapmachine:21-jre-alpine
COPY --from=build /family-budget/target/*.jar /family-budget/*.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/family-budget/*.jar"]