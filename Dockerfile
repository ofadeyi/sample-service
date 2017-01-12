##################################################################
# Whitbread Digital Sample-Service Image
##################################################################
FROM whitbreaddigital/java:8u111

MAINTAINER Team Kraken

#############################################
# Setup
#############################################
VOLUME /tmp

ARG ARTIFACT_DOWNLOAD_LINK

# Download the jar and the entrypoint script file from the artefact repository and S3
RUN wget -O app.jar ${ARTIFACT_DOWNLOAD_LINK} \
    && bash -c 'touch /app.jar' \
    && wget -O /usr/local/bin/entrypoint.sh https://s3-eu-west-1.amazonaws.com/wbroomcontrol/entrypoint.sh \
    && chmod +x /usr/local/bin/entrypoint.sh

# Command that will be runned when the container is started
CMD bash -c 'source /usr/local/bin/entrypoint.sh && java -Djava.security.egd=file:/dev/./urandom -jar /app.jar'
