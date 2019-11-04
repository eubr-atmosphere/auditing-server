#!/bin/bash
DIR_PATH=$(pwd)
GENERAL_CONF_FILE_PATH="general.conf"

IMAGE_NAME="eubraatmosphere/auditing-database"
CONTAINER_NAME="auditing-database"

CONTAINER_PORT=5432

DB_USER=$(grep ^db_user $GENERAL_CONF_FILE_PATH | awk -F "=" '{print $2}')
DB_PASSWORD=$(grep ^db_password $GENERAL_CONF_FILE_PATH | awk -F "=" '{print $2}')

DB_NAME=fogbowauditing

DB_DATA_DIR=~/fogbow-atm-components/auditing-server/data
mkdir -p $DB_DATA_DIR
CONTAINER_DATA_DIR="/var/lib/postgresql/data"

TAG=$(grep ^img_tag $GENERAL_CONF_FILE_PATH | awk -F "=" '{print $2}')

if [ -z ${TAG// } ]; then
	TAG="latest"
fi

sudo docker pull $IMAGE_NAME:$TAG
sudo docker stop $CONTAINER_NAME
sudo docker rm $CONTAINER_NAME

sudo docker run -tdi --name $CONTAINER_NAME \
	-p $CONTAINER_PORT:$CONTAINER_PORT \
	-e DB_USER=$DB_USER \
	-e DB_PASS=$DB_PASSWORD \
	-e DB_NAME=$DB_NAME \
	-v $DB_DATA_DIR:$CONTAINER_DATA_DIR \
	$IMAGE_NAME:$TAG