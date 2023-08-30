# Use a base image with Java 11 and Maven installed
FROM maven:3.8.6-amazoncorretto-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Copy the source code
COPY src src

# Build the application
RUN mvn package -DskipTests

# Create a new image with just the built JAR file
FROM maven:3.8.6-amazoncorretto-17

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the previous build stage
COPY --from=build /app/target/achadoseperdidos-1.jar achadoseperdidos-1.jar

#COPY target/achadoseperdidos-1.jar /app/achadoseperdidos-1.jar

EXPOSE 8080

CMD ["java", "-jar", "achadoseperdidos-1.jar"]
