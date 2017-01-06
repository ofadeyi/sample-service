##################################################################
# Whitbread Digital Sample-Service Image
##################################################################
FROM whitbreaddigital/java:8u111

MAINTAINER Team Kraken

#############################################
# Setup
#############################################
VOLUME /tmp

# Download the entrypoint script
RUN wget ${ARTIFACT_DOWNLOAD_LINK} -O app.jar \
    bash -c 'touch /app.jar' \
    wget https://s3-eu-west-1.amazonaws.com/wbroomcontrol/entrypoint.sh -O /usr/local/bin/entrypoint.sh \
    chmod +x /usr/local/bin/entrypoint.sh

# Command that will be runned when the container is started
CMD bash -c 'source /usr/local/bin/entrypoint.sh && java -Djava.security.egd=file:/dev/./urandom -jar /app.jar'
