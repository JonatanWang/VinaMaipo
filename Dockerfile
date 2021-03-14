FROM maven:3-openjdk-15-slim
RUN mvn -version

COPY docker/docker-compose-wait-2.8.0 /wait
RUN chmod +x /wait

# Install Spring Boot CLI
RUN apt-get update && \
    apt-get install -y zip unzip && \
    curl -s "https://get.sdkman.io" | bash && \
    bash -c "source /root/.sdkman/bin/sdkman-init.sh && sdk install springboot" && \
    apt-get remove -y zip unzip

# Make Maven download needed dependencies with docker/spring/pom.xml
WORKDIR /tmp
COPY docker/dummy-spring-app src
COPY pom.xml pom.xml
RUN mvn spring-boot:run

WORKDIR /app
VOLUME /app

EXPOSE 8080

ENV SPRING_BOOT_MONGODB_HOST=${SPRING_BOOT_MONGODB_HOST:-localhost}

CMD /wait && ls -l /app && mvn spring-boot:run -Dspring-boot.run.arguments=--spring.data.mongodb.host=$SPRING_BOOT_MONGODB_HOST
