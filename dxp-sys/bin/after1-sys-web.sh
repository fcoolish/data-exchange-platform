#!/bin/sh

## java env
export JAVA_HOME=/usr/local/java/jdk1.7.0_72
export JRE_HOME=$JAVA_HOME/jre

## restart tomcat
/usr/local/apache-tomcat-7.0.29/bin/shutdown.sh
sleep 5
rm -rf /usr/local/apache-tomcat-7.0.29/webapps/dxp-sys
/usr/local/apache-tomcat-7.0.29/bin/startup.sh
