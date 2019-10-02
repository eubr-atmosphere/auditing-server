#!/bin/bash

cd /home/ubuntu/auditing

host_ip=$(grep ^host_ip general.conf | awk -F "=" '{print $2}')
db_container_port=$(grep ^container_port general.conf | awk -F "=" '{print $2}')
db_name=$(grep ^db_name general.conf | awk -F "=" '{print $2}')
db_url=$host_ip:$db_container_port/$db_name
db_user=$(grep ^db_user general.conf | awk -F "=" '{print $2}')
db_password=$(grep ^db_password general.conf | awk -F "=" '{print $2}')

sed -i 's,spring.datasource.url.*,spring.datasource.url=jdbc:postgresql://$db_url,' ./application.properties
sed -i 's,spring.datasource.username.*,spring.datasource.username=$db_user,' ./application.properties
sed -i 's,spring.datasource.password.*,spring.datasource.password=$db_password,' ./application.properties

git clone https://github.com/eubr-atmosphere/auditing-server.git
mv general.conf auditing-server/database-setup
cd auditing-server/database-setup
bash deploy-script.sh

cd /home/ubuntu/auditing

sudo rm -rf auditing-server

image=eubraatmosphere/auditing-server:$1
sudo docker pull $image
container_id=`sudo docker run --name auditing-server -idt $image`

sudo docker exec $container_id /bin/bash -c "mkdir src/main/resources/private"
sudo docker cp ./auditing-server.conf $container_id:/root/auditing-server/src/main/resources/private
sudo docker cp ./application.properties $container_id:/root/auditing-server/src/main/resources/
sudo docker exec $container_id /bin/bash -c "mvn spring-boot:run -X > log.out 2> log.err" &