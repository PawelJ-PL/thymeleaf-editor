FROM openjdk:8u232-jre-slim
MAINTAINER Pawel <inne.poczta@gmail.com>

RUN /bin/bash -c 'mkdir -p /opt/thymeleaf_editor'

ADD build/libs/thymeleaf-editor-*.jar /opt/thymeleaf_editor/thymeleaf-editor.jar
ADD application.properties /opt/thymeleaf_editor/
ADD docker_entrypoint.sh /docker_entrypoint.sh

WORKDIR /opt/thymeleaf_editor/
ENTRYPOINT ["/docker_entrypoint.sh"]

EXPOSE 30000

USER 65534