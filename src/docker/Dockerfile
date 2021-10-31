FROM openjdk:8-slim

ENV KATOONYAKA_HOME /katoonyaka-home

COPY *.jar /katoonyaka-bin/katoonyaka.jar

RUN groupadd -g 2000 app && \
    useradd -u 2000 -g app -s /bin/bash -m app && \
    chown -R app:app /katoonyaka-bin

EXPOSE 8113

USER app:app

WORKDIR /katoonyaka-bin

ENTRYPOINT ["java", \
    "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Xmx128m", "-Xms32m", \
    "-jar", "katoonyaka.jar"]
