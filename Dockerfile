##################################################################
# Whitbread Digital Sample-Service Image
##################################################################
FROM whitbreaddigital/java:8u111

MAINTAINER Team Kraken

#############################################
# Setup
#############################################
VOLUME /tmp

# Copy the jar file from the Maven target folder and renames it
ADD target/sample-service-*.jar app.jar

# Download the entrypoint script
RUN bash -c 'touch /app.jar' \
    wget https://s3-eu-west-1.amazonaws.com/wbroomcontrol/entrypoint.sh -O /usr/local/bin/entrypoint.sh \
    chmod +x /usr/local/bin/entrypoint.sh

# Command that will be runned when the container is started
CMD bash -c 'source /usr/local/bin/entrypoint.sh && java -Djava.security.egd=file:/dev/./urandom -jar /app.jar'
