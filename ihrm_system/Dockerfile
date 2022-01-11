FROM java:8
EXPOSE 9004
# 将本地的jar包放到容器的根目录中，linux中 '/'代表的就是根目录
ADD target/ihrm_system-1.0-SNAPSHOT.jar /ihrm_system.jar
# VOLUME /data/logs
ENTRYPOINT ["java", "-Xmx64m", "-Xms16m", "-Xmn8m", "-Xss8m", "-jar", "/ihrm_system.jar", "-Dspring.profiles.active=pro"]
