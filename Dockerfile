FROM repo.sadad.co.ir/repository/baam-docker-registry/eclipse-temurin:21-jre-alpine
VOLUME /tmp

ENV TZ=Asia/Tehran

RUN  mkdir -p /var/log/promissory-api
RUN  chmod -R 777 /var/log/promissory-api

COPY target/*.jar promissory-api-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-Xdebug","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=1525","-Dcom.ibm.icu.util.TimeZone.DefaultTimeZoneType=JDK","-jar","/promissory-api-1.0.0-SNAPSHOT.jar"]