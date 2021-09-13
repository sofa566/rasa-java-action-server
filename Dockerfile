FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

COPY  libs libs
RUN ./mvnw install:install-file -Dfile=libs/rasa-java-sdk-2.3.0.jar -DgroupId=io.github.rbajek -DartifactId=rasa-java-sdk -Dversion=2.3.0 -Dpackaging=jar

RUN ./mvnw install \
   && mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:8-jre-alpine
USER root
RUN apk update && apk upgrade  \
    && apk add --update tzdata && cp /usr/share/zoneinfo/Asia/Taipei /etc/localtime \
    && echo "Asia/Taipei" > /etc/timezone \
    && rm -rf /var/cache/apk/*
ENV TZ=Asia/Taipei

RUN addgroup -S rasa && adduser -S rasa -G rasa
# VOLUME /t
USER rasa
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-noverify","-XX:TieredStopAtLevel=1","-cp","app:app/lib/*","io.github.rbajek.rasa.action.server.RasaActionServer"]
