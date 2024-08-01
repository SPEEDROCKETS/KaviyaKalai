#!/usr/bin/env bash

LOG_FILE=$1
STAGE_STATUS=$2
PROJECT_NAME=$3
ENVIRONMENT=$4

if [[ "$STAGE_STATUS" == "Succeeded" ]]
then
  SOURCE="jenkins-success-logs"
else
  SOURCE="jenkins-failure-logs"
fi

echo $(cat $LOG_FILE) > build.log

curl --location --request POST "https://hec-ext-qa-splunk.app.ford.com:443/services/collector/raw?channel=00872DC6-AC83-4EDE-8AFE-8413C3825C4C&sourcetype=$PROJECT_NAME-$ENVIRONMENT-$SOURCE-$LOG_FILE" \
--header 'Authorization: Splunk 23043770-b78a-4c9b-b083-f6a796994dbd' \
--header 'Content-Type: text/plain' \
--data-binary '@build.log'
