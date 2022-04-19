#!/bin/sh
path=$(pwd)
logdir=$path/log/
echo "log dir: "$logdir
mkdir $logdir 2>/dev/null

service_list=(ihrm_eureka ihrm_gate ihrm_system ihrm_company ihrm_employee ihrm_social_securitys ihrm_attendance ihrm_audit)

for service in ${service_list[@]}
do
  echo "start "$service" ?"
  read start_service
  cd $path/$service/target/
  pwd
  run_java="-Xmx64m -Xms16m -jar ${service}-1.0-SNAPSHOT.jar --spring.profiles.active=local"
  nohup java $run_java > $logdir$service".log" 2>&1 &
  echo "starting ${service} ..."
  echo ""
done