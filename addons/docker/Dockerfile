FROM eclipse-temurin:17 as compiler

LABEL stage=compiler
RUN mkdir -p /workdir
WORKDIR /workdir

COPY . .

RUN ./mvnw --no-transfer-progress package -T 2 -DskipTests

# build
FROM eclipse-temurin:17 as builder
LABEL stage=builder
RUN echo 'test'
COPY --from=compiler /workdir/app/main/target/*.jar ./application.jar

EXPOSE 8080
ENTRYPOINT ["java","-Xms256m","-Xmx512m", "-jar", "application.jar"]