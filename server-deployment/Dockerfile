FROM ubuntu:xenial


RUN DEBIAN_FRONTEND=noninteractive && \
	apt-get -qq update && \
	apt-get -y -qq dist-upgrade && \
	apt-get -qq install -y locales && \
	locale-gen en_US.UTF-8 && \
	apt-get -y install openjfx && \
	export LANG=en_US.UTF-8
RUN DEBIAN_FRONTEND=noninteractive && apt-get -qq install -y vim screen wget git curl openjdk-8-jdk

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

COPY main.jar /root/main.jar

EXPOSE 4567

ENTRYPOINT ["java","-jar","/root/main.jar"]