FROM whitbreaddigital/java:8u111
VOLUME /tmp
ADD sample-service-*.jar app.jar
RUN bash -c 'touch /app.jar'

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

CMD bash -c 'source /usr/local/bin/entrypoint.sh && java -Djava.security.egd=file:/dev/./urandom -jar /app.jar'
