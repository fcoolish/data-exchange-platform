#!/bin/sh
## java env
export JAVA_HOME=/usr/local/jdk1.7
export JRE_HOME=$JAVA_HOME/jre

## exec shell name
EXEC_SHELL_NAME=mst-service\.sh
## service name
SERVICE_NAME=dxp-mst-service
SERVICE_DIR=/usr/local/workspace/mst-service
JAR_NAME=$SERVICE_NAME\.jar
PID=$SERVICE_NAME\.pid

cd $SERVICE_DIR

case "$1" in

    start)
        nohup $JRE_HOME/bin/java -Xms256m -Xmx512m -jar $JAR_NAME >/dev/null 2>&1 &
        echo $! > $SERVICE_DIR/$PID
        echo "#### start $SERVICE_NAME"
        ;;

    stop)
        kill `cat $SERVICE_DIR/$PID`
        rm -rf $SERVICE_DIR/$PID
        echo "#### stop $SERVICE_NAME"

        sleep 5

        TEMP_PID=`ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}'`
        if [ "$TEMP_PID" == "" ]; then
            echo "#### $SERVICE_NAME process not exists or stop success"
        else
            echo "#### $SERVICE_NAME process pid is:$TEMP_PID"
            kill -9 $TEMP_PID
        fi
        ;;

    restart)
        $0 stop
        sleep 2
        $0 start
        echo "#### restart $SERVICE_NAME"
        ;;

    *)
        ## restart
        $0 stop
	sleep 2
        $0 start
        ;;

esac
exit 0

