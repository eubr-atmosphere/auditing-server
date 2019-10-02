#!/bin/bash

cd /home/ubuntu/auditing
git clone https://github.com/eubr-atmosphere/auditing-server.git
mv general.conf auditing-server/database-setup
cd auditing-server/database-setup
bash deploy-script.sh

cd /home/ubuntu/auditing

sudo rm -rf auditing-server

image=eubraatmosphere/auditing-server:$1
sudo docker pull $image
container_id=`sudo docker run --name auditing-server -idt $image`

DIR_PATH="`dirname \"$0\"`"              # relative
DIR_PATH="`( cd \"$DIR_PATH\" && pwd )`"  # absolutized and normalized
if [ -z "$DIR_PATH" ] ; then
  # error; for some reason, the path is not accessible
  # to the script (e.g. permissions re-evaled after suid)
  exit 1  # fail
fi

sudo docker exec $container_id /bin/bash -c "mkdir src/main/resources/private"
sudo docker cp $DIR_PATH/auditing-server.conf $container_id:/root/auditing-server/src/main/resources/private
sudo docker exec $container_id /bin/bash -c "mvn spring-boot:run -X > log.out 2> log.err" &