FROM java:8
EXPOSE 9003
# 将本地的jar包放到容器的根目录中，linux中 '/'代表的就是根目录
ADD target/ihrm_employee-1.0-SNAPSHOT.jar /ihrm_employee.jar
# VOLUME /data/logs
ENTRYPOINT ["java", "-Xmx128m", "-Xms32m", "-Xmn8m", "-Xss8m", "-jar", "/ihrm_employee.jar", "-Dspring.profiles.active=pro"]
