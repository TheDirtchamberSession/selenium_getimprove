FROM openjdk:jdk-buster

LABEL name="testing" \
    maintainer="Testing" \
    version="1.0" \
    description="Selenium framework, Maven and Chrome Headless in a container"

# Downloading and installing Maven
# 1- Define a constant with the version of maven you want to install
ARG MAVEN_VERSION=3.6.3

# 2- Define a constant with the working directory
ARG USER_HOME_DIR="/home"
ENV HOME "$USER_HOME_DIR"

# 3- Define the SHA key to validate the maven download
ARG SHA=c35a1803a6e70a126e80b2b3ae33eed961f83ed74d18fcd16909b2d44d7dada3203f1ffe726c17ef8dcca2dcaa9fca676987befeadc9b9f759967a8cb77181c0

# 4- Define the URL where maven can be downloaded from
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

# 5- Create the directories, download maven, validate the download, install it, remove downloaded file and set links
RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && echo "Downloading maven" \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  \
  && echo "Checking download hash" \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  \
  && echo "Unziping maven" \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  \
  && echo "Cleaning and setting links" \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

# 6- Define environmental variables required by Maven, like Maven_Home directory and where the maven repo is located
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG /usr/share/maven/.m2

# Install Chrome
# Install Chrome dependencies
RUN echo "Installing Chrome" && apt-get update && apt-get -y install sudo
#RUN apt-get install -y --no-install-recommends wget
RUN apt update && \
    apt install -y --no-install-recommends fonts-liberation libappindicator3-1 libasound2 libatk-bridge2.0-0 libgtk-3-0 libnspr4 libnss3 libx11-xcb1 libxss1 libxtst6 lsb-release xdg-utils libxcb-dri3-0 libgbm1 libdrm2 vim

# Download and install Chrome
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN sudo dpkg -i google-chrome-stable_current_amd64.deb

# Add chrome as a user
RUN echo "Adding Chrome user" && groupadd -r chrome && \
    useradd -r -g chrome -G audio,video chrome && \
    mkdir -p /home/chrome && \
    chown -R chrome:chrome /home/chrome && \
    chown -R chrome:chrome /opt/google/chrome &&\
    chown -R chrome:chrome /usr/share/maven
RUN adduser chrome sudo

# Copy test source
COPY . ${USER_HOME_DIR}/testing

# Get installed chrome version & fetch appropriate chromedriver
RUN /bin/bash -c 'chromeversion="$(google-chrome -version)" && versionnumber="${chromeversion:14}" && chromedriverversion="${versionnumber%%.*}" && \
                    fullurl="$(curl https://chromedriver.storage.googleapis.com/LATEST_RELEASE_$chromedriverversion)" && wget https://chromedriver.storage.googleapis.com/$fullurl/chromedriver_linux64.zip && \
                    unzip -o chromedriver_linux64.zip -d /home/testing'

# Set privileges for new user
RUN chown -R chrome:chrome /home/testing
USER chrome

# Run test suit to make maven download all the necessary dependencies
WORKDIR ${USER_HOME_DIR}/testing

RUN mvn test -Dsurefire.suiteXmlFiles=./suites/RunSelenium.xml
