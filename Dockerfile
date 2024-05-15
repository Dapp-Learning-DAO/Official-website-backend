########## compile
FROM maven:3-jdk-8 as builder

# /src
WORKDIR /code

# copy source code
COPY gradlew gradlew
COPY build.gradle build.gradle
COPY gradle gradle
COPY settings.gradle settings.gradle

COPY src src

# build
RUN bash gradlew build -x test
RUN ls -al /code
RUN ls -al /code/dist/apps

########## copy compiled files
FROM openjdk:8-jdk-alpine as prod
LABEL maintainer="Hongbin.Yuan <yuanhongbin9090@gmail.com>"

VOLUME /logs
RUN apk --no-cache add bash curl wget

EXPOSE 8080

COPY NFT-DL-WARCRAFT.json /config/NFT-DL-WARCRAFT.json

ENV DEBUG_OPTS="-agentlib:jdwp=transport=dt_socket,address=9093,server=y,suspend=n"
ENV JAVA_OPTS="-Dfile.encoding=UTF-8 -Xmx256m -Xms256m -Xmn128m -Xss512k -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/logs/heap_error.log"

COPY --from=builder /code/dist/lib              /app/lib
COPY --from=builder /code/dist/conf             /app/conf
COPY --from=builder /code/dist/apps/dl.jar      /app/apps/dl.jar

# start commond
ENTRYPOINT ["sh", "-c", "java ${DEBUG_OPTS} ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -cp '/app/conf/:/app/apps/*:/app/lib/*' com.dl.officialsite.OfficialSiteApplication ${0} ${@}"]
