FROM java:8
EXPOSE 9009
# 将本地的jar包放到容器的根目录中，linux中 '/'代表的就是根目录
ADD target/ihrm_audit-1.0-SNAPSHOT.jar /ihrm_audit.jar
# VOLUME /data/logs
ENTRYPOINT ["java", "-Xmx64m", "-Xms16m", "-Xmn8m", "-Xss8m", "-jar", "/ihrm_audit.jar", "-Dspring.profiles.active=pro"]
