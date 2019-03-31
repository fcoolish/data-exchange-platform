#!/bin/sh

## exec shell name
EXEC_SHELL_NAME=mst-service\.sh
## service name
SERVICE_NAME=dxp-mst-service
## service dir 
SERVICE_DIR=/usr/local/workspace/mst-service

mkdir $SERVICE_DIR

cd $SERVICE_DIR

rm -rf logs

mkdir logs

