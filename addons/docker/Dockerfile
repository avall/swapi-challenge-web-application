FROM eclipse-temurin:17 as compiler

LABEL stage=compiler
RUN mkdir -p /workdir
WORKDIR /workdir

COPY . .

# RUN ./mvnw --no-transfer-progress package -T 2 -DskipTests
RUN ./gradlew clean build -x test --parallel --max-workers=2

# build
FROM eclipse-temurin:17 as builder
LABEL stage=builder
RUN echo 'test'
COPY --from=compiler /workdir/app/main/build/libs/*.jar ./application.jar

EXPOSE 8082
ENTRYPOINT ["java","-Xms256m","-Xmx512m", "-jar", "application.jar"]