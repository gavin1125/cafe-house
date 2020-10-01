FROM java:8
ARG JAR_FILE
ADD target/${JAR_FILE} /customer-service.jar
ENTRYPOINT ["java", "-jar","/customer-service.jar"]