FROM openjdk:11.0-oracle
ARG JAR_FILE=target/wallet-kr-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8092
ENTRYPOINT ["java","-jar","/app.jar"]