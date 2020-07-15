FROM openjdk:8
ADD build/libs/tourguide-pricer-1.0.0.jar tourguide-pricer-1.0.0.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "tourguide-pricer-1.0.0.jar"]
