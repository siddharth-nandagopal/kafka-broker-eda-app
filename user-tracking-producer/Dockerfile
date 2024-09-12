FROM openjdk:22
EXPOSE 8080
ADD target/user-tracking-producer-0.0.1-SNAPSHOT.jar user-tracking-producer.jar
ENTRYPOINT ["java","-jar","/user-tracking-producer.jar"]