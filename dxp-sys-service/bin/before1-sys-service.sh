#!/bin/sh

## exec shell name
EXEC_SHELL_NAME=sys-service\.sh
## service name
SERVICE_NAME=dxp-sys-service
## service dir 
SERVICE_DIR=/usr/local/workspace/sys-service

mkdir $SERVICE_DIR

cd $SERVICE_DIR

rm -rf logs

mkdir logs

