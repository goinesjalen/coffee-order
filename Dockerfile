FROM eclipse-temurin:17
WORKDIR /home
COPY db.txt db.txt
COPY ./target/coffee-order-0.0.1-SNAPSHOT.jar coffee-order.jar
COPY db.txt db.txt
ENTRYPOINT ["java", "-jar", "coffee-order.jar"]
