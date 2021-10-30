FROM openjdk:8-slim

ENV KATOONYAKA_HOME /katoonyaka-home

RUN mkdir -p /katoonyaka-bin/config

COPY *.jar /katoonyaka-bin/katoonyaka.jar

WORKDIR /katoonyaka-bin

ENTRYPOINT ["java", \
    "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Xmx128m", "-Xms32m", \
    "-jar", "katoonyaka.jar"]
