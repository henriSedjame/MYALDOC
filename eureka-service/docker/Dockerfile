FROM openjdk:11
ADD target/myaldoc-eureka-service.jar myaldoc-eureka-service.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar", "myaldoc-eureka-service.jar"]