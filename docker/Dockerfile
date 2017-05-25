FROM dockerreg.lan:5000/homeinfo_java_base:0.2.0
MAINTAINER Pawel <inne.poczta@gmail.com>

RUN /bin/bash -c 'mkdir -p /opt/thymeleaf_editor'

ADD build/libs/thymeleaf-editor-1.0.0.jar /opt/thymeleaf_editor/thymeleaf-editor.jar
ADD application.properties /opt/thymeleaf_editor/
ADD docker/start.sh /usr/local/bin/

WORKDIR /opt/thymeleaf_editor/
CMD ["/usr/local/bin/start.sh"]
