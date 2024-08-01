#!/usr/bin/env bash

APP_NAME=$1
ENV_NAME=$2
PIPELINERUN_STATUS=$3


build_log_url="/var/jenkins_home/jobs/$JOB_NAME/builds/$BUILD_NUMBER/log"

echo $build_log_url

creationTime=$(cat $build_log_url | grep "creationTimestamp" | head -n 1 | cut -d"=" -f2)

TRIGGERED_BY=$(cat $build_log_url | grep "Started by user " | head -n 1 | cut -d" " -f5)

echo "StartedBy:$TRIGGERED_BY"

START_TIME=$(date -d "$creationTime" +"%Y-%m-%d %H:%M:%S")

echo "starttime:$START_TIME"

END_TIME=$(date +"%Y-%m-%d %H:%M:%S")

echo "endtime:$END_TIME"

END_TIME_SECONDS=$(date --date "$END_TIME" +%s)

START_TIME_SECONDS=$(date --date "$START_TIME" +%s)

DIFF_BW_SECONDS=$((END_TIME_SECONDS - START_TIME_SECONDS))

echo "startimesec:$START_TIME_SECONDS"

echo "endtimesec:$END_TIME_SECONDS"

echo "diff:$DIFF_BW_SECONDS"

TIME_TAKEN=$(printf '%dh:%dm:%ds\n' $((DIFF_BW_SECONDS%86400/3600)) $((DIFF_BW_SECONDS%3600/60)) $((DIFF_BW_SECONDS%60)))

echo $TIME_TAKEN

APPLICATION_JSON='{
  "event": {
    "app-name":"%s",
    "environment-name": "%s",
    "data-source":"jenkins",
    "pipeline-status":"%s",
    "triggered-by":"%s",
    "start-time":"%s",
    "end-time":"%s",
    "time-taken":"%s"
  },
  "sourcetype": "jenkins-deploy-monitor"
}'

echo $(printf "$APPLICATION_JSON" "$APP_NAME" "$ENV_NAME" "$PIPELINERUN_STATUS" "$TRIGGERED_BY" "$START_TIME" "$END_TIME" "$TIME_TAKEN") > application.json

curl --location --request POST 'https://hec-ext-qa-splunk.app.ford.com:443/services/collector' --header 'Authorization: Splunk 23043770-b78a-4c9b-b083-f6a796994dbd' --header 'Content-Type: application/json' --data @application.json
