#!/bin/bash

APP_MAIN=com.dl.officialsite.OfficialSiteApplication
CLASSPATH='conf/:apps/*:lib/*'
CURRENT_DIR=$(pwd)/
LOG_DIR=${CURRENT_DIR}log
CONF_DIR=${CURRENT_DIR}conf

if [[ -n "$DBPWD" ]];then
  sed "s/{{SPRING_DATASOURCE_PASSWORD}}/$DBPWD/g" conf/application-template.yml > conf/application.yml
else
  echo "by hand"
fi

if [[ -n "$SECRET_ID" ]];then
  sed "s/{{SECRET_ID}}/$SECRET_ID/g" conf/application-template.yml > conf/application.yml
else
  echo "by hand"
fi

if [[ -n "$SECRET_KEY" ]];then
  sed "s/{{SECRET_KEY}}/$SECRET_KEY/g" conf/application-template.yml > conf/application.yml
else
  echo "by hand"
fi

if [[ -n "$BUCKET_NAME" ]];then
  sed "s/{{BUCKET_NAME}}/$BUCKET_NAME/g" conf/application-template.yml > conf/application.yml
else
  echo "by hand"
fi

if [[ -n "$REGION_NAME" ]];then
  sed "s/{{REGION_NAME}}/$REGION_NAME/g" conf/application-template.yml > conf/application.yml
else
  echo "by hand"
fi

SERVER_PORT=$(cat $CONF_DIR/application.yml | grep "server:" -A 3 | grep "port" | awk '{print $2}'| sed 's/\r//')
if [ ${SERVER_PORT}"" = "" ];then
    echo "$CONF_DIR/application.yml server port has not been configured"
    exit -1
fi
JAVA_CMD="${JAVA_HOME}/bin/java"
if [ ${JAVA_HOME}"" = "" ];then
    echo "JAVA_HOME has not been configured, using java"
    JAVA_CMD='java'
fi

mkdir -p log

startWaitTime=30
processPid=0
processStatus=0
checkProcess(){
    server_pid=$(ps aux | grep java | grep $CURRENT_DIR | grep $APP_MAIN | awk '{print $2}')
    if [ -n "$server_pid" ]; then
        processPid=$server_pid
        processStatus=1
    else
        processPid=0
        processStatus=0
    fi
}

JAVA_OPTS=" -Dfile.encoding=UTF-8"
JAVA_OPTS+=" -Xmx256m -Xms256m -Xmn128m -Xss512k -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m"
JAVA_OPTS+=" -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${LOG_DIR}/heap_error.log"

start(){
    checkProcess
    echo "==============================================================================================="
    if [ $processStatus == 1 ]; then
        echo "Server $APP_MAIN Port $SERVER_PORT is running PID($processPid)"
        echo "==============================================================================================="
    else
        echo -n "Server $APP_MAIN Port $SERVER_PORT ..."
        echo "$JAVA_CMD -agentlib:jdwp=transport=dt_socket,address=9093,server=y,suspend=n -Djdk.tls.namedGroups="secp256k1" $JAVA_OPTS -Djava.library.path=$CONF_DIR -Dhttps.protocols=TLSv1.2 -cp $CLASSPATH $APP_MAIN >> $LOG_DIR/front.out 2>&1 &"

        nohup $JAVA_CMD -agentlib:jdwp=transport=dt_socket,address=9093,server=y,suspend=n -Djdk.tls.namedGroups="secp256k1" $JAVA_OPTS -Djava.library.path=$CONF_DIR -Dhttps.protocols=TLSv1.2 -cp $CLASSPATH $APP_MAIN >> $LOG_DIR/front.out 2>&1 &

        count=1
        result=0
        while [ $count -lt $startWaitTime ] ; do
           checkProcess
           if [ $processPid -ne 0 ]; then
               result=1
               break
           fi
           let count++
           echo -n "."
           sleep 1
       done

       if [ $result -ne 0 ]; then
           echo "PID($processPid) [Starting]. Please check message through the log file (default path:./log/)."
           echo "==============================================================================================="
       else
           echo "[Failed]. Please check message through the log file (default path:./log/)."
           echo "==============================================================================================="
       fi
    fi
}

start